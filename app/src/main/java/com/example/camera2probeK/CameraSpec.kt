// Original Code: TobiasWeis / android-camera2probe / Old school Java
// https://github.com/TobiasWeis/android-camera2probe/wiki
//
// 1st) Ported to Android Studio 4.2.1, API 29 and Java 8 (camera2probe4)
// 2nd) Ported to Kotlin 1.5
// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/

package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraAccessException
import android.os.Build
import android.content.Context
import android.util.Range
import kotlin.collections.ArrayList

class CameraSpec internal constructor(context: Context) {
    private var manager: CameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraIds: Array<String> = manager.cameraIdList
    private var characteristics: CameraCharacteristics = manager.getCameraCharacteristics(cameraIds[0])
    private var specs: MutableList<CameraSpecResult> = ArrayList()

    private fun setCharacteristics(CameraId: String) {
        try {
            characteristics = manager.getCameraCharacteristics(CameraId)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    fun getSpecs(): MutableList<CameraSpecResult> {
        return specs
    }

    private fun getCameraSpecs(title: String, commentList: List<Pair<Int, String>>, funcs: IntArray?) : List<CameraSpecResult> {
        val specTxt: MutableList<CameraSpecResult> = ArrayList()
        if (funcs != null) {
            if (title != KEY_NONE)
                specs.add(CameraSpecResult(KEY_TITLE, title, NONE))
            commentList.forEach { p: Pair<Int, String> ->
                val checkmark = if (funcs.contains(p.first)) CHECK else CROSS
                specTxt.add(CameraSpecResult(KEY_NEWLINE, p.second, checkmark))
            }
        }
        return specTxt
    }


    fun buildSpecs() {
        readModelInfo()
        for (id in cameraIds) {
            setCharacteristics(id)
            readCameraInfo(id)

            readToneMaps()

            specs.add(CameraSpecResult(KEY_BRAKE, "", NONE))

            read3ACapabilities()
            readAwbCapabilities()
            readAfCapabilities()
            readAeCapabilities()
            readControlSceneModes()
            readSceneEffectModes()

            readFaceDetectModes()

            readZoomParameters()

            specs.add(CameraSpecResult(KEY_BRAKE, "", NONE))

            readScalerStreamConfigMap()

            readVideoParameters()
        }
    }

    private fun readModelInfo() {
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "MODEL", NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Model: " + Build.MODEL, NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Manufacturer: " + Build.MANUFACTURER, NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Build version: " + Build.VERSION.RELEASE, NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "SDK version: " + Build.VERSION.SDK_INT.toString(), NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Logical Cameras: " + cameraIds.size.toString(), NONE))
    }

    private fun readCameraInfo(id : String) {
        var title = "LOGICAL CAMERA"
        val lensFacing = LensFacingsComment()
        val lensFacingKey = characteristics.get(CameraCharacteristics.LENS_FACING)
        val lensFacingTxt = lensFacingKey?.let {lensFacing.get(it)}
        specs.add(CameraSpecResult(KEY_L_TITLE, "$title[$id] $lensFacingTxt", NONE))

        // This API is not properly implemented on many models
        val physicalCameraIds = characteristics.physicalCameraIds
        var cameras = "API not implemented"
        if (physicalCameraIds.size != 0) cameras = physicalCameraIds.size.toString()
        specs.add(CameraSpecResult(KEY_NEWLINE, "Physical Cameras: $cameras", NONE))

        title = "Hardware Level Support Category: "
        val hwLevelComment =  HardwareLevelsComment()
        val hwlevelKey = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
        val hwLevelTxt = hwlevelKey?.let { hwLevelComment.get(it) }
        specs.add(CameraSpecResult(KEY_NEWLINE, title + hwLevelTxt, NONE))

        title = "Info version: "
        var infoVersionTxt = characteristics.get(CameraCharacteristics.INFO_VERSION)
        if (infoVersionTxt == null) infoVersionTxt = "Not supported"
        specs.add(CameraSpecResult(KEY_NEWLINE, title + infoVersionTxt, NONE))

        title = "Sensor Array Size: "
        val sensorInfoPixelArraySize = characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE)
        var sensorInfoPixelArraySizeTxt = "Could not get"
        if (sensorInfoPixelArraySize != null)
            sensorInfoPixelArraySizeTxt =
                sensorInfoPixelArraySize.width.toString() + " x " + sensorInfoPixelArraySize.height.toString()
        specs.add(CameraSpecResult(KEY_NEWLINE, title + sensorInfoPixelArraySizeTxt, NONE))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            title = "Sensor Array Size Max Res: "
            val sensorInfoPixelArraySizeMax =
                characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE_MAXIMUM_RESOLUTION)
            var sensorInfoPixelArraySizeMaxTxt = "Could not get"
            if (sensorInfoPixelArraySizeMax != null)
                sensorInfoPixelArraySizeMaxTxt =
                    sensorInfoPixelArraySizeMax.width.toString() + " x " + sensorInfoPixelArraySizeMax.height.toString()
            specs.add(CameraSpecResult(KEY_NEWLINE, title + sensorInfoPixelArraySizeMaxTxt, NONE))
        }

        title = "Sensor Pre Collective Array Size: "
        val sensorInfoPreCorrectedActiveArraySize =
            characteristics.get(CameraCharacteristics.SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE)
        var sensorInfoPreCorrectedActiveArraySizeTxt = "Could not get"
        if (sensorInfoPreCorrectedActiveArraySize != null)
            sensorInfoPreCorrectedActiveArraySizeTxt = sensorInfoPreCorrectedActiveArraySize.toShortString()
        specs.add(CameraSpecResult(KEY_NEWLINE, title + sensorInfoPreCorrectedActiveArraySizeTxt, NONE))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            title = "Sensor Pre Collective Array Size Max Res: "
            val sensorInfoPreCorrectedActiveArraySizeMax =
                characteristics.get(CameraCharacteristics.SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE_MAXIMUM_RESOLUTION)
            var sensorInfoPreCorrectedActiveArraySizeMaxTxt = "Could not get"
            if (sensorInfoPreCorrectedActiveArraySizeMax != null)
                sensorInfoPreCorrectedActiveArraySizeMaxTxt = sensorInfoPreCorrectedActiveArraySizeMax.toShortString()
            specs.add(CameraSpecResult(KEY_NEWLINE, title + sensorInfoPreCorrectedActiveArraySizeMaxTxt, NONE))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            title = "Rotate and Crop"
            val rotateAndCropModesComment = RotateAndCropModesComment()
            val rotateAndCropModes = characteristics.get(CameraCharacteristics.SCALER_AVAILABLE_ROTATE_AND_CROP_MODES)
            specs.addAll(getCameraSpecs(title, rotateAndCropModesComment.get(), rotateAndCropModes))
        }

        val depthExclusive = characteristics.get(CameraCharacteristics.DEPTH_DEPTH_IS_EXCLUSIVE)
        var depthExclusiveTxt = "Depth not supported"
        if (depthExclusive != null) {
            if (depthExclusive)
                depthExclusiveTxt = "Must interleave color and depth requests"
            else
                depthExclusiveTxt = "Single request can target both types"
        }
        specs.add(CameraSpecResult(KEY_NEWLINE, "Depth and Color: $depthExclusiveTxt", NONE))

        val flashAvailable = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
        if (flashAvailable != null) {
            var flashAvailableTxt = "Flash no available"
            if (flashAvailable)
                flashAvailableTxt = "Flash available"
            specs.add(CameraSpecResult(KEY_NEWLINE, flashAvailableTxt, NONE))
        }

        specs.add(CameraSpecResult(KEY_BRAKE, "", NONE))

        specs.add(CameraSpecResult(KEY_INDENT_PARA, "RawSensitivityBoostRange", NONE))
        val boostRange = characteristics.get(CameraCharacteristics.CONTROL_POST_RAW_SENSITIVITY_BOOST_RANGE)
        var result = "No support any RAW format outputs"
        if (boostRange != null)
            result = boostRange.lower.toString() + " to " + boostRange.upper.toString()
        specs.add(CameraSpecResult(KEY_INDENT_PARA, result, NONE))
        specs.add(CameraSpecResult(KEY_RESET, "", NONE))

        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Logical multi camera sensor sync type", NONE))
        val logicalMultiCameraSensorSyncTypesComment = LogicalMultiCameraSensorSyncTypesComment()
        val logicalMultiCameraSensorSyncType = characteristics.get(CameraCharacteristics.LOGICAL_MULTI_CAMERA_SENSOR_SYNC_TYPE)
        var logicalMultiCameraSensorSyncTypeTxt = "H/W level sync, not supurtted"
        if (logicalMultiCameraSensorSyncType != null)
            logicalMultiCameraSensorSyncTypeTxt = logicalMultiCameraSensorSyncTypesComment.get(logicalMultiCameraSensorSyncType)
        specs.add(CameraSpecResult(KEY_INDENT_PARA, logicalMultiCameraSensorSyncTypeTxt, NONE))

        title = "Color Correction"
        val colorCorrectionModesComment = ColorCorrectionModesComment()
        val colorCorrectionModes = characteristics.get(CameraCharacteristics.COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES)
        specs.addAll(getCameraSpecs(title, colorCorrectionModesComment.get(), colorCorrectionModes))

        title = "Noise Reduction"
        val noiseReductionModesComment = NoiseReductionModesComment()
        val noiseReductionModes = characteristics.get(CameraCharacteristics.NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES)
        specs.addAll(getCameraSpecs(title, noiseReductionModesComment.get(), noiseReductionModes))

        title = "Request Available Capabilities"
        val requestAvailableCapabilitiesComment = RequestAvailableCapabilitiesComment(Build.VERSION.SDK_INT)
        val capabilities = characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)
        specs.addAll(getCameraSpecs(title, requestAvailableCapabilitiesComment.get(), capabilities))
     }

    private fun readToneMaps() {
        var title = "Tone Map Modes"
        val availableToneMapModesComment = AvailableToneMapModesComment()
        val availableToneMapModes = characteristics.get(CameraCharacteristics.TONEMAP_AVAILABLE_TONE_MAP_MODES)
        specs.addAll(getCameraSpecs(title, availableToneMapModesComment.get(), availableToneMapModes))

        title = "Tone Map Points: "
        val toneMapPoints = characteristics.get(CameraCharacteristics.TONEMAP_MAX_CURVE_POINTS)
        var toneMapPointsTxt = "Not supported programmable Tone Map"
        if (toneMapPoints != null)
            toneMapPointsTxt = title + toneMapPoints.toString()
        specs.add(CameraSpecResult(KEY_NONE, toneMapPointsTxt, NONE))
    }

    private fun read3ACapabilities() {
        val title = "3A: Auto-Exposure, -White balance, -Focus"
        val controlAvailableModesComment = ControlAvailableModesComment(Build.VERSION.SDK_INT)
        val controlAvailableModes = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_MODES)
        specs.addAll(getCameraSpecs(title, controlAvailableModesComment.get(), controlAvailableModes))
    }

    private fun readAwbCapabilities() {
        specs.add(CameraSpecResult(KEY_TITLE, "Auto White Balance Capabilities", NONE))

        val maxRegions = characteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AWB)
        specs.add(CameraSpecResult(KEY_NEWLINE, "AWB max regions: " + maxRegions.toString(), NONE))

        val controlAwbModesComment = ControlAwbModesComment()
        val capabilities = characteristics.get(CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES)
        specs.addAll(getCameraSpecs(KEY_NONE, controlAwbModesComment.get(), capabilities))

        val lockAvailable = characteristics.get(CameraCharacteristics.CONTROL_AWB_LOCK_AVAILABLE)
        if (lockAvailable != null) {
            val checkmark = if (lockAvailable) CHECK else CROSS
            specs.add(CameraSpecResult(KEY_NEWLINE, "AWB Lock Available", checkmark))
        }
    }

    private fun readAfCapabilities() {
        specs.add(CameraSpecResult(KEY_TITLE, "Auto Focus Capabilities", NONE))

        val maxRegions = characteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)
        specs.add(CameraSpecResult(KEY_NEWLINE, "AF max regions: " + maxRegions.toString(), NONE))

        val controlAfModesComment = ControlAfModesComment()
        val capabilities = characteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES)
        specs.addAll(getCameraSpecs(KEY_NONE, controlAfModesComment.get(), capabilities))
    }

    private fun readAeCapabilities() {
        specs.add(CameraSpecResult(KEY_TITLE, "Auto Exposure Capabilities", NONE))

        val maxRegions = characteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AE)
        specs.add(CameraSpecResult(KEY_NEWLINE, "AE max regions: " + maxRegions.toString(), NONE))

        val aeCompensationRangeVal = characteristics.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE)
        val aeCompensationRange = aeCompensationRangeVal?.lower.toString() + " to " + aeCompensationRangeVal?.upper.toString()
        specs.add(CameraSpecResult(KEY_NEWLINE, "AE Compensation Range: " + aeCompensationRange, NONE))

        val aeCompensationStep = characteristics.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_STEP)
        specs.add(CameraSpecResult(KEY_NEWLINE, "AE Compensation Step: " + aeCompensationStep.toString(), NONE))

        val controlAeModesComment = ControlAeModesComment()
        val capabilities = characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES)
        specs.addAll(getCameraSpecs(KEY_NONE, controlAeModesComment.get(), capabilities))

        val aeLockAvailable = characteristics.get(CameraCharacteristics.CONTROL_AE_LOCK_AVAILABLE)
        val checkmark = if (aeLockAvailable == true) CHECK else CROSS
        specs.add(CameraSpecResult(KEY_NEWLINE, "AE Lock", checkmark))

        val controlAeAntibandingModesComment = ControlAeAntibandingModesComment()
        val aeAntibandingModes = characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES)
        specs.addAll(getCameraSpecs(KEY_NONE, controlAeAntibandingModesComment.get(), aeAntibandingModes))
    }

    private fun readControlSceneModes() {
        val title = "Scene Modes"
        val controlSceneModesComment = ControlSceneModesComment()
        val controlSceneModes = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES)
        specs.addAll(getCameraSpecs(title, controlSceneModesComment.get(), controlSceneModes))
    }

    private fun readSceneEffectModes() {
        var title = "Color effects"
        val controlAvailableEffectsComment = ControlAvailableEffectsComment()
        val controlAvailableEffects = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS)
        specs.addAll(getCameraSpecs(title, controlAvailableEffectsComment.get(), controlAvailableEffects))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            title = "Extended scene mode"
            specs.add(CameraSpecResult(KEY_INDENT_PARA, title, NONE))
            val controlAvailableExtendedSceneModesComment = ControlAvailableExtendedSceneModesComment()
            val controlAvailableExtendedSceneModes = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_CAPABILITIES)
            if (controlAvailableExtendedSceneModes != null) {
                controlAvailableExtendedSceneModesComment.get().forEach { p: Pair<Int, String> ->
                    var contain = NONE
                    controlAvailableExtendedSceneModes.forEach {
                        if (it.mode == p.first) {
                            contain = it.mode
                        }
                    }
                    val checkmark = if (contain != NONE) CHECK else CROSS
                    specs.add(CameraSpecResult(KEY_INDENT_PARA, p.second, checkmark))
                }
                // TODO It doesn't work on my Moto g30 so I don't know which one is betterIt doesn't work on my machine so I don't know which one is better
                controlAvailableExtendedSceneModes.forEach {
                    specs.add(CameraSpecResult(KEY_INDENT_PARA, it.toString(), NONE))
                }
            } else
                specs.add(CameraSpecResult(KEY_INDENT_PARA, "Not supported this API", NONE))
        }
    }

    private fun readFaceDetectModes() {
        var title = "Face Detection Modes"
        val availableFaceDetectModesComment = AvailableFaceDetectModesComment()
        val controlSceneModes = characteristics.get(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES)
        specs.addAll(getCameraSpecs(title, availableFaceDetectModesComment.get(), controlSceneModes))

        title = "Max Face Count: "
        val maxFaceCount = characteristics.get(CameraCharacteristics.STATISTICS_INFO_MAX_FACE_COUNT)
        var maxFaceCountTxt = "Not supported Face detection"
        if (maxFaceCount != null)
            maxFaceCountTxt = title + maxFaceCount.toString()
        specs.add(CameraSpecResult(KEY_NONE, maxFaceCountTxt, NONE))
    }

    private fun readZoomParameters() {
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Zoom Capabilities", NONE))

        val digitalZoom = characteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Max Digital Zoom: " + (digitalZoom?:UNKNOWN).toString(), NONE))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val zoomRatio = characteristics.get(CameraCharacteristics.CONTROL_ZOOM_RATIO_RANGE)
            if (zoomRatio != null) {
                val ratio = zoomRatio.lower.toString() + " to " + zoomRatio.upper.toString()
                specs.add(CameraSpecResult(KEY_INDENT_PARA, "Zoom ratio: " + ratio, NONE))
            }
        }

        val scalerCroppingTypesComment = ScalerCroppingTypesComment()
        val zoomTypeKey = characteristics.get(CameraCharacteristics.SCALER_CROPPING_TYPE)
        val zoomTypeTxt = zoomTypeKey?.let { scalerCroppingTypesComment.get(it) }
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Zoom Type: " + zoomTypeTxt, NONE))
    }

    private fun readScalerStreamConfigMap() {
        val configs = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
        val imageFormatsComment = ImageFormatsComment(Build.VERSION.SDK_INT)

        specs.add(CameraSpecResult(KEY_TITLE, "Image Formats", NONE))
        val outputFormats = configs?.outputFormats
        if (outputFormats != null) {
            imageFormatsComment.get().forEach { p: Pair<Int, String> ->
                if (outputFormats.contains(p.first)) {
                    specs.add(CameraSpecResult(KEY_INDENT_PARA, "Output (" + p.first.toString() + ")" + p.second, NONE))
                    val outputSizes = configs.getOutputSizes(p.first)
                    outputSizes.forEachIndexed { index, size ->
                        val outputXY = "[$index]"  + size.width.toString() + "x" + size.height.toString()
                        val minFrame = ", Duration(min frame):" + configs.getOutputMinFrameDuration(p.first, size)
                        val stall = " (stall):" + configs.getOutputStallDuration(p.first, size)
                        specs.add(CameraSpecResult(KEY_INDENT_PARA, outputXY + minFrame + stall, NONE))
                    }
                    specs.add(CameraSpecResult(KEY_RESET, "", NONE))
                }
            }

            imageFormatsComment.get().forEach { p: Pair<Int, String> ->
                if (outputFormats.contains(p.first)) {
                    val highResolutionOutputs = configs.getHighResolutionOutputSizes(p.first)
                    if (highResolutionOutputs.isNotEmpty()) {
                        specs.add(CameraSpecResult(KEY_INDENT_PARA, "High Resolution (" + p.first.toString() + ")" + p.second, NONE))
                        highResolutionOutputs.forEachIndexed { index, size ->
                            val outputXY = "[$index]"  + size.width.toString() + "x" + size.height.toString()
                            val minFrame = ", Duration(min frame):" + configs.getOutputMinFrameDuration(p.first, size)
                            val stall = " (stall):" + configs.getOutputStallDuration(p.first, size)
                            specs.add(CameraSpecResult(KEY_INDENT_PARA, outputXY + minFrame + stall, NONE))
                        }
                        specs.add(CameraSpecResult(KEY_RESET, "", NONE))
                    }
                }
            }
        }

        val inputFormats = configs?.inputFormats
        if (inputFormats != null) {
            imageFormatsComment.get().forEach { p: Pair<Int, String> ->
                if (inputFormats.contains(p.first)) {
                    specs.add(CameraSpecResult(KEY_INDENT_PARA, "Input (" + p.first.toString() + ")" + p.second, NONE))
                    val inputSizes = configs.getInputSizes(p.first)
                    inputSizes.forEachIndexed { index, size ->
                        val outputXY = "[$index]"  + size.width.toString() + "x" + size.height.toString()
                        specs.add(CameraSpecResult(KEY_INDENT_PARA, outputXY, NONE))
                    }
                    specs.add(CameraSpecResult(KEY_RESET, "", NONE))
                }
            }

            imageFormatsComment.get().forEach { p: Pair<Int, String> ->
                if (inputFormats.contains(p.first)) {
                    val outputs4Inputs = configs.getValidOutputFormatsForInput(p.first)
                    if (outputs4Inputs.size > 0) {
                        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Output for Input (" + p.first.toString() + ")" + p.second, NONE))
                        outputs4Inputs.forEachIndexed { index, id ->
                            val io = "[$index] (" + id.toString() + ")" + imageFormatsComment.get(id)
                            specs.add(CameraSpecResult(KEY_INDENT_PARA, io, NONE))
                        }
                        specs.add(CameraSpecResult(KEY_RESET, "", NONE))
                    }
                }
            }
        }

        val videoSizes = configs?.highSpeedVideoSizes
        if (videoSizes != null) {
            specs.add(CameraSpecResult(KEY_INDENT_PARA, "HighSpeedVideoConfigurations", NONE))
            // round robin,
            videoSizes.forEach { size ->
                val fpsRanges = configs.highSpeedVideoFpsRanges
                fpsRanges.forEach { range ->
                    var videoConfigs = size.width.toString() + "x" + size.height.toString()
                    videoConfigs += ", FPS " + range.lower.toString() + " to " + range.upper.toString()
                    specs.add(CameraSpecResult(KEY_INDENT_PARA, videoConfigs, NONE))
                }
            }
            specs.add(CameraSpecResult(KEY_RESET, "", NONE))
        }
    }

    private fun readVideoParameters() {
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Video AE Available FPS Range", NONE))
        characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES)?.forEachIndexed { index: Int, range: Range<Int> ->
            specs.add(CameraSpecResult(KEY_INDENT_PARA, "[$index] " + range.lower.toString() + " to " + range.upper.toString(), NONE))
        }
    }

    companion object {
        const val NONE = -1
        const val CROSS = 0
        const val CHECK = 1

        const val KEY_L_TITLE = "L TITLE"
        const val KEY_TITLE = "TITLE"
        const val KEY_NEWLINE = "NORMAL"
        const val KEY_INDENT_PARA = "INDENT1"
        const val KEY_BRAKE = "BR"
        const val KEY_RESET = "RESET"
        const val KEY_NONE = ""

        const val UNKNOWN = -1
    }
}
// Original Code: TobiasWeis / android-camera2probe / Old school Java
// https://github.com/TobiasWeis/android-camera2probe/wiki
//
// 1st) Ported to Android Studio 4.2.1, API 29 and Java 8 (camera2probe4)
// 2nd) Ported to Kotlin 1.5
// Toyoaki, OHGOCHI  https://twitter.com/Ohgochi/

package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraAccessException
import android.os.Build
import android.content.Context
import android.util.Range
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList
import kotlin.reflect.KClass

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
        var specTxt: MutableList<CameraSpecResult> = ArrayList()
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

            read3ACapabilities()
            readAwbCapabilities()
            readAfCapabilities()
            readAeCapabilities()

            readZoomParameters()

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

        val hwLevelComment =  HardwareLevelsComment()
        val hwlevelKey = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
        val hwLevelTxt = hwlevelKey?.let { hwLevelComment.get(it) }
        specs.add(CameraSpecResult(KEY_NEWLINE, "Hardware Level Support Category: $hwLevelTxt", NONE))

        val infoVersion = characteristics.get(CameraCharacteristics.INFO_VERSION)
        specs.add(CameraSpecResult(KEY_NEWLINE, "Info version: $infoVersion", NONE))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            title = "Rotate and Crop"
            val rotateAndCropModesComment = RotateAndCropModesComment()
            val rotateAndCropModes = characteristics.get(CameraCharacteristics.SCALER_AVAILABLE_ROTATE_AND_CROP_MODES)
            specs.addAll(getCameraSpecs(title, rotateAndCropModesComment.get(), rotateAndCropModes))
        }
        specs.add(CameraSpecResult(KEY_BRAKE, "", NONE))

        title = "Color effects"
        val controlAvailableEffectsComment = ControlAvailableEffectsComment()
        val controlAvailableEffects = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS)
        specs.addAll(getCameraSpecs(title, controlAvailableEffectsComment.get(), controlAvailableEffects))

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

    private fun read3ACapabilities() {
        var title = "3A: Auto-Exposure, -White balance, -Focus"
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

        specs.add(CameraSpecResult(KEY_BRAKE, "", NONE))
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
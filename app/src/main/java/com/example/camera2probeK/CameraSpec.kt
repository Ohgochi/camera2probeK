// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/

package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraAccessException
import android.os.Build
import android.content.Context
import kotlin.collections.ArrayList

class CameraSpec internal constructor(context: Context) {
    private var manager: CameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraIds: Array<String> = manager.cameraIdList
    private var characteristics: CameraCharacteristics = manager.getCameraCharacteristics(cameraIds[0])
    private var specs: MutableList<CameraSpecResult> = ArrayList()

    fun getSpecs(): MutableList<CameraSpecResult> {
        return specs
    }

    fun buildSpecs() {
        readModelInfo()
        for (id in cameraIds) {
            setCharacteristics(id)
            specs.addAll(ReadBasicInfo(characteristics, id).get())

            specs.addAll(ReadLensInfo(characteristics).get())
            specs.addAll(ReadSensorInfo(characteristics).get())

            specs.addAll(Read3AInfo(characteristics).get())

            readControlSceneModes()
            readSceneEffectModes()
            readEdgeEnhancementModes()
            readDistortionCorectionModes()
            readHotPixelModes()

            readFaceDetectModes()

            specs.add(CameraSpecResult(KEY_BRAKE, "", NONE))
            readZoomParameters()

            readScalerStreamConfigMap()
            readJpegThumbnailSizes()

            specs.add(CameraSpecResult(KEY_RESET, "", NONE))
            readVideoParameters()
        }
    }

    private fun setCharacteristics(CameraId: String) {
        try {
            characteristics = manager.getCameraCharacteristics(CameraId)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun getCameraSpecs(title: String, commentList: List<Pair<Int, String>>, funcs: IntArray?) : List<CameraSpecResult> {
        val specTxt: MutableList<CameraSpecResult> = ArrayList()
        if (funcs != null) {
            if (title != KEY_NONE)
                specTxt.add(CameraSpecResult(KEY_TITLE, title, NONE))
            commentList.forEach { comment ->
                val checkmark = if (funcs.contains(comment.first)) CHECK else CROSS
                specTxt.add(CameraSpecResult(KEY_NEWLINE, comment.second, checkmark))
            }
            funcs.forEach { func ->
                var unknownFunc = true
                commentList.forEach one@{ comment ->
                    if (comment.first == func) {
                        unknownFunc = false
                        return@one
                    }
                }
                if (unknownFunc)
                    specTxt.add(CameraSpecResult(KEY_NEWLINE, "[$func]: Unknown Function", SPACE))
            }
        }
        return specTxt
    }

    private fun readModelInfo() {
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "MODEL", NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Model: " + Build.MODEL, NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Manufacturer: " + Build.MANUFACTURER, NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Build version: " + Build.VERSION.RELEASE, NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "SDK version: " + Build.VERSION.SDK_INT.toString(), NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Logical Cameras: " + cameraIds.size.toString(), NONE))
        specs.add(CameraSpecResult(KEY_RESET, "", NONE))
    }

    private fun readControlSceneModes() {
        val title = "Scene Modes"
        val controlSceneModes = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES)
        specs.addAll(getCameraSpecs(title, GetOverviewSceneModes().get(), controlSceneModes))
    }

    private fun readSceneEffectModes() {
        var title = "Color effects"
        val controlAvailableEffects = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS)
        specs.addAll(getCameraSpecs(title, GetOverviewEffects().get(), controlAvailableEffects))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            title = "Extended scene mode"
            specs.add(CameraSpecResult(KEY_INDENT_PARA, title, NONE))
            val extendedSceneModes = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_CAPABILITIES)
            if (extendedSceneModes != null) {
                GetOverviewExtendedSceneModes().get().forEach { comment ->
                    var contain = NONE
                    extendedSceneModes.forEach { mode ->
                        if (mode.mode == comment.first) {
                            contain = mode.mode
                        }
                    }
                    val checkmark = if (contain != NONE) CHECK else CROSS
                    specs.add(CameraSpecResult(KEY_INDENT_PARA, comment.second, checkmark))
                }
                // TODO I haven't confirmed whether only the character information
                //  that can be cut by acquisition with toString () is sufficient.
                extendedSceneModes.forEach {
                    specs.add(CameraSpecResult(KEY_INDENT_PARA, it.toString(), NONE))
                }
            } else
                specs.add(CameraSpecResult(KEY_INDENT_PARA, "Not supported this API", NONE))
        }
    }

    private fun readEdgeEnhancementModes() {
        val title = "Edge Enhancement Modes"
        val edgeEnhancementModesTxt = "Not supported"
        val edgeEnhancementModes = characteristics.get(CameraCharacteristics.EDGE_AVAILABLE_EDGE_MODES)
        if (edgeEnhancementModes != null)
            specs.addAll(getCameraSpecs(title, GetOverviewEdgeEnhancementModes().get(), edgeEnhancementModes))
        else {
            specs.add(CameraSpecResult(KEY_INDENT_PARA, title, NONE))
            specs.add(CameraSpecResult(KEY_INDENT_PARA, edgeEnhancementModesTxt, NONE))
        }
    }

    private fun readDistortionCorectionModes() {
        val title = "Distortion Correction Modes"
        val distortionCorrectionModesTxt = "Not supported"
        val distortionCorrectionModes = characteristics.get(CameraCharacteristics.DISTORTION_CORRECTION_AVAILABLE_MODES)
        if (distortionCorrectionModes != null) {
            specs.addAll(getCameraSpecs(title, GetOverviewDistortionCorrectionModes().get(), distortionCorrectionModes))
        } else {
            specs.add(CameraSpecResult(KEY_INDENT_PARA, title, NONE))
            specs.add(CameraSpecResult(KEY_INDENT_PARA, distortionCorrectionModesTxt, NONE))
        }
    }

    private fun readHotPixelModes() {
        val title = "Hot Pixel Modes"
        val hotPixelModesTxt = "Not supported"
        val hotPixelModes = characteristics.get(CameraCharacteristics.HOT_PIXEL_AVAILABLE_HOT_PIXEL_MODES)
        if (hotPixelModes != null) {
            specs.addAll(getCameraSpecs(title, GetOverviewDistortionCorrectionModes().get(), hotPixelModes))
        } else {
            specs.add(CameraSpecResult(KEY_INDENT_PARA, title, NONE))
            specs.add(CameraSpecResult(KEY_INDENT_PARA, hotPixelModesTxt, NONE))
        }
    }

    private fun readFaceDetectModes() {
        var title = "Face Detection Modes"
        val controlSceneModes = characteristics.get(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES)
        specs.addAll(getCameraSpecs(title, GetOverviewFaceDetectModes().get(), controlSceneModes))

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

        val zoomTypeKey = characteristics.get(CameraCharacteristics.SCALER_CROPPING_TYPE)
        val zoomTypeTxt = zoomTypeKey?.let { GetOverviewScalerCroppingTypes().get(it) }
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Zoom Type: " + zoomTypeTxt, NONE))
    }

    private fun readScalerStreamConfigMap() {
        val configs = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

        specs.add(CameraSpecResult(KEY_TITLE, "Image Formats", NONE))
        val outputFormats = configs?.outputFormats
        var subtitle = "Output ("
        if (outputFormats != null) {
            GetOverviewImageFormats().get().forEach { comment ->
                if (outputFormats.contains(comment.first)) {
                    val outputFormatsTxt = comment.first.toString() + ")" + comment.second
                    specs.add(CameraSpecResult(KEY_INDENT_PARA, subtitle + outputFormatsTxt, NONE))
                    val outputSizes = configs.getOutputSizes(comment.first)
                    outputSizes.forEachIndexed { index, size ->
                        val outputXY = "[$index]"  + size.width.toString() + "x" + size.height.toString()
                        val minFrame = ", Duration(min frame):" + configs.getOutputMinFrameDuration(comment.first, size)
                        val stall = " (stall):" + configs.getOutputStallDuration(comment.first, size)
                        specs.add(CameraSpecResult(KEY_INDENT_PARA, outputXY + minFrame + stall, NONE))
                    }
                    specs.add(CameraSpecResult(KEY_RESET, "", NONE))
                }
            }

            subtitle = "High Resolution ("
            GetOverviewImageFormats().get().forEach {
                if (outputFormats.contains(it.first)) {
                    val highResolutionOutputs = configs.getHighResolutionOutputSizes(it.first)
                    if (highResolutionOutputs.isNotEmpty()) {
                        val highResolutionOutputsTxt = it.first.toString() + ")" + it.second
                        specs.add(CameraSpecResult(KEY_INDENT_PARA,  subtitle + highResolutionOutputsTxt, NONE))
                        highResolutionOutputs.forEachIndexed { index, size ->
                            val outputXY = "[$index]"  + size.width.toString() + "x" + size.height.toString()
                            val minFrame = ", Duration(min frame):" + configs.getOutputMinFrameDuration(it.first, size)
                            val stall = " (stall):" + configs.getOutputStallDuration(it.first, size)
                            specs.add(CameraSpecResult(KEY_INDENT_PARA, outputXY + minFrame + stall, NONE))
                        }
                        specs.add(CameraSpecResult(KEY_RESET, "", NONE))
                    }
                }
            }
        }

        val inputFormats = configs?.inputFormats
        if (inputFormats != null) {
            var subtitle = "Input ("
            GetOverviewImageFormats().get().forEach {
                if (inputFormats.contains(it.first)) {
                    val inputsTxt = it.first.toString() + ")" + it.second
                    specs.add(CameraSpecResult(KEY_INDENT_PARA, subtitle + inputsTxt, NONE))
                    val inputSizes = configs.getInputSizes(it.first)
                    inputSizes.forEachIndexed { index, size ->
                        val outputXY = "[$index]"  + size.width.toString() + "x" + size.height.toString()
                        specs.add(CameraSpecResult(KEY_INDENT_PARA, outputXY, NONE))
                    }
                    specs.add(CameraSpecResult(KEY_RESET, "", NONE))
                }
            }

            subtitle = "Output for Input ("
            GetOverviewImageFormats().get().forEach {
                if (inputFormats.contains(it.first)) {
                    val outputs4Inputs = configs.getValidOutputFormatsForInput(it.first)
                    if (outputs4Inputs.size > 0) {
                        val outputs4InputsTxt = it.first.toString() + ")" + it.second
                        specs.add(CameraSpecResult(KEY_INDENT_PARA, subtitle + outputs4InputsTxt, NONE))
                        outputs4Inputs.forEachIndexed { index, id ->
                            val io = "[$index] (" + id.toString() + ")" + GetOverviewImageFormats().get(id)
                            specs.add(CameraSpecResult(KEY_INDENT_PARA, io, NONE))
                        }
                        specs.add(CameraSpecResult(KEY_RESET, "", NONE))
                    }
                }
            }
        }

        val videoSizes = configs?.highSpeedVideoSizes
        if (videoSizes != null) {
            specs.add(CameraSpecResult(KEY_INDENT_PARA, "HighSpeed Video Configurations", NONE))
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

    private fun readJpegThumbnailSizes() {
        val jpegThumbnailSizes = characteristics.get(CameraCharacteristics.JPEG_AVAILABLE_THUMBNAIL_SIZES)
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Jpeg Thumbnail Sizes", NONE))
        jpegThumbnailSizes?.forEachIndexed { index, size ->
            val sizeTxt = "[$index] " + size.width.toString() + " x " + size.height.toString()
            specs.add(CameraSpecResult(KEY_INDENT_PARA, sizeTxt, NONE))
        }
    }

    private fun readVideoParameters() {
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Video AE Available FPS Range", NONE))
        characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES)?.forEachIndexed { index, range ->
            specs.add(CameraSpecResult(KEY_INDENT_PARA, "[$index] " + range.lower.toString() + " to " + range.upper.toString(), NONE))
        }
    }

    companion object {
        const val NONE = -1
        const val CROSS = 0
        const val CHECK = 1
        const val SPACE = 16

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
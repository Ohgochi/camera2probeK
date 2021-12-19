// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import android.os.Build

class ReadEffectsInfo(characteristics: CameraCharacteristics) : CameraSpecs(characteristics) {
    var specs: MutableList<CameraSpecResult> = ArrayList()

    fun get(): List<CameraSpecResult> {
        readColorEffect()
        readSceneModes()
        readExtendedSceneModes()
        readColorEffect()
        readFaceDetectModes()
        readZoomParameters()

        return specs
    }

    private fun readSceneModes() {
        val title = "Scene Modes"
        val controlSceneModes = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES)
        specs.addAll(getOverviewList(title, GetOverviewSceneModes().get(), controlSceneModes))
    }

    private fun readExtendedSceneModes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val title = "Extended scene mode"
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, title, CameraSpec.NONE))
            val extendedSceneModes = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_CAPABILITIES)
            if (extendedSceneModes != null) {
                GetOverviewExtendedSceneModes().get().forEach { comment ->
                    var contain = CameraSpec.NONE
                    extendedSceneModes.forEach { mode ->
                        if (mode.mode == comment.first) {
                            contain = mode.mode
                        }
                    }
                    val checkmark = if (contain != CameraSpec.NONE) CameraSpec.CHECK else CameraSpec.CROSS
                    specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, comment.second, checkmark))
                }
                // TODO I haven't confirmed whether only the character information
                //  that can be cut by acquisition with toString () is sufficient.
                extendedSceneModes.forEach {
                    specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, it.toString(), CameraSpec.NONE))
                }
            } else
                specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, "Not supported this API", CameraSpec.NONE))
        }
    }

    private fun readColorEffect() {
        val title = "Color effects"
        val controlAvailableEffects = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS)
        specs.addAll(getOverviewList(title, GetOverviewEffects().get(), controlAvailableEffects))
    }

    private fun readFaceDetectModes() {
        var title = "Face Detection Modes"
        val controlSceneModes = characteristics.get(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES)
        specs.addAll(getOverviewList(title, GetOverviewFaceDetectModes().get(), controlSceneModes))

        title = "Max Face Count: "
        val maxFaceCount = characteristics.get(CameraCharacteristics.STATISTICS_INFO_MAX_FACE_COUNT)
        var maxFaceCountTxt = "Not supported Face detection"
        if (maxFaceCount != null)
            maxFaceCountTxt = title + maxFaceCount.toString()
        specs.add(CameraSpecResult(CameraSpec.KEY_NONE, maxFaceCountTxt, CameraSpec.NONE))
    }

    private fun readZoomParameters() {
        specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, "Zoom Capabilities", CameraSpec.NONE))

        val digitalZoom = characteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)
        specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, "Max Digital Zoom: " + (digitalZoom?: CameraSpec.UNKNOWN).toString(), CameraSpec.NONE))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val zoomRatio = characteristics.get(CameraCharacteristics.CONTROL_ZOOM_RATIO_RANGE)
            if (zoomRatio != null) {
                val ratio = zoomRatio.lower.toString() + " to " + zoomRatio.upper.toString()
                specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, "Zoom ratio: " + ratio, CameraSpec.NONE))
            }
        }

        val zoomTypeKey = characteristics.get(CameraCharacteristics.SCALER_CROPPING_TYPE)
        val zoomTypeTxt = zoomTypeKey?.let { GetOverviewScalerCroppingTypes().get(it) }
        specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, "Zoom Type: " + zoomTypeTxt, CameraSpec.NONE))
    }
}
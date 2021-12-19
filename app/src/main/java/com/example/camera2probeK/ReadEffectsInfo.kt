// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import android.os.Build

class ReadEffectsInfo(characteristics: CameraCharacteristics) : CameraSpecs(characteristics) {
    var specs: MutableList<CameraSpecResult> = ArrayList()

    fun get(): List<CameraSpecResult> {
        readControlSceneModes()
        readSceneEffectModes()
        readEdgeEnhancementModes()
        readDistortionCorectionModes()

        return specs
    }

    private fun readControlSceneModes() {
        val title = "Scene Modes"
        val controlSceneModes = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES)
        specs.addAll(getOverviewList(title, GetOverviewSceneModes().get(), controlSceneModes))
    }

    private fun readSceneEffectModes() {
        var title = "Color effects"
        val controlAvailableEffects = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS)
        specs.addAll(getOverviewList(title, GetOverviewEffects().get(), controlAvailableEffects))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            title = "Extended scene mode"
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

    private fun readEdgeEnhancementModes() {
        val title = "Edge Enhancement Modes"
        val edgeEnhancementModesTxt = "Not supported"
        val edgeEnhancementModes = characteristics.get(CameraCharacteristics.EDGE_AVAILABLE_EDGE_MODES)
        if (edgeEnhancementModes != null)
            specs.addAll(getOverviewList(title, GetOverviewEdgeEnhancementModes().get(), edgeEnhancementModes))
        else {
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, title, CameraSpec.NONE))
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, edgeEnhancementModesTxt, CameraSpec.NONE))
        }
    }

    private fun readDistortionCorectionModes() {
        val title = "Distortion Correction Modes"
        val distortionCorrectionModesTxt = "Not supported"
        val distortionCorrectionModes = characteristics.get(CameraCharacteristics.DISTORTION_CORRECTION_AVAILABLE_MODES)
        if (distortionCorrectionModes != null) {
            specs.addAll(getOverviewList(title, GetOverviewDistortionCorrectionModes().get(), distortionCorrectionModes))
        } else {
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, title, CameraSpec.NONE))
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, distortionCorrectionModesTxt, CameraSpec.NONE))
        }
    }
}
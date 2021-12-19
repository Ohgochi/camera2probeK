// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import com.example.camera2probeK.CameraSpec.Companion.NONE

class ReadCorrectionModes(characteristics: CameraCharacteristics) : CameraSpecs(characteristics) {
    var specs: MutableList<CameraSpecResult> = ArrayList()

    fun get(): List<CameraSpecResult> {
        var title = "Corrention Modes"

        title = "Color Correction"
        val colorCorrectionModes = characteristics.get(CameraCharacteristics.COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES)
        specs.addAll(getOverviewList(title, GetOverviewColorCorrectionModes().get(), colorCorrectionModes))

        title = "Noise Reduction"
        val noiseReductionModes = characteristics.get(CameraCharacteristics.NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES)
        specs.addAll(getOverviewList(title, GetOverviewNoiseReductionModes().get(), noiseReductionModes))

        title = "Tone Map Modes"
        val availableToneMapModes = characteristics.get(CameraCharacteristics.TONEMAP_AVAILABLE_TONE_MAP_MODES)
        specs.addAll(getOverviewList(title, GetOverviewToneMapModes().get(), availableToneMapModes))

        title = "Tone Map Points: "
        val toneMapPoints = characteristics.get(CameraCharacteristics.TONEMAP_MAX_CURVE_POINTS)
        var toneMapPointsTxt = "Not supported programmable Tone Map"
        if (toneMapPoints != null)
            toneMapPointsTxt = title + toneMapPoints.toString()
        specs.add(CameraSpecResult(CameraSpec.KEY_NONE, toneMapPointsTxt, NONE))
        specs.add(CameraSpecResult(CameraSpec.KEY_BRAKE, "", NONE))

        readHotPixelModes()
        readEdgeEnhancementModes()
        readDistortionCorectionModes()

        return specs
    }

    private fun readHotPixelModes() {
        val title = "Hot Pixel Modes"
        val hotPixelModesTxt = "Not supported"
        val hotPixelModes = characteristics.get(CameraCharacteristics.HOT_PIXEL_AVAILABLE_HOT_PIXEL_MODES)
        if (hotPixelModes != null) {
            specs.addAll(getOverviewList(title, GetOverviewDistortionCorrectionModes().get(), hotPixelModes))
        } else {
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, title, NONE))
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, hotPixelModesTxt, NONE))
        }
    }

    private fun readEdgeEnhancementModes() {
        val title = "Edge Enhancement Modes"
        val edgeEnhancementModesTxt = "Not supported"
        val edgeEnhancementModes = characteristics.get(CameraCharacteristics.EDGE_AVAILABLE_EDGE_MODES)
        if (edgeEnhancementModes != null)
            specs.addAll(getOverviewList(title, GetOverviewEdgeEnhancementModes().get(), edgeEnhancementModes))
        else {
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, title, NONE))
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, edgeEnhancementModesTxt, NONE))
        }
    }

    private fun readDistortionCorectionModes() {
        val title = "Distortion Correction Modes"
        val distortionCorrectionModesTxt = "Not supported"
        val distortionCorrectionModes = characteristics.get(CameraCharacteristics.DISTORTION_CORRECTION_AVAILABLE_MODES)
        if (distortionCorrectionModes != null) {
            specs.addAll(getOverviewList(title, GetOverviewDistortionCorrectionModes().get(), distortionCorrectionModes))
        } else {
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, title, NONE))
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, distortionCorrectionModesTxt, NONE))
        }
    }

}
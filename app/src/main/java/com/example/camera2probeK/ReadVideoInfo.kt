// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import com.example.camera2probeK.CameraSpec.Companion.NONE

class ReadVideoInfo(characteristics: CameraCharacteristics) : CameraSpecs(characteristics) {
    var specs: MutableList<CameraSpecResult> = ArrayList()

    fun get(): List<CameraSpecResult> {
        readScalerStreamConfigMapVideo()
        readVideoAeFpsRange()
        readVideoStabilization()

        return specs
    }

    private fun readScalerStreamConfigMapVideo() {
        val title = "HighSpeed Video Configurations"
        val configs = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
        val videoSizes = configs?.highSpeedVideoSizes
        if (videoSizes != null) {
            specs.add(CameraSpecResult(CameraSpec.KEY_RESET, "", NONE))
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, title, NONE))
            // round robin,
            videoSizes.forEach { size ->
                val fpsRanges = configs.highSpeedVideoFpsRanges
                fpsRanges.forEach { range ->
                    var videoConfigs = size.width.toString() + "x" + size.height.toString()
                    videoConfigs += ", FPS " + range.lower.toString() + " to " + range.upper.toString()
                    specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, videoConfigs, NONE))
                }
            }
            specs.add(CameraSpecResult(CameraSpec.KEY_RESET, "", NONE))
        }
    }

    private fun readVideoAeFpsRange() {
        val title = "Video AE Available FPS Range"
        specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, title, NONE))
        characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES)?.forEachIndexed { index, range ->
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, "[$index] " + range.lower.toString() + " to " + range.upper.toString(), NONE))
        }
    }

    private fun readVideoStabilization() {
        val title = "Video Stabilization Modes"
        specs.add(CameraSpecResult(CameraSpec.KEY_RESET, "title", NONE))
        specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, title, NONE))
        val modes = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES)
        var modesText = "Could not get"
        if (modes != null)
            modesText = GetOverviewVideoStabilizationMode().get(modes[0])
        specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, modesText, NONE))
    }
}
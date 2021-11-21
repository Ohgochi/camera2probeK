// OHGOCHI, Toyoaki @Ohgochi
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class NoiseReductionModesComment : CameraSpecsComment  {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.NOISE_REDUCTION_MODE_OFF, "No Noise Reduction"),
        Pair(CameraMetadata.NOISE_REDUCTION_MODE_FAST, "FAST: Capture rate priority"),
        Pair(CameraMetadata.NOISE_REDUCTION_MODE_HIGH_QUALITY, "HIGH QUALITY: Quality priority"),
        Pair(CameraMetadata.NOISE_REDUCTION_MODE_MINIMAL, "MINIMAL: Only sensor raw domain basic NR"),
        Pair(CameraMetadata.NOISE_REDUCTION_MODE_ZERO_SHUTTER_LAG, "ZERO SHUTTER LAG: Post-processing for the buffer"),
    )
}
// OHGOCHI, Toyoaki @Ohgochi
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class ColorCorrectionModesComment : CameraSpecsComment {
    override var comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_OFF, "No aberration correction is applied"),
        Pair(CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_FAST, "Reference to Last frame (LEGACY)"),
        Pair(CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_HIGH_QUALITY, "Quality priority Color correction"),
    )
}
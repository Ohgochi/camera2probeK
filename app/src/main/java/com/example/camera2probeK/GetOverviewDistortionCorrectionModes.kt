// OHGOCHI, Toyoaki  https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class GetOverviewDistortionCorrectionModes : GetOverviewCameraSpecs {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.DISTORTION_CORRECTION_MODE_OFF, "Mode OFF"),
        Pair(CameraMetadata.DISTORTION_CORRECTION_MODE_FAST, "FAST: Applied w/o reducing frame rate"),
        Pair(CameraMetadata.DISTORTION_CORRECTION_MODE_HIGH_QUALITY, "High Quality"),
    )
}
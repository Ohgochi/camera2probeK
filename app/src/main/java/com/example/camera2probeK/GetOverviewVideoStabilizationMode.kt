// OHGOCHI, Toyoaki  https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class GetOverviewVideoStabilizationMode : GetOverviewCameraSpecs {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.CONTROL_VIDEO_STABILIZATION_MODE_OFF, "disabled"),
        Pair(CameraMetadata.CONTROL_VIDEO_STABILIZATION_MODE_ON, "enabled"),
    )
}
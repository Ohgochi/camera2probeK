// OHGOCHI, Toyoaki @Ohgochi
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class GetOverviewAeAntibandingModes : GetOverviewCameraSpecs {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_OFF, "Antibanding disabled"),
        Pair(CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_50HZ, "Antibanding for 50Hz illumination"),
        Pair(CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_60HZ, "Antibanding for 60Hz illumination"),
        Pair(CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_AUTO, "Antibanding Auto"),
    )
}
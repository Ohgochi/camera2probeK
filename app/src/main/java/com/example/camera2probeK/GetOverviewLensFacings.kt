// OHGOCHI, Toyoaki @Ohgochi
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class GetOverviewLensFacings : GetOverviewCameraSpecs {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.LENS_FACING_FRONT, "Front Camera"),
        Pair(CameraMetadata.LENS_FACING_BACK, "Back Camera"),
        Pair(CameraMetadata.LENS_FACING_EXTERNAL, "External Camera"),
    )
}
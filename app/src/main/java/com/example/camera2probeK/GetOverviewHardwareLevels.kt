// OHGOCHI, Toyoaki @Ohgochi
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class GetOverviewHardwareLevels : GetOverviewCameraSpecs {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED, "LIMITED"),
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_FULL, "FULL"),
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY, "LEGACY"),
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_3, "LEVEL_3"),
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_EXTERNAL, "EXTERNAL"),
    )
}
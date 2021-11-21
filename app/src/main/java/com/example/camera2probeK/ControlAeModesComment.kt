// OHGOCHI, Toyoaki @Ohgochi
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class ControlAeModesComment : CameraSpecsComment {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.CONTROL_AE_MODE_OFF, "Auto Exposure disabled"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON, "Auto Exposure active"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH, "Flash unit, firing it in low-light conditions"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON_ALWAYS_FLASH, "Flash unit, always firing"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE, "Automatic red eye reduction"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON_EXTERNAL_FLASH, "External flash has been turned on")
    )
}
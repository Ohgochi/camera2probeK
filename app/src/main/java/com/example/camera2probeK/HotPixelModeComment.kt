// OHGOCHI, Toyoaki  https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class HotPixelModeComment : CameraSpecsComment {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.HOT_PIXEL_MODE_OFF, "Mode OFF"),
        Pair(CameraMetadata.HOT_PIXEL_MODE_FAST, "FAST: Don't slow down frame rate"),
        Pair(CameraMetadata.HOT_PIXEL_MODE_HIGH_QUALITY, "HQ (possibly low frame rate)"),
    )
}
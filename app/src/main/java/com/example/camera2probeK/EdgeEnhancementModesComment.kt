// OHGOCHI, Toyoaki  https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class EdgeEnhancementModesComment : CameraSpecsComment {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.EDGE_MODE_OFF, "Mode OFF"),
        Pair(CameraMetadata.EDGE_MODE_FAST, "FAST: Don't slow down frame rate"),
        Pair(CameraMetadata.EDGE_MODE_HIGH_QUALITY, "HQ (possibly low frame rate)"),
        Pair(CameraMetadata.EDGE_MODE_ZERO_SHUTTER_LAG, "Zero shutter lag"),
    )
}
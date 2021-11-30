// OHGOCHI, Toyoaki  https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class SyncMaxLatencyComment : CameraSpecsComment {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.SYNC_MAX_LATENCY_UNKNOWN , "Requests immediately applied"),
        Pair(CameraMetadata.SYNC_MAX_LATENCY_PER_FRAME_CONTROL, "Unknown Latency"),
    )
}
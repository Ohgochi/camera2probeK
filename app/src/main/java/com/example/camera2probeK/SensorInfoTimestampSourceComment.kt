// OHGOCHI, Toyoaki  https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class SensorInfoTimestampSourceComment : CameraSpecsComment {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.SENSOR_INFO_TIMESTAMP_SOURCE_UNKNOWN, "UNKNOWN"),
        Pair(CameraMetadata.SENSOR_INFO_TIMESTAMP_SOURCE_REALTIME, "By system clock"),
    )
}
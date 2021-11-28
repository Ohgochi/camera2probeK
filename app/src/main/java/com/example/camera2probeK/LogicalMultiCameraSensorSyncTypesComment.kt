// OHGOCHI, Toyoaki  https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class LogicalMultiCameraSensorSyncTypesComment : CameraSpecsComment {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.LOGICAL_MULTI_CAMERA_SENSOR_SYNC_TYPE_APPROXIMATE, "APPROXIMATE sync"),
        Pair(CameraMetadata.LOGICAL_MULTI_CAMERA_SENSOR_SYNC_TYPE_CALIBRATED, "Frame sync at the hardware level"),
    )
}
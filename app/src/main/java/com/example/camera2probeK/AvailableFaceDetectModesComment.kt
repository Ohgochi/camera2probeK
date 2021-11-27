package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class AvailableFaceDetectModesComment : CameraSpecsComment {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.STATISTICS_FACE_DETECT_MODE_OFF, "Do not include face detection statistics"),
        Pair(CameraMetadata.STATISTICS_FACE_DETECT_MODE_SIMPLE, "Return face rectangle and confidence values only"),
        Pair(CameraMetadata.STATISTICS_FACE_DETECT_MODE_FULL, "Return all face metadata"),
    )
}
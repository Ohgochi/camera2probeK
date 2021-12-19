// OHGOCHI, Toyoaki @Ohgochi
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class GetOverviewAfModes : GetOverviewCameraSpecs {
    override var comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.CONTROL_AF_MODE_OFF, "AF Disabled"),
        Pair(CameraMetadata.CONTROL_AF_MODE_AUTO, "Basic automatic focus mode"),
        Pair(CameraMetadata.CONTROL_AF_MODE_MACRO, "Close-up focusing mode"),
        Pair(CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_VIDEO, "Constantly-in-focus VIDEO stream"),
        Pair(CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_PICTURE, "Constantly-in-focus PICTURE stream"),
        Pair(CameraMetadata.CONTROL_AF_MODE_EDOF, "Extended depth of field mode")
    )
}
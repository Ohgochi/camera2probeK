// OHGOCHI, Toyoaki @Ohgochi
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class GetOverviewAwbModes : GetOverviewCameraSpecs {
    override var comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.CONTROL_AWB_MODE_AUTO, "AWB Active"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_OFF, "AWB Disabled"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_DAYLIGHT, "AWB Disabled (Daylight light: CIE D65)"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_CLOUDY_DAYLIGHT, "AWB Disabled (Cloudy daylight light)"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_TWILIGHT, "AWB Disabled (Twilight light)"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_INCANDESCENT, "AWB Disabled (Incandescent light: CIE A)"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_SHADE, "AWB Disabled (Shade light)"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_FLUORESCENT, "AWB Disabled (Fluorescent light: CIE F2)"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_WARM_FLUORESCENT, "AWB Disabled (Warm fluorescent light: CIE F4)")
    )
}
// OHGOCHI, Toyoaki  https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class GetOverviewTestPatternModes : GetOverviewCameraSpecs  {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.SENSOR_TEST_PATTERN_MODE_OFF , "No test pattern mode"),
        Pair(CameraMetadata.SENSOR_TEST_PATTERN_MODE_SOLID_COLOR, "Each pixel in [R, G_even, G_odd, B]"),
        Pair(CameraMetadata.SENSOR_TEST_PATTERN_MODE_COLOR_BARS , "Test pattern is similar to COLOR_BARS"),
        Pair(CameraMetadata.SENSOR_TEST_PATTERN_MODE_COLOR_BARS_FADE_TO_GRAY, "Test pattern similar COLOR_BARS fade to gray"),
        Pair(CameraMetadata.SENSOR_TEST_PATTERN_MODE_PN9 , "Pseudo-random sequence generated from a PN9 512-bit sequence"),
        Pair(CameraMetadata.SENSOR_TEST_PATTERN_MODE_CUSTOM1, "Custom test pattern"),
    )
}
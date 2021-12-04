package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class ColorFilterArrangementComment : CameraSpecsComment {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_RGGB , "No test pattern mode"),
        Pair(CameraMetadata.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_GRBG, "GRBG"),
        Pair(CameraMetadata.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_GBRG , "GBRG"),
        Pair(CameraMetadata.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_BGGR, "BGGR"),
        Pair(CameraMetadata.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_RGB , "RGB 3 x 16bit"),
        Pair(CameraMetadata.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_MONO, "Monochrome"),
        Pair(CameraMetadata.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_NIR, "Near Infrared"),
    )
}
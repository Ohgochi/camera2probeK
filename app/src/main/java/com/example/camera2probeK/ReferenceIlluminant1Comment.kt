// OHGOCHI, Toyoaki  https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class ReferenceIlluminant1Comment : CameraSpecsComment {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_DAYLIGHT, "DAYLIGHT"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_FLUORESCENT, "FLUORESCENT: D 5700 - 7100K"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_TUNGSTEN, "TUNGSTEN"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_FLASH, "FLASH"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_FINE_WEATHER, "FINE WEATHER"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_CLOUDY_WEATHER, "CLOUDY WEATHER"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_SHADE, "SHADE"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_DAYLIGHT_FLUORESCENT, "DAYLIGHT FLUORESCENT: D 5700 - 7100K"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_DAY_WHITE_FLUORESCENT, "DAY WHITE FLUORESCENT: N 4600 - 5400K"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_COOL_WHITE_FLUORESCENT, "COOL WHITE FLUORESCENT: W 3900 - 4500K"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_WHITE_FLUORESCENT, "WHITE FLUORESCENT: WW 3200 - 3700K"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_STANDARD_A, "STANDARD A"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_STANDARD_B, "STANDARD B"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_STANDARD_C, "STANDARD C"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_D50, "D50"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_D55, "D55"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_D65, "D65"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_D75, "D75"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_ISO_STUDIO_TUNGSTEN, ""),
    )
}
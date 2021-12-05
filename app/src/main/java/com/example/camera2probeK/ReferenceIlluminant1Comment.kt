package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class ReferenceIlluminant1Comment : CameraSpecsComment {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_DAYLIGHT, ""),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_FLUORESCENT, "PORTRAIT: Still photos of people"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_TUNGSTEN, "PORTRAIT: Still photos of people"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_FLASH, "PORTRAIT: Still photos of people"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_FINE_WEATHER, "Optimized for distant macroscopic objects"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_CLOUDY_WEATHER, "NIGHT: Low-light settings"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_SHADE, "NIGHT PORTRAIT: People in low-light settings"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_DAYLIGHT_FLUORESCENT, "THEATRE: Dim, indoor settings where flash off"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_DAY_WHITE_FLUORESCENT, "BEACH: Bright, outdoor beach"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_COOL_WHITE_FLUORESCENT, "SNOW: bright settings containing snow"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_WHITE_FLUORESCENT, "SUNSET: Optimized for scenes of the setting sun"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_STEADYPHOTO, "STEADY PHOTO: Avoid blurry photos"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_STANDARD_A, "Nighttime photos of fireworks"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_STANDARD_B, "ACTION(SPORTS): For Quickly moving people"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_STANDARD_C, "SPORTS(ACTION): For Quickly moving people"),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_D50, ""),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_D55, ""),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_D65, ""),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_D75, ""),
        Pair(CameraMetadata.SENSOR_REFERENCE_ILLUMINANT1_ISO_STUDIO_TUNGSTEN, ""),
    )
}
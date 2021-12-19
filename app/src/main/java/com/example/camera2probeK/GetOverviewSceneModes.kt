// OHGOCHI, Toyoaki  https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class GetOverviewSceneModes : GetOverviewCameraSpecs {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.CONTROL_SCENE_MODE_DISABLED, "No scene modes"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_FACE_PRIORITY, "3A for face detection"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_PORTRAIT, "PORTRAIT: Still photos of people"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_LANDSCAPE, "Optimized for distant macroscopic objects"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_NIGHT, "NIGHT: Low-light settings"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_NIGHT_PORTRAIT, "NIGHT PORTRAIT: People in low-light settings"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_THEATRE, "THEATRE: Dim, indoor settings where flash off"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_BEACH, "BEACH: Bright, outdoor beach"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_SNOW, "SNOW: bright settings containing snow"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_SUNSET, "SUNSET: Optimized for scenes of the setting sun"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_STEADYPHOTO, "STEADY PHOTO: Avoid blurry photos"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_FIREWORKS, "Nighttime photos of fireworks"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_ACTION, "ACTION(SPORTS): For Quickly moving people"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_SPORTS, "SPORTS(ACTION): For Quickly moving people"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_PARTY, "PARTY: Dim, indoor with multiple moving people."),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_CANDLELIGHT, "CANDLELIGHT: Dim, main light source is a candle"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_BARCODE, "BARCODE: Optimized for capturing barcode"),
        // Pair(CameraMetadata.CONTROL_SCENE_MODE_HIGH_SPEED_VIDEO, "Deprecated : For over 60fps video"),
        Pair(CameraMetadata.CONTROL_SCENE_MODE_HDR, "HDR: High dynamic range"),
    )
}
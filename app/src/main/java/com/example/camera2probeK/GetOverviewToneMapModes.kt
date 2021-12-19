// OHGOCHI, Toyoaki  https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class GetOverviewToneMapModes : GetOverviewCameraSpecs {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.TONEMAP_MODE_CONTRAST_CURVE, "Use the tone mapping curve specified"),
        Pair(CameraMetadata.TONEMAP_MODE_FAST, "Advanced gamma mapping and color enhancement"),
        Pair(CameraMetadata.TONEMAP_MODE_HIGH_QUALITY, "High-Q gamma mapping and color enhancement"),
        Pair(CameraMetadata.TONEMAP_MODE_GAMMA_VALUE, "Tone map with ganma value"),
        Pair(CameraMetadata.TONEMAP_MODE_PRESET_CURVE, "Preset Tone Map"),
    )
}
// OHGOCHI, Toyoaki @Ohgochi
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class GetOverviewEffects : GetOverviewCameraSpecs {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.CONTROL_EFFECT_MODE_AQUA, "Aqua"),
        Pair(CameraMetadata.CONTROL_EFFECT_MODE_BLACKBOARD, "Blackboard"),
        Pair(CameraMetadata.CONTROL_EFFECT_MODE_MONO, "Mono color"),
        Pair(CameraMetadata.CONTROL_EFFECT_MODE_NEGATIVE, "Photo-negative"),
        Pair(CameraMetadata.CONTROL_EFFECT_MODE_OFF, "No color effect"),
        Pair(CameraMetadata.CONTROL_EFFECT_MODE_POSTERIZE, "Posterization"),
        Pair(CameraMetadata.CONTROL_EFFECT_MODE_SEPIA, "Sepia"),
        Pair(CameraMetadata.CONTROL_EFFECT_MODE_SOLARIZE, "Solarisation"),
        Pair(CameraMetadata.CONTROL_EFFECT_MODE_WHITEBOARD, "Whiteboard"),
    )
}
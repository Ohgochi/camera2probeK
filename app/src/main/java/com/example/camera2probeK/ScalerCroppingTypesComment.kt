// OHGOCHI, Toyoaki @Ohgochi
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata

class ScalerCroppingTypesComment : CameraSpecsComment {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.SCALER_CROPPING_TYPE_CENTER_ONLY, "Only supports centered crop regions"),
        Pair(CameraMetadata.SCALER_CROPPING_TYPE_FREEFORM, "Supports arbitrarily chosen crop regions"),
    )
}
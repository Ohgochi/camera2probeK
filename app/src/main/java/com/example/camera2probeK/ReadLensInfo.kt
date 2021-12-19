package com.example.camera2probeK

import android.Manifest
import android.hardware.camera2.CameraCharacteristics
import permissions.dispatcher.NeedsPermission

class ReadLensInfo(characteristics: CameraCharacteristics) : CameraSpecs(characteristics) {

    @NeedsPermission(Manifest.permission.CAMERA)
    fun get(): List<CameraSpecResult> {
        val specs: MutableList<CameraSpecResult> = ArrayList()

        return specs
    }

}
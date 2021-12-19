// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/

package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraAccessException
import android.os.Build
import android.content.Context
import kotlin.collections.ArrayList

class CameraSpec internal constructor(context: Context) {
    private var manager: CameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraIds: Array<String> = manager.cameraIdList
    private var characteristics: CameraCharacteristics = manager.getCameraCharacteristics(cameraIds[0])
    private var specs: MutableList<CameraSpecResult> = ArrayList()

    fun getSpecs(): MutableList<CameraSpecResult> {
        return specs
    }

    fun buildSpecs() {
        readModelInfo()
        for (id in cameraIds) {
            setCharacteristics(id)

            // TODO I may have misunderstood creating instances of classes
            specs.addAll(ReadBasicInfo(characteristics, id).get())
            specs.addAll(ReadLensInfo(characteristics).get())
            specs.addAll(ReadSensorInfo(characteristics).get())
            specs.addAll(ReadCorrectionModes(characteristics).get())
            specs.addAll(Read3AInfo(characteristics).get())
            specs.addAll(ReadEffectsInfo(characteristics).get())
            specs.addAll(ReadScalerStreamConfigMap(characteristics).get())
            specs.addAll(ReadVideoInfo(characteristics).get())
        }
    }

    private fun setCharacteristics(CameraId: String) {
        try {
            characteristics = manager.getCameraCharacteristics(CameraId)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun readModelInfo() {
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "MODEL", NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Model: " + Build.MODEL, NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Manufacturer: " + Build.MANUFACTURER, NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Build version: " + Build.VERSION.RELEASE, NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "SDK version: " + Build.VERSION.SDK_INT.toString(), NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Logical Cameras: " + cameraIds.size.toString(), NONE))
        specs.add(CameraSpecResult(KEY_RESET, "", NONE))
    }

    companion object {
        const val NONE = -1
        const val CROSS = 0
        const val CHECK = 1
        const val SPACE = 16

        const val KEY_L_TITLE = "L TITLE"
        const val KEY_TITLE = "TITLE"
        const val KEY_NEWLINE = "NORMAL"
        const val KEY_INDENT_PARA = "INDENT1"
        const val KEY_BRAKE = "BR"
        const val KEY_RESET = "RESET"
        const val KEY_NONE = ""

        const val UNKNOWN = -1
    }
}
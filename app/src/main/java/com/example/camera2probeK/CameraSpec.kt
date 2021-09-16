// Original Code: TobiasWeis / android-camera2probe / Old school Java
// https://github.com/TobiasWeis/android-camera2probe/wiki
//
// 1st) Ported to Android Studio 4.2.1, API 29 and Java 8 (camera2probe4)
// 2nd) Ported to Kotlin 1.5
// Toyoaki, OHGOCHI  https://twitter.com/Ohgochi/

package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraAccessException
import android.os.Build
import android.content.Context
import androidx.core.util.Pair
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class CameraSpec internal constructor(context: Context) {
    private var manager: CameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraIds: Array<String> = manager.cameraIdList
    private var characteristics: CameraCharacteristics? = null
    private var specs: MutableList<CameraSpecResult> = ArrayList()

    private fun setCharacteristics(CameraId: String) {
        try {
            characteristics = manager.getCameraCharacteristics(CameraId)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    fun getSpecs(): MutableList<CameraSpecResult> {
        return specs
    }

    fun buildSpecs() {
        readModelInfo()
        for (id in cameraIds) {
            specs.add(CameraSpecResult(KEY_LOGICAL, id, NONE))
            setCharacteristics(id)
            readHwLevel()
            readAvailableCapabilities()
            readAwbCapabilities()
            readAfCapabilities()
            readAeCapabilities()
        }
    }

    private fun readModelInfo() {
        specs.add(CameraSpecResult(KEY_TITLE, "MODEL", NONE))
        specs.add(CameraSpecResult("Model", Build.MODEL, NONE))
        specs.add(CameraSpecResult("Manufacturer", Build.MANUFACTURER, NONE))
        specs.add(CameraSpecResult("Build version", Build.VERSION.RELEASE, NONE))
        specs.add(CameraSpecResult("SDK version", Build.VERSION.SDK_INT.toString(), NONE))
        specs.add(CameraSpecResult("Logical Cameras", cameraIds.size.toString(), NONE))
    }

    private fun readHwLevel() {
        val hwlevel = characteristics?.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
        specs.add(CameraSpecResult(KEY_TITLE, "Hardware Level Support Category", NONE))
        specs.add(CameraSpecResult("Category", CameraSpecsComment.getInfoSupportedHardwareLevelComment(hwlevel?:UNKNOWN), NONE))
    }

    private fun readAvailableCapabilities() {
        val capabilities = characteristics?.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)
        if (capabilities != null) {
            val capabilitiesList = Arrays.stream(capabilities).boxed().collect(Collectors.toList()) as List<Int?>
            specs.add(CameraSpecResult(KEY_TITLE, "Request Available Capabilities", NONE))
            CameraSpecsComment.getRequestAvailableCapabilitiesComment()
                .forEach(Consumer { p: Pair<Int, String> ->
                    val checkmark = if (capabilitiesList.contains(p.first)) CHECK else CROSS
                    specs.add(CameraSpecResult(p.second, "", checkmark))
                })
        }
    }

    private fun readAwbCapabilities() {
        val capabilities = characteristics?.get(CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES)
        if (capabilities != null) {
            val capabilitiesList =
                Arrays.stream(capabilities).boxed().collect(Collectors.toList()) as List<Int?>
            specs.add(CameraSpecResult(KEY_TITLE, "Auto White Balance Capabilities", NONE))
            CameraSpecsComment.getControlAwbModeComment()
                .forEach(Consumer { p: Pair<Int, String> ->
                    val checkmark = if (capabilitiesList.contains(p.first)) CHECK else CROSS
                    specs.add(CameraSpecResult(p.second, "", checkmark))
                })
        }
    }

    private fun readAfCapabilities() {
        val capabilities = characteristics?.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES)
        if (capabilities != null) {
            val capabilitiesList =
                Arrays.stream(capabilities).boxed().collect(Collectors.toList()) as List<Int?>
            specs.add(CameraSpecResult(KEY_TITLE, "Auto Focus Capabilities", NONE))
            CameraSpecsComment.getControlAfModeComment()
                .forEach(Consumer { p: Pair<Int, String> ->
                    val checkmark = if (capabilitiesList.contains(p.first)) CHECK else CROSS
                    specs.add(CameraSpecResult(p.second, "", checkmark))
                })
        }
    }

    private fun readAeCapabilities() {
        val capabilities = characteristics?.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES)
        if (capabilities != null) {
            val capabilitiesList =
                Arrays.stream(capabilities).boxed().collect(Collectors.toList()) as List<Int?>
            specs.add(CameraSpecResult(KEY_TITLE, "Auto Exposure Capabilities", NONE))
            CameraSpecsComment.getControlAeModeComment()
                .forEach(Consumer { p: Pair<Int, String> ->
                    val checkmark = if (capabilitiesList.contains(p.first)) CHECK else CROSS
                    specs.add(CameraSpecResult(p.second, "", checkmark))
                })
        }
    }

    companion object {
        const val NONE = -1
        const val CROSS = 0
        const val CHECK = 1

        const val KEY_TITLE = "TITLE"
        const val KEY_LOGICAL = "LOGICAL CAMERA"
        const val KEY_PHYSICAL = "PHYSICAL CAMERA"

        const val UNKNOWN = -1
    }

}
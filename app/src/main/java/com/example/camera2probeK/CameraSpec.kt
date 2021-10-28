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
import android.util.Range
import androidx.core.util.Pair
import com.example.camera2probeK.CameraSpecsComment.afMode
import com.example.camera2probeK.CameraSpecsComment.getComment
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class CameraSpec internal constructor(context: Context) {
    private var manager: CameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraIds: Array<String> = manager.cameraIdList
    private var characteristics: CameraCharacteristics = manager.getCameraCharacteristics(cameraIds[0])
    private var specs: MutableList<CameraSpecResult> = ArrayList()

    private fun setCharacteristics(CameraId: String) {
        try {
            characteristics = manager.getCameraCharacteristics(CameraId)
            CameraSpecsComment.setupLists()
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
            setCharacteristics(id)
            readCameraInfo(id)

            readHwLevel()
            readZoomParameters()
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

    private fun readCameraInfo(id : String) {
        val cameraLensFacing = characteristics.get(CameraCharacteristics.LENS_FACING)
        val cameraTitle: String = id + ": " + getComment(CameraSpecsComment.lensFacing, cameraLensFacing?: UNKNOWN)
        specs.add(CameraSpecResult(KEY_LOGICAL, cameraTitle, NONE))
    }

    private fun readHwLevel() {
        val hwlevel = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
        specs.add(CameraSpecResult(KEY_TITLE, "Hardware Level Support Category", NONE))
        specs.add(CameraSpecResult("Category", getComment(CameraSpecsComment.hwLevel, hwlevel?:UNKNOWN), NONE))
    }

    private fun readZoomParameters() {
        specs.add(CameraSpecResult(KEY_TITLE, "Camera Capabilities", NONE))

        val digitalZoom = characteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)
        specs.add(CameraSpecResult("Max Digital Zoom", (digitalZoom?:UNKNOWN).toString(), NONE))

        val zoomType = characteristics.get(CameraCharacteristics.SCALER_CROPPING_TYPE)
        specs.add(CameraSpecResult("Zoom Type", getComment(CameraSpecsComment.scalerCroppingType, zoomType?:UNKNOWN), NONE))
    }

    private fun readAvailableCapabilities() {
        val capabilities = characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)
        if (capabilities != null) {
            val capabilitiesList = Arrays.stream(capabilities).boxed().collect(Collectors.toList()) as List<Int?>
            specs.add(CameraSpecResult(KEY_TITLE, "Request Available Capabilities", NONE))
            getComment(CameraSpecsComment.availableCapabilities).forEach{ p: Pair<Int, String> ->
                val checkmark = if (capabilitiesList.contains(p.first)) CHECK else CROSS
                specs.add(CameraSpecResult(p.second, "", checkmark))
            }
        }
    }

    private fun readAwbCapabilities() {
        val capabilities = characteristics.get(CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES)
        if (capabilities != null) {
            val capabilitiesList = Arrays.stream(capabilities).boxed().collect(Collectors.toList()) as List<Int?>
            specs.add(CameraSpecResult(KEY_TITLE, "Auto White Balance Capabilities", NONE))
            getComment(CameraSpecsComment.awbMode).forEach{ p: Pair<Int, String> ->
                val checkmark = if (capabilitiesList.contains(p.first)) CHECK else CROSS
                specs.add(CameraSpecResult(p.second, "", checkmark))
            }
        }
    }

    private fun readAfCapabilities() {
        val capabilities = characteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES)
        if (capabilities != null) {
            val capabilitiesList = Arrays.stream(capabilities).boxed().collect(Collectors.toList()) as List<Int?>
            specs.add(CameraSpecResult(KEY_TITLE, "Auto Focus Capabilities", NONE))
            getComment(afMode).forEach{ p: Pair<Int, String> ->
                val checkmark = if (capabilitiesList.contains(p.first)) CHECK else CROSS
                specs.add(CameraSpecResult(p.second, "", checkmark))
            }
        }
    }

    private fun readAeCapabilities() {
        val capabilities = characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES)
        if (capabilities != null) {
            val capabilitiesList = Arrays.stream(capabilities).boxed().collect(Collectors.toList()) as List<Int?>
            specs.add(CameraSpecResult(KEY_TITLE, "Auto Exposure Capabilities", NONE))
            getComment(CameraSpecsComment.aeMode).forEach { p: Pair<Int, String> ->
                val checkmark = if (capabilitiesList.contains(p.first)) CHECK else CROSS
                specs.add(CameraSpecResult(p.second, "", checkmark))
            }
        }

        characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES)?.forEachIndexed() { index: Int, p: Range<Int> ->
            specs.add(CameraSpecResult("AE Available FPS Range", "[$index] " + p.lower.toString() + " to " + p.upper.toString(), NONE))
        }
    }

    companion object {
        const val NONE = -1.0f
        const val CROSS = 0.0f
        const val CHECK = 1.0f

        const val KEY_TITLE = "TITLE"
        const val KEY_LOGICAL = "LOGICAL CAMERA"
        const val KEY_PHYSICAL = "PHYSICAL CAMERA"

        const val UNKNOWN = -1
    }

}
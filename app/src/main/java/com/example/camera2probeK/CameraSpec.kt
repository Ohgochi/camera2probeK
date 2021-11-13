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
import android.util.Size
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

            readZoomParameters()
            readAvailableCapabilities()
            readAwbCapabilities()
            readAfCapabilities()
            readAeCapabilities()

            readVideoParameters()
        }
    }

    private fun readModelInfo() {
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "MODEL", NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Model: " + Build.MODEL, NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Manufacturer: " + Build.MANUFACTURER, NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Build version: " + Build.VERSION.RELEASE, NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "SDK version: " + Build.VERSION.SDK_INT.toString(), NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Logical Cameras: " + cameraIds.size.toString(), NONE))
    }

    private fun readCameraInfo(id : String) {
        val cameraLensFacing = characteristics.get(CameraCharacteristics.LENS_FACING)
        val cameraTitle: String = getComment(CameraSpecsComment.lensFacing, cameraLensFacing?: UNKNOWN)
        specs.add(CameraSpecResult(KEY_L_TITLE, "LOGICAL CAMERA[" + id + "] " + cameraTitle, NONE))

        // This API is not properly implemented on many models
        val physicalCameraIds = characteristics.physicalCameraIds
        var cameras = "API not implemented"
        if (physicalCameraIds.size != 0) cameras = physicalCameraIds.size.toString()
        specs.add(CameraSpecResult(KEY_NEWLINE, "Physical Cameras: " + cameras, NONE))

        val hwlevelVal = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
        val hwLevel = getComment(CameraSpecsComment.hwLevel, hwlevelVal?:UNKNOWN)
        specs.add(CameraSpecResult(KEY_NEWLINE,"Hardware Level Support Category: " + hwLevel, NONE))

        specs.add(CameraSpecResult(KEY_BRAKE, "", NONE))

        specs.add(CameraSpecResult(KEY_TITLE, "Image Formats", NONE))
        val configs = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

        val outputFormats = configs?.outputFormats
        if (outputFormats != null) {
            getComment(CameraSpecsComment.imageFormat).forEach { p: Pair<Int, String> ->
                if (outputFormats.contains(p.first)) {
                    specs.add(CameraSpecResult(KEY_INDENT_PARA, "Output (" + p.first.toString() + ")" + p.second, NONE))
                    val outputSizes = configs.getOutputSizes(p.first)
                    outputSizes.forEachIndexed { index, size ->
                        val outputXY = "[$index]"  + size.width.toString() + "x" + size.height.toString()
                        val minFrame = ", Duration(min frame):" + configs.getOutputMinFrameDuration(p.first, size)
                        val stall = " (stall):" + configs.getOutputStallDuration(p.first, size)
                        specs.add(CameraSpecResult(KEY_INDENT_PARA, outputXY + minFrame + stall, NONE))
                    }
                    specs.add(CameraSpecResult(KEY_RESET, "", NONE))
                }
            }

            getComment(CameraSpecsComment.imageFormat).forEach { p: Pair<Int, String> ->
                if (outputFormats.contains(p.first)) {
                    val highResolutionOutputs = configs.getHighResolutionOutputSizes(p.first)
                    if (highResolutionOutputs.size > 0) {
                        specs.add(CameraSpecResult(KEY_INDENT_PARA, "High Resolution (" + p.first.toString() + ")" + p.second, NONE))
                        highResolutionOutputs.forEachIndexed { index, size ->
                            val outputXY = "[$index]"  + size.width.toString() + "x" + size.height.toString()
                            val minFrame = ", Duration(min frame):" + configs.getOutputMinFrameDuration(p.first, size)
                            val stall = " (stall):" + configs.getOutputStallDuration(p.first, size)
                            specs.add(CameraSpecResult(KEY_INDENT_PARA, outputXY + minFrame + stall, NONE))
                        }
                        specs.add(CameraSpecResult(KEY_RESET, "", NONE))
                    }
                }
            }
        }

        val inputFormats = configs?.inputFormats
        if (inputFormats != null) {
            getComment(CameraSpecsComment.imageFormat).forEach { p: Pair<Int, String> ->
                if (inputFormats.contains(p.first)) {
                    specs.add(CameraSpecResult(KEY_INDENT_PARA, "Input (" + p.first.toString() + ")" + p.second, NONE))
                    val outputSizes = configs.getInputSizes(p.first)
                    outputSizes.forEachIndexed { index, size ->
                        val outputXY = "[$index]"  + size.width.toString() + "x" + size.height.toString()
                        specs.add(CameraSpecResult(KEY_INDENT_PARA, outputXY, NONE))
                    }
                    specs.add(CameraSpecResult(KEY_RESET, "", NONE))
                }
            }

            getComment(CameraSpecsComment.imageFormat).forEach { p: Pair<Int, String> ->
                if (inputFormats.contains(p.first)) {
                    val outputs4Inputs = configs.getValidOutputFormatsForInput(p.first)
                    if (outputs4Inputs.size > 0) {
                        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Output for Input (" + p.first.toString() + ")" + p.second, NONE))
                        outputs4Inputs.forEachIndexed { index, id ->
                            val io = "[$index] (" + id.toString() + ")" + getComment(CameraSpecsComment.imageFormat, id)
                            specs.add(CameraSpecResult(KEY_INDENT_PARA, io, NONE))
                        }
                        specs.add(CameraSpecResult(KEY_RESET, "", NONE))
                    }
                }
            }
        }

        val videoSizes = configs?.highSpeedVideoSizes
        if (videoSizes != null) {
            specs.add(CameraSpecResult(KEY_INDENT_PARA, "HighSpeedVideoConfigurations", NONE))
            videoSizes.forEach { size ->
                val fpsRanges = configs.highSpeedVideoFpsRanges
                fpsRanges.forEach { range ->
                    var videoConfigs = size.width.toString() + "x" + size.height.toString()
                    videoConfigs += ", FPS " + range.lower.toString() + " to " + range.upper.toString()
                    specs.add(CameraSpecResult(KEY_INDENT_PARA, videoConfigs, NONE))
                }
            }
            specs.add(CameraSpecResult(KEY_RESET, "", NONE))
        }

        specs.add(CameraSpecResult(KEY_TITLE, "Color Correction", NONE))
        val colorCorrectionModes = characteristics.get(CameraCharacteristics.COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES)
        if (colorCorrectionModes != null) {
            getComment(CameraSpecsComment.colorCorrectionMode).forEach { p: Pair<Int, String> ->
                val checkmark = if (colorCorrectionModes.contains(p.first)) CHECK else CROSS
                specs.add(CameraSpecResult(KEY_NEWLINE, p.second, checkmark))
            }
        }
    }

    private fun readZoomParameters() {
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Zoom Capabilities", NONE))

        val digitalZoom = characteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Max Digital Zoom: " + (digitalZoom?:UNKNOWN).toString(), NONE))

        val zoomTypeVal = characteristics.get(CameraCharacteristics.SCALER_CROPPING_TYPE)
        val zoomType = getComment(CameraSpecsComment.scalerCroppingType, zoomTypeVal?:UNKNOWN)
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Zoom Type: " + zoomType, NONE))
    }

    private fun readAvailableCapabilities() {
        val capabilities = characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)
        if (capabilities != null) {
            val capabilitiesList = Arrays.stream(capabilities).boxed().collect(Collectors.toList()) as List<Int?>
            specs.add(CameraSpecResult(KEY_TITLE, "Request Available Capabilities", NONE))
            getComment(CameraSpecsComment.availableCapabilities).forEach{ p: Pair<Int, String> ->
                val checkmark = if (capabilitiesList.contains(p.first)) CHECK else CROSS
                specs.add(CameraSpecResult(KEY_NEWLINE, p.second, checkmark))
            }
        }
    }

    private fun readAwbCapabilities() {
        specs.add(CameraSpecResult(KEY_TITLE, "Auto White Balance Capabilities", NONE))
        val capabilities = characteristics.get(CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES)
        if (capabilities != null) {
            val capabilitiesList = Arrays.stream(capabilities).boxed().collect(Collectors.toList()) as List<Int?>
            getComment(CameraSpecsComment.awbMode).forEach{ p: Pair<Int, String> ->
                val checkmark = if (capabilitiesList.contains(p.first)) CHECK else CROSS
                specs.add(CameraSpecResult(KEY_NEWLINE, p.second, checkmark))
            }
        }
    }

    private fun readAfCapabilities() {
        specs.add(CameraSpecResult(KEY_TITLE, "Auto Focus Capabilities", NONE))

        val capabilities = characteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES)
        if (capabilities != null) {
            val capabilitiesList = Arrays.stream(capabilities).boxed().collect(Collectors.toList()) as List<Int?>
            getComment(afMode).forEach{ p: Pair<Int, String> ->
                val checkmark = if (capabilitiesList.contains(p.first)) CHECK else CROSS
                specs.add(CameraSpecResult(KEY_NEWLINE, p.second, checkmark))
            }
        }
    }

    private fun readAeCapabilities() {
        specs.add(CameraSpecResult(KEY_TITLE, "Auto Exposure Capabilities", NONE))

        val capabilities = characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES)
        if (capabilities != null) {
            val capabilitiesList = Arrays.stream(capabilities).boxed().collect(Collectors.toList()) as List<Int?>
            getComment(CameraSpecsComment.aeMode).forEach { p: Pair<Int, String> ->
                val checkmark = if (capabilitiesList.contains(p.first)) CHECK else CROSS
                specs.add(CameraSpecResult(KEY_NEWLINE, p.second, checkmark))
            }
        }
        val aeLockAvailable = characteristics.get(CameraCharacteristics.CONTROL_AE_LOCK_AVAILABLE)
        val checkmark = if (aeLockAvailable == true) CHECK else CROSS
        specs.add(CameraSpecResult(KEY_NEWLINE, "AE Lock", checkmark))

        val aeCompensationRangeVal = characteristics.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE)
        val aeCompensationRange = aeCompensationRangeVal?.lower.toString() + " to " + aeCompensationRangeVal?.upper.toString()
        specs.add(CameraSpecResult(KEY_NEWLINE, "AE Compensation Range: " + aeCompensationRange, NONE))

        val aeCompensationStep = characteristics.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_STEP)
        specs.add(CameraSpecResult(KEY_NEWLINE, "AE Compensation Step: " + aeCompensationStep.toString(), NONE))

        specs.add(CameraSpecResult(KEY_BRAKE, "", NONE))
        val aeAntibandingModes = characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES)
        if (aeAntibandingModes != null) {
            val capabilitiesList = Arrays.stream(aeAntibandingModes).boxed().collect(Collectors.toList()) as List<Int?>
            getComment(CameraSpecsComment.aeAntibandingMode).forEach { p: Pair<Int, String> ->
                val checkmark = if (capabilitiesList.contains(p.first)) CHECK else CROSS
                specs.add(CameraSpecResult(KEY_NEWLINE, p.second, checkmark))
            }
        }
    }

    private fun readVideoParameters() {

        specs.add(CameraSpecResult(KEY_INDENT_PARA, "Video AE Available FPS Range", NONE))
        characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES)?.forEachIndexed { index: Int, range: Range<Int> ->
            specs.add(CameraSpecResult(KEY_INDENT_PARA, "[$index] " + range.lower.toString() + " to " + range.upper.toString(), NONE))
        }
    }

    companion object {
        const val NONE = -1
        const val CROSS = 0
        const val CHECK = 1

        const val KEY_L_TITLE = "L TITLE"
        const val KEY_TITLE = "TITLE"
        const val KEY_NEWLINE = "NORMAL"
        const val KEY_INDENT_PARA = "INDENT1"
        const val KEY_BRAKE = "BR"
        const val KEY_RESET = "RESET"

        const val UNKNOWN = -1
    }

}
package com.example.camera2probeK

import android.annotation.TargetApi
import android.hardware.camera2.CameraMetadata
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.util.Pair

object CameraSpecsComment {
    private val infoSupportedHardwareLevelComment = listOf(
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED, "LIMITED"),
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_FULL, "FULL"),
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY, "LEGACY"),
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_3, "LEVEL_3"),
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_EXTERNAL, "EXTERNAL")
    )

    @RequiresApi(Build.VERSION_CODES.Q) private val requestAvailableCapabilitiesCommentQ = listOf(
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE, "Camera API Compatible"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_MANUAL_SENSOR, "Manually Controlled"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_MANUAL_POST_PROCESSING, "Manually Controlled Postprocessing"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_RAW, "Outputting RAW Buffers"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_PRIVATE_REPROCESSING, "Zero Shutter Lag Reprocessing"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_READ_SENSOR_SETTINGS, "Accurately Reporting Sensor Settings"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_BURST_CAPTURE, "Burst Capture"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_YUV_REPROCESSING, "YUV_420_888 Reprocessing"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_DEPTH_OUTPUT, "Depth Measurements"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_CONSTRAINED_HIGH_SPEED_VIDEO, "High Speed Video Recording"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_MOTION_TRACKING, "Motion Tracking"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_LOGICAL_MULTI_CAMERA, "Multiplex Physical Cameras"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_MONOCHROME, "Monochrome Camera"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_SECURE_IMAGE_DATA, "Trusted Execution Environments Only"),
    )
    @RequiresApi(Build.VERSION_CODES.R) private val requestAvailableCapabilitiesCommentR = listOf(
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE, "Camera API Compatible"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_MANUAL_SENSOR, "Manually Controlled"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_MANUAL_POST_PROCESSING, "Manually Controlled Postprocessing"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_RAW, "Outputting RAW Buffers"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_PRIVATE_REPROCESSING, "Zero Shutter Lag Reprocessing"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_READ_SENSOR_SETTINGS, "Accurately Reporting Sensor Settings"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_BURST_CAPTURE, "Burst Capture"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_YUV_REPROCESSING, "YUV_420_888 Reprocessing"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_DEPTH_OUTPUT, "Depth Measurements"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_CONSTRAINED_HIGH_SPEED_VIDEO, "High Speed Video Recording"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_MOTION_TRACKING, "Motion Tracking"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_LOGICAL_MULTI_CAMERA, "Multiplex Physical Cameras"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_MONOCHROME, "Monochrome Camera"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_SECURE_IMAGE_DATA, "Trusted Execution Environments Only"),
        // R
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_SYSTEM_CAMERA, "Permission SYSTEM_CAMERA"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_OFFLINE_PROCESSING, "Supports Offline Processing"),
    )
    @RequiresApi(Build.VERSION_CODES.S) private val requestAvailableCapabilitiesCommentS = listOf(
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE, "Camera API Compatible"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_MANUAL_SENSOR, "Manually Controlled"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_MANUAL_POST_PROCESSING, "Manually Controlled Postprocessing"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_RAW, "Outputting RAW Buffers"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_PRIVATE_REPROCESSING, "Zero Shutter Lag Reprocessing"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_READ_SENSOR_SETTINGS, "Accurately Reporting Sensor Settings"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_BURST_CAPTURE, "Burst Capture"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_YUV_REPROCESSING, "YUV_420_888 Reprocessing"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_DEPTH_OUTPUT, "Depth Measurements"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_CONSTRAINED_HIGH_SPEED_VIDEO, "High Speed Video Recording"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_MOTION_TRACKING, "Motion Tracking"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_LOGICAL_MULTI_CAMERA, "Multiplex Physical Cameras"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_MONOCHROME, "Monochrome Camera"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_SECURE_IMAGE_DATA, "Trusted Execution Environments Only"),
        // R
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_SYSTEM_CAMERA, "Permission SYSTEM_CAMERA"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_OFFLINE_PROCESSING, "Supports Offline Processing"),
        // S
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_ULTRA_HIGH_RESOLUTION_SENSOR, "Ultra High Resolution"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_REMOSAIC_REPROCESSING, "Bayer Pattern Reprocessing"),
    )

    private val controlAwbModeComment = listOf(
        Pair(CameraMetadata.CONTROL_AWB_MODE_AUTO, "AWB Active"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_OFF, "AWB Disabled"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_DAYLIGHT, "AWB Disabled (Daylight light: CIE D65)"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_CLOUDY_DAYLIGHT, "AWB Disabled (Cloudy daylight light)"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_TWILIGHT, "AWB Disabled (Twilight light)"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_INCANDESCENT, "AWB Disabled (Incandescent light: CIE A)"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_SHADE, "AWB Disabled (Shade light)"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_FLUORESCENT, "AWB Disabled (Fluorescent light: CIE F2)"),
        Pair(CameraMetadata.CONTROL_AWB_MODE_WARM_FLUORESCENT, "AWB Disabled (Warm fluorescent light: CIE F4)")
    )
    private val controlAfModeComment = listOf(
        Pair(CameraMetadata.CONTROL_AF_MODE_OFF, "AF Disabled"),
        Pair(CameraMetadata.CONTROL_AF_MODE_AUTO, "Basic automatic focus mode"),
        Pair(CameraMetadata.CONTROL_AF_MODE_MACRO, "Close-up focusing mode"),
        Pair(CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_VIDEO, "Constantly-in-focus VIDEO stream"),
        Pair(CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_PICTURE, "Constantly-in-focus PICTURE stream"),
        Pair(CameraMetadata.CONTROL_AF_MODE_EDOF, "Extended depth of field mode")
    )
    private val controlAeModeComment = listOf(
        Pair(CameraMetadata.CONTROL_AE_MODE_OFF, "Auto Exposure disabled"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON, "Auto Exposure active"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH, "Flash unit, firing it in low-light conditions"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON_ALWAYS_FLASH, "Flash unit, always firing"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE, "Automatic red eye reduction"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON_EXTERNAL_FLASH, "External flash has been turned on")
    )

    fun getInfoSupportedHardwareLevelComment(key: Int): String {
        val matchLevel = infoSupportedHardwareLevelComment.stream()
            .filter { p: Pair<Int, String> -> p.first == key }
            .findFirst()
        var result = "UNKNOWN"
        if (matchLevel.isPresent) result = matchLevel.get().second
        return result
    }

    fun getInfoSupportedHardwareLevelComment(): List<Pair<Int, String>> {
        return infoSupportedHardwareLevelComment
    }

    fun getRequestAvailableCapabilitiesComment(key: Int): String {
        //var stream: Stream<Pair<Int, String>> = Stream<Pair<Int, String>>(-1, "")
        var result = "UNKNOWN"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            //stream = requestAvailableCapabilitiesCommentS.stream()
            val matchLevel = requestAvailableCapabilitiesCommentS.stream()
                .filter { p: Pair<Int, String> -> p.first == key }
                .findFirst()
            if (matchLevel.isPresent) result = matchLevel.get().second
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val matchLevel = requestAvailableCapabilitiesCommentR.stream()
                .filter { p: Pair<Int, String> -> p.first == key }
                .findFirst()
            if (matchLevel.isPresent) result = matchLevel.get().second
        }
        else {
            val matchLevel = requestAvailableCapabilitiesCommentQ.stream()
                .filter { p: Pair<Int, String> -> p.first == key }
                .findFirst()
            if (matchLevel.isPresent) result = matchLevel.get().second
        }
        return result
    }

    fun getRequestAvailableCapabilitiesComment(): List<Pair<Int, String>> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) return requestAvailableCapabilitiesCommentS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) return requestAvailableCapabilitiesCommentR
        return requestAvailableCapabilitiesCommentQ
    }

    fun getControlAwbModeComment(key: Int): String {
        val matchLevel =
            controlAwbModeComment.stream().filter { p: Pair<Int, String> -> p.first == key }
                .findFirst()
        var result = "UNKNOWN"
        if (matchLevel.isPresent) result = matchLevel.get().second
        return result
    }

    fun getControlAwbModeComment(): List<Pair<Int, String>> {
        return controlAwbModeComment
    }

    fun getControlAfModeComment(key: Int): String {
        val matchLevel =
            controlAfModeComment.stream().filter { p: Pair<Int, String> -> p.first == key }
                .findFirst()
        var result = "UNKNOWN"
        if (matchLevel.isPresent) result = matchLevel.get().second
        return result
    }

    fun getControlAfModeComment(): List<Pair<Int, String>> {
        return controlAfModeComment
    }

    fun getControlAeModeComment(key: Int): String {
        val matchLevel =
            controlAeModeComment.stream().filter { p: Pair<Int, String> -> p.first == key }
                .findFirst()
        var result = "UNKNOWN"
        if (matchLevel.isPresent) result = matchLevel.get().second
        return result
    }

    fun getControlAeModeComment(): List<Pair<Int, String>> {
        return controlAeModeComment
    }
}
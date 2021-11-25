// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata
import android.os.Build
import androidx.annotation.RequiresApi

class RequestAvailableCapabilitiesComment(sdk: Int) : CameraSpecsComment {
    override var comments: MutableList<Pair<Int, String>> = mutableListOf()

    val commentsBaseQ: List<Pair<Int, String>> = listOf(
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
    @RequiresApi(Build.VERSION_CODES.R) private val commentAddR = listOf(
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_SYSTEM_CAMERA, "Permission SYSTEM_CAMERA"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_OFFLINE_PROCESSING, "Supports Offline Processing"),
    )
    @RequiresApi(Build.VERSION_CODES.S) private val commentAddS = listOf(
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_ULTRA_HIGH_RESOLUTION_SENSOR, "Ultra High Resolution"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_REMOSAIC_REPROCESSING, "Bayer Pattern Reprocessing"),
    )

    init {
        if (sdk < Build.VERSION_CODES.R) {
            comments = listOf(
                commentsBaseQ,
            ).flatten() as MutableList<Pair<Int, String>>
        }

        @RequiresApi(Build.VERSION_CODES.R)
        if (sdk < Build.VERSION_CODES.S) {
            comments = listOf(
                commentsBaseQ,
                commentAddR,
            ).flatten() as MutableList<Pair<Int, String>>
        }

        @RequiresApi(Build.VERSION_CODES.S)
        if (sdk >= Build.VERSION_CODES.S) {
            comments = listOf(
                commentsBaseQ,
                commentAddR,
                commentAddS,
            ).flatten() as MutableList<Pair<Int, String>>
        }
    }
}
package com.example.camera2probeK

import android.graphics.ImageFormat
import android.hardware.camera2.CameraMetadata
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.util.Pair
import java.util.*

object CameraSpecsComment {
    const val hwLevel = "InfoHwLevel"
    private val infoSupportedHardwareLevelComment = listOf(
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED, "LIMITED"),
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_FULL, "FULL"),
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY, "LEGACY"),
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_3, "LEVEL_3"),
        Pair(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_EXTERNAL, "EXTERNAL"),
    )

    const val lensFacing = "lensFacing"
    private val lensFacingComment = listOf(
        Pair(CameraMetadata.LENS_FACING_FRONT, "Front Camera"),
        Pair(CameraMetadata.LENS_FACING_BACK, "Back Camera"),
        Pair(CameraMetadata.LENS_FACING_EXTERNAL, "External Camera"),
    )

    const val scalerCroppingType = "ScalerCroppingType"
    private val scalerCroppingTypeComment = listOf(
        Pair(CameraMetadata.SCALER_CROPPING_TYPE_CENTER_ONLY, "Only supports centered crop regions"),
        Pair(CameraMetadata.SCALER_CROPPING_TYPE_FREEFORM, "Supports arbitrarily chosen crop regions"),
    )

    const val imageFormat = "ImageFormat"
    private val imageFormatsCommentBaseQ = listOf(
        Pair(ImageFormat.DEPTH16, "16bit dense depth image"),
        Pair(ImageFormat.DEPTH_JPEG, "Depth augmented compressed JPEG"),
        Pair(ImageFormat.DEPTH_POINT_CLOUD, "Sparse depth point cloud"),
        Pair(ImageFormat.FLEX_RGBA_8888, "FLEX_RGBA_8888: Multi-plane 8bits RGBA"),
        Pair(ImageFormat.FLEX_RGB_888, "FLEX_RGB_888: Multi-plane 8bits RGB"),
        Pair(ImageFormat.HEIC, "Compressed HEIC"),
        Pair(ImageFormat.JPEG, "Compressed JPEG"),
        Pair(ImageFormat.NV16, "NV16: YCbCr for video"),
        Pair(ImageFormat.NV21, "NV21: YCrCb for images"),
        Pair(ImageFormat.PRIVATE, "Private opaque image"),
        Pair(ImageFormat.RAW10, "10-bit raw Bayer-pattern"),
        Pair(ImageFormat.RAW12, "12-bit raw Bayer-pattern"),
        Pair(ImageFormat.RAW_PRIVATE, "RAW_PRIVATE: Single channel, sensor raw image"),
        Pair(ImageFormat.RAW_SENSOR, "RAW_SENSOR: Single channel, Bayer-mosaic image"),
        Pair(ImageFormat.RGB_565, "RGB_565"),
        Pair(ImageFormat.UNKNOWN, "UNKNOWN"),
        Pair(ImageFormat.Y8, "Y8: 8bits Y plane only"),
        Pair(ImageFormat.YUV_420_888, "YUV_420_888: YCbCr 4:2:0 chroma 8bits subsampled"),
        Pair(ImageFormat.YUV_422_888, "YUV_422_888: YCbCr 4:2:2 chroma 8bits subsampled"),
        Pair(ImageFormat.YUV_444_888, "YUV_444_888: YCbCr 4:4:4 chroma 8bits subsampled"),
        Pair(ImageFormat.YUY2, "YUY2: YCbCr for images"),
        Pair(ImageFormat.YV12, "YV12: 4:2:0 YCrCb planar"),
    )
    @RequiresApi(Build.VERSION_CODES.S) private val imageFormatsCommentAddS = listOf(
        Pair(ImageFormat.YCBCR_P010, "P010: 4:2:0 YCbCr semiplanar"),
    )
    private var imageFormatsComment: MutableList<Pair<Int, String>> = mutableListOf()

    const val availableCapabilities = "AvailableCapabilities"
    private val requestAvailableCapabilitiesCommentBaseQ = listOf(
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
    @RequiresApi(Build.VERSION_CODES.R) private val requestAvailableCapabilitiesCommentAddR = listOf(
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_SYSTEM_CAMERA, "Permission SYSTEM_CAMERA"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_OFFLINE_PROCESSING, "Supports Offline Processing"),
    )
    @RequiresApi(Build.VERSION_CODES.S) private val requestAvailableCapabilitiesCommentAddS = listOf(
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_ULTRA_HIGH_RESOLUTION_SENSOR, "Ultra High Resolution"),
        Pair(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_REMOSAIC_REPROCESSING, "Bayer Pattern Reprocessing"),
    )
    private var requestAvailableCapabilitiesComment: MutableList<Pair<Int, String>> = mutableListOf()

    const val awbMode = "AwbMode"
    private val controlAwbModesComment = listOf(
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

    const val colorCorrectionMode = "ColorCorrectionMode"
    private val colorCorrectionModesComment = listOf(
        Pair(CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_OFF, "No aberration correction is applied"),
        Pair(CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_FAST, "Reference to Last frame (LEGACY)"),
        Pair(CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_HIGH_QUALITY, "Quality priority Color correction"),
    )

    const val afMode = "AfMode"
    private val controlAfModesComment = listOf(
        Pair(CameraMetadata.CONTROL_AF_MODE_OFF, "AF Disabled"),
        Pair(CameraMetadata.CONTROL_AF_MODE_AUTO, "Basic automatic focus mode"),
        Pair(CameraMetadata.CONTROL_AF_MODE_MACRO, "Close-up focusing mode"),
        Pair(CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_VIDEO, "Constantly-in-focus VIDEO stream"),
        Pair(CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_PICTURE, "Constantly-in-focus PICTURE stream"),
        Pair(CameraMetadata.CONTROL_AF_MODE_EDOF, "Extended depth of field mode")
    )

    const val aeMode = "AeMode"
    private val controlAeModesComment = listOf(
        Pair(CameraMetadata.CONTROL_AE_MODE_OFF, "Auto Exposure disabled"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON, "Auto Exposure active"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH, "Flash unit, firing it in low-light conditions"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON_ALWAYS_FLASH, "Flash unit, always firing"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE, "Automatic red eye reduction"),
        Pair(CameraMetadata.CONTROL_AE_MODE_ON_EXTERNAL_FLASH, "External flash has been turned on")
    )

    const val aeAntibandingMode = "AeAntibandingMode"
    private val controlAeAntibandingModesComment = listOf(
        Pair(CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_OFF, "Antibanding disabled"),
        Pair(CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_50HZ, "Antibanding for 50Hz illumination"),
        Pair(CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_60HZ, "Antibanding for 60Hz illumination"),
        Pair(CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_AUTO, "Antibanding Auto"),
    )

    private val listIds = listOf(
        hwLevel,
        lensFacing,
        scalerCroppingType,
        colorCorrectionMode,
        availableCapabilities,
        awbMode,
        afMode,
        aeMode,
        aeAntibandingMode,
    )

    fun setupLists() {
        if (requestAvailableCapabilitiesComment.isEmpty()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                requestAvailableCapabilitiesComment =
                    listOf(
                        requestAvailableCapabilitiesCommentBaseQ,
                    ).flatten() as MutableList<Pair<Int, String>>
            } else {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    requestAvailableCapabilitiesComment =
                            listOf(
                                    requestAvailableCapabilitiesCommentBaseQ,
                                    requestAvailableCapabilitiesCommentAddR,
                            ).flatten() as MutableList<Pair<Int, String>>
                } else {
                    requestAvailableCapabilitiesComment =
                            listOf(
                                    requestAvailableCapabilitiesCommentBaseQ,
                                    requestAvailableCapabilitiesCommentAddR,
                                    requestAvailableCapabilitiesCommentAddS,
                            ).flatten() as MutableList<Pair<Int, String>>
                }
            }
        }
        if (imageFormatsComment.isEmpty()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                imageFormatsComment =
                    listOf(
                        imageFormatsCommentBaseQ,
                    ).flatten() as MutableList<Pair<Int, String>>
            } else {
                imageFormatsComment =
                        listOf(
                                imageFormatsCommentBaseQ,
                                imageFormatsCommentAddS
                        ).flatten() as MutableList<Pair<Int, String>>
            }
        }
    }

    // Getters may prefer a simple one-on-one style as a Kotlin language style

    fun getComment(func: String): List<Pair<Int, String>> {
        when (func) {
            hwLevel -> return infoSupportedHardwareLevelComment
            lensFacing -> return lensFacingComment
            scalerCroppingType -> return scalerCroppingTypeComment
            imageFormat -> return imageFormatsComment
            availableCapabilities -> return requestAvailableCapabilitiesComment
            awbMode -> return controlAwbModesComment
            colorCorrectionMode -> return colorCorrectionModesComment
            afMode -> return controlAfModesComment
            aeMode -> return controlAeModesComment
            aeAntibandingMode -> return controlAeAntibandingModesComment
            else -> return emptyList()
        }
    }

    fun getComment(func: String, key: Int): String {
        val matchLevel: Optional<Pair<Int, String>>?
        when (func) {
            hwLevel -> matchLevel =
                        infoSupportedHardwareLevelComment.stream()
                        .filter { p: Pair<Int, String> -> p.first == key }.findFirst()
            lensFacing -> matchLevel =
                        lensFacingComment.stream()
                        .filter { p: Pair<Int, String> -> p.first == key }.findFirst()
            scalerCroppingType -> matchLevel =
                        scalerCroppingTypeComment.stream()
                        .filter { p: Pair<Int, String> -> p.first == key }.findFirst()
            imageFormat -> matchLevel =
                        imageFormatsComment.stream()
                        .filter { p: Pair<Int, String> -> p.first == key }.findFirst()
            availableCapabilities -> matchLevel =
                        requestAvailableCapabilitiesComment.stream()
                        .filter { p: Pair<Int, String> -> p.first == key }.findFirst()
            awbMode -> matchLevel =
                        controlAwbModesComment.stream()
                        .filter { p: Pair<Int, String> -> p.first == key }.findFirst()
            colorCorrectionMode -> matchLevel =
                        colorCorrectionModesComment.stream()
                        .filter { p: Pair<Int, String> -> p.first == key }.findFirst()
            afMode -> matchLevel =
                        controlAfModesComment.stream()
                        .filter { p: Pair<Int, String> -> p.first == key }.findFirst()
            aeMode -> matchLevel =
                        controlAeModesComment.stream()
                        .filter { p: Pair<Int, String> -> p.first == key }.findFirst()
            aeAntibandingMode -> matchLevel =
                    controlAeAntibandingModesComment.stream()
                        .filter { p: Pair<Int, String> -> p.first == key }.findFirst()
            else -> matchLevel =
                        null
        }
        var result = "UNKNOWN"
        if (matchLevel != null && matchLevel.isPresent)
            result = matchLevel.get().second
        return result
    }
}
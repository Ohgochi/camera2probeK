// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import android.os.Build
import com.example.camera2probeK.CameraSpec.Companion.KEY_INDENT_PARA
import com.example.camera2probeK.CameraSpec.Companion.NONE

class ReadBasicInfo(characteristics: CameraCharacteristics, id: String) : CameraSpecs(characteristics)  {
    var cameraid: String = ""

    init { cameraid = id }

    fun get(): List<CameraSpecResult> {
        val specs: MutableList<CameraSpecResult> = ArrayList()

        var title = "LOGICAL CAMERA"
        val lensFacingKey = characteristics.get(CameraCharacteristics.LENS_FACING)
        val lensFacingTxt = lensFacingKey?.let {GetOverviewLensFacings().get(it)}
        specs.add(CameraSpecResult(CameraSpec.KEY_L_TITLE,"$title[$cameraid] $lensFacingTxt", NONE))


        // This API is not properly implemented on many models
        title = "Physical Cameras: "
        val physicalCameraIds = characteristics.physicalCameraIds
        var cameras = "API not implemented"
        if (physicalCameraIds.size != 0) cameras = physicalCameraIds.size.toString()
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + cameras, NONE))

        title = "Hardware Level Support Category: "
        val hwlevelKey = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
        val hwLevelTxt = hwlevelKey?.let { GetOverviewHardwareLevels().get(it) }
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + hwLevelTxt, NONE))

        title = "Info version: "
        var infoVersionTxt = characteristics.get(CameraCharacteristics.INFO_VERSION)
        if (infoVersionTxt == null) infoVersionTxt = "Not supported"
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + infoVersionTxt, NONE))

        title = "Raw Sensitivity Boost Range: "
        val boostRange = characteristics.get(CameraCharacteristics.CONTROL_POST_RAW_SENSITIVITY_BOOST_RANGE)
        var boostRangeTxt = "No support RAW format out"
        if (boostRange != null)
            boostRangeTxt = boostRange.lower.toString() + " to " + boostRange.upper.toString()
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + boostRangeTxt, NONE))

        title = "Lens Shading Applied: "
        val lensShading = characteristics.get(CameraCharacteristics.SENSOR_INFO_LENS_SHADING_APPLIED)
        var lensShadingTxt = "not supported"
        if (lensShading != null)
            lensShadingTxt = if (lensShading) "RAW have lens shading correction" else "no adjusted for lens shading correction"
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + lensShadingTxt, NONE))

        title = "Max Frame Duration: "
        val maxFrameDuration = characteristics.get(CameraCharacteristics.SENSOR_INFO_MAX_FRAME_DURATION)
        var maxFrameDurationTxt = "not supported"
        if (maxFrameDuration != null)
            maxFrameDurationTxt = "$maxFrameDuration nanoseconds"
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + maxFrameDurationTxt, NONE))

        title = "Sync Max Latensy: "
        val syncMaxLatency = characteristics.get(CameraCharacteristics.SYNC_MAX_LATENCY)
        var syncMaxLatencyTxt = "Could not get"
        if (syncMaxLatency != null) {
            if (syncMaxLatency <= CameraCharacteristics.SYNC_MAX_LATENCY_PER_FRAME_CONTROL)
                syncMaxLatencyTxt = GetOverviewSyncMaxLatency().get(syncMaxLatency)
            else
                syncMaxLatencyTxt = syncMaxLatency.toString() + " frames"
        }
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + syncMaxLatencyTxt, NONE))

        title = "Rotate and Crop"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val rotateAndCropModes = characteristics.get(CameraCharacteristics.SCALER_AVAILABLE_ROTATE_AND_CROP_MODES)
            specs.addAll(getOverviewList(title, GetOverviewRotateAndCropModes().get(), rotateAndCropModes))
        }

        title = "Depth and Color: "
        val depthExclusive = characteristics.get(CameraCharacteristics.DEPTH_DEPTH_IS_EXCLUSIVE)
        var depthExclusiveTxt = "Depth not supported"
        if (depthExclusive != null) {
            if (depthExclusive)
                depthExclusiveTxt = "Must interleave color and depth requests"
            else
                depthExclusiveTxt = "Single request can target both types"
        }
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + depthExclusiveTxt, NONE))

        title = "Flash "
        val flashAvailable = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
        var flashAvailableTxt = "no available"
        if (flashAvailable != null) {
            if (flashAvailable) flashAvailableTxt = "available"
        }
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + flashAvailableTxt, NONE))

        title = "Logical multi camera sync type: "
        val logicalMultiCameraSensorSyncType = characteristics.get(CameraCharacteristics.LOGICAL_MULTI_CAMERA_SENSOR_SYNC_TYPE)
        var logicalMultiCameraSensorSyncTypeTxt = "H/W level sync, not supurtted"
        if (logicalMultiCameraSensorSyncType != null)
            logicalMultiCameraSensorSyncTypeTxt = GetOverviewLogicalMultiCamSensorSyncTypes().get(logicalMultiCameraSensorSyncType)
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + logicalMultiCameraSensorSyncTypeTxt, NONE))


        title = "Color Correction"
        val colorCorrectionModes = characteristics.get(CameraCharacteristics.COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES)
        specs.addAll(getOverviewList(title, GetOverviewColorCorrectionModes().get(), colorCorrectionModes))

        title = "Noise Reduction"
        val noiseReductionModes = characteristics.get(CameraCharacteristics.NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES)
        specs.addAll(getOverviewList(title, GetOverviewNoiseReductionModes().get(), noiseReductionModes))

        title = "Request Available Capabilities"
        val capabilities = characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)
        specs.addAll(getOverviewList(title, GetOverviewAvailableCapabilities().get(), capabilities))

        title = "Tone Map Modes"
        val availableToneMapModes = characteristics.get(CameraCharacteristics.TONEMAP_AVAILABLE_TONE_MAP_MODES)
        specs.addAll(getOverviewList(title, GetOverviewToneMapModes().get(), availableToneMapModes))

        title = "Tone Map Points: "
        val toneMapPoints = characteristics.get(CameraCharacteristics.TONEMAP_MAX_CURVE_POINTS)
        var toneMapPointsTxt = "Not supported programmable Tone Map"
        if (toneMapPoints != null)
            toneMapPointsTxt = title + toneMapPoints.toString()
        specs.add(CameraSpecResult(CameraSpec.KEY_NONE, toneMapPointsTxt, NONE))

        return specs
    }
}
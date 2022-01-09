// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraCharacteristics.*
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

        title = "Request Available Capabilities"
        val capabilities = characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)
        specs.addAll(getOverviewList(title, GetOverviewAvailableCapabilities().get(), capabilities))

        title = "Request Available Capability values"
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title, NONE))

        var subtitle = "Reprocess Max Capture Stall: "
        val reprocessMaxCaputureStall = characteristics.get(REPROCESS_MAX_CAPTURE_STALL)
        var reprocessMaxCaputureStallTxt = "Could not get"
        if (reprocessMaxCaputureStall != null)
            reprocessMaxCaputureStallTxt = "$reprocessMaxCaputureStall frames"
        specs.add(CameraSpecResult(KEY_INDENT_PARA, subtitle + reprocessMaxCaputureStallTxt, NONE))

        subtitle = "Request Max Num Input Streams: "
        val requestMaxNumInputStreams = characteristics.get(REQUEST_MAX_NUM_INPUT_STREAMS)
        var requestMaxNumInputStreamsTxt = "Could not get"
        if (requestMaxNumInputStreams != null)
            requestMaxNumInputStreamsTxt = if (requestMaxNumInputStreams == 0)
                "Not Support Input Stream"
            else
                "$requestMaxNumInputStreams stream"
        specs.add(CameraSpecResult(KEY_INDENT_PARA, subtitle + requestMaxNumInputStreamsTxt, NONE))

        subtitle = "Request Max Num Output Proc: "
        val requestMaxNumOutputProc = characteristics.get(REQUEST_MAX_NUM_OUTPUT_PROC)
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "$subtitle$requestMaxNumOutputProc streams", NONE))

        subtitle = "Request Max Num Output Proc Stalling: "
        val requestMaxNumOutputProcStalling = characteristics.get(REQUEST_MAX_NUM_OUTPUT_PROC_STALLING)
        var unit = "stream"
        if (requestMaxNumOutputProcStalling != null && requestMaxNumOutputProcStalling > 1) unit = "streams"
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "$subtitle$requestMaxNumOutputProcStalling $unit", NONE))

        subtitle = "Request Max Num Output Raw: "
        val requestMaxNumOutputRaw = characteristics.get(REQUEST_MAX_NUM_OUTPUT_RAW)
        var requestMaxNumOutputRawTxt = "Raw not supported"
        if (requestMaxNumOutputRaw != null)
            requestMaxNumOutputRawTxt = if (requestMaxNumOutputRaw > 1)
                "$requestMaxNumOutputRaw streams"
            else
                "$requestMaxNumOutputRaw stream"
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "$subtitle$requestMaxNumOutputRawTxt", NONE))

        subtitle = "Request Partial Result Count: "
        val requestPartialResultCount = characteristics.get(REQUEST_PARTIAL_RESULT_COUNT)
        var requestPartialResultCountTxt = "Could not get"
        if (requestPartialResultCount != null)
            requestPartialResultCountTxt = "$reprocessMaxCaputureStall"
        specs.add(CameraSpecResult(KEY_INDENT_PARA, subtitle + requestPartialResultCountTxt, NONE))

        subtitle = "Request Pipeline Max Depth: "
        val requestPipelineMaxDepth = characteristics.get(REQUEST_PIPELINE_MAX_DEPTH)
        specs.add(CameraSpecResult(KEY_INDENT_PARA, "$subtitle$requestPipelineMaxDepth stages", NONE))

        return specs
    }
}
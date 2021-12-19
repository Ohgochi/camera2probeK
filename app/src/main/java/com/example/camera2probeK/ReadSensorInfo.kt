// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.Manifest
import android.hardware.camera2.CameraCharacteristics
import android.os.Build
import com.example.camera2probeK.CameraSpec.Companion.KEY_INDENT_PARA
import com.example.camera2probeK.CameraSpec.Companion.KEY_NEWLINE
import com.example.camera2probeK.CameraSpec.Companion.KEY_RESET
import com.example.camera2probeK.CameraSpec.Companion.KEY_BRAKE
import com.example.camera2probeK.CameraSpec.Companion.NONE
import permissions.dispatcher.NeedsPermission

class ReadSensorInfo(characteristics: CameraCharacteristics) : CameraSpecs(characteristics){

    @NeedsPermission(Manifest.permission.CAMERA)
    fun get(): List<CameraSpecResult> {
        val specs: MutableList<CameraSpecResult> = ArrayList()
        var title = "Sensor Information"

        specs.add(CameraSpecResult(KEY_BRAKE, "", NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title, NONE))

        title = "Sensor Array Size: "
        val sensorPixelArraySize = characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE)
        var sensorPixelArraySizeTxt = "Could not get"
        if (sensorPixelArraySize != null)
            sensorPixelArraySizeTxt =
                    sensorPixelArraySize.width.toString() + " x " + sensorPixelArraySize.height.toString()
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorPixelArraySizeTxt, NONE))

        title = "Sensor Active Array Size: "
        val sensorInfoActiveArraySize = characteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE)
        var sensorInfoActiveArraySizeTxt = "Could not get"
        if (sensorInfoActiveArraySize != null)
            sensorInfoActiveArraySizeTxt = sensorInfoActiveArraySize.toShortString()
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorInfoActiveArraySizeTxt, NONE))

        title = "Sensor Active Array Size Max Res: "
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            var sensorInfoActiveArraySizeMaxResTxt = "Not supported"
            val sensorInfoActiveArraySizeMaxRes =
                    characteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE_MAXIMUM_RESOLUTION)
            if (sensorInfoActiveArraySizeMaxRes != null)
                sensorInfoActiveArraySizeMaxResTxt = sensorInfoActiveArraySizeMaxRes.toShortString()
            specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorInfoActiveArraySizeMaxResTxt, NONE))
        }

        title = "Sensor Array Size Max Res: "
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val sensorInfoPixelArraySizeMax =
                    characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE_MAXIMUM_RESOLUTION)
            var sensorInfoPixelArraySizeMaxTxt = "Could not get"
            if (sensorInfoPixelArraySizeMax != null)
                sensorInfoPixelArraySizeMaxTxt =
                        sensorInfoPixelArraySizeMax.width.toString() + " x " + sensorInfoPixelArraySizeMax.height.toString()
            specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorInfoPixelArraySizeMaxTxt, NONE))
        }

        title = "Sensor Pre Collective Array Size: "
        val sensorInfoPreCorrectedActiveArraySize =
                characteristics.get(CameraCharacteristics.SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE)
        var sensorInfoPreCorrectedActiveArraySizeTxt = "Could not get"
        if (sensorInfoPreCorrectedActiveArraySize != null)
            sensorInfoPreCorrectedActiveArraySizeTxt = sensorInfoPreCorrectedActiveArraySize.toShortString()
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorInfoPreCorrectedActiveArraySizeTxt, NONE))

        title = "Sensor Pre Collective Array Size Max Res: "
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val sensorInfoPreCorrectedActiveArraySizeMax =
                    characteristics.get(CameraCharacteristics.SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE_MAXIMUM_RESOLUTION)
            var sensorInfoPreCorrectedActiveArraySizeMaxTxt = "Could not get"
            if (sensorInfoPreCorrectedActiveArraySizeMax != null)
                sensorInfoPreCorrectedActiveArraySizeMaxTxt = sensorInfoPreCorrectedActiveArraySizeMax.toShortString()
            specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorInfoPreCorrectedActiveArraySizeMaxTxt, NONE))
        }

        title = "Sensor Physical Size: "
        val sensorInfoPixelPhysicalSize = characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE)
        var sensorInfoPixelPhysicalSizeTxt = "Could not get"
        if (sensorInfoPixelPhysicalSize != null)
            sensorInfoPixelPhysicalSizeTxt =
                    sensorInfoPixelPhysicalSize.width.toString() + " x " + sensorInfoPixelPhysicalSize.height.toString() + " mm"
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorInfoPixelPhysicalSizeTxt, NONE))

        title = "Sensor Binning Factor: "
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val sensorInfoBinningFactor =
                    characteristics.get(CameraCharacteristics.SENSOR_INFO_BINNING_FACTOR)
            var sensorInfoBinningFactorTxt = "Not supported"
            if (sensorInfoBinningFactor != null)
                sensorInfoBinningFactorTxt =
                        sensorInfoBinningFactor.width.toString() + " x " + sensorInfoBinningFactor.height.toString()
            specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorInfoBinningFactorTxt, NONE))
        }

        title = "Sensor Exposure Time Range: "
        val sensorInfoExposureTimeRange =
                characteristics.get(CameraCharacteristics.SENSOR_INFO_EXPOSURE_TIME_RANGE)
        var sensorInfoExposureTimeRangeTxt = "CNot supported"
        if (sensorInfoExposureTimeRange != null)
            sensorInfoExposureTimeRangeTxt =
                    sensorInfoExposureTimeRange.lower.toString() + " to " + sensorInfoExposureTimeRange.upper.toString() + " nanoseconds"
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorInfoExposureTimeRangeTxt, NONE))

        title = "Sensor Orientation: "
        val sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)
        var sensorOrientationTxt = "Could not get"
        if (sensorOrientation != null)
            sensorOrientationTxt = sensorOrientation.toString() + " deg"
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorOrientationTxt, NONE))

        title = "Sensor Sensitivity ISO Range: "
        val sensorInfoSensitivityRange = characteristics.get(CameraCharacteristics.SENSOR_INFO_SENSITIVITY_RANGE)
        var sensorInfoSensitivityRangeTxt = "ISO 12232:2006 not supported"
        if (sensorInfoSensitivityRange != null)
            sensorInfoSensitivityRangeTxt =
                    sensorInfoSensitivityRange.lower.toString() + " to " + sensorInfoSensitivityRange.upper.toString()
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorInfoSensitivityRangeTxt, NONE))

        title = "Sensor White Level: "
        val sensorWhiteLevel = characteristics.get(CameraCharacteristics.SENSOR_INFO_WHITE_LEVEL)
        var sensorWhiteLevelTxt = "not supported"
        if (sensorWhiteLevel != null) sensorWhiteLevelTxt = sensorWhiteLevel.toString()
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorWhiteLevelTxt, NONE))

        title = "Sensor Black Level Pattern: "
        val sensorBlackLevelPattern = characteristics.get(CameraCharacteristics.SENSOR_BLACK_LEVEL_PATTERN)
        var sensorBlackLevelPatternTxt = "Could not get"
        if (sensorBlackLevelPattern != null)
            sensorBlackLevelPatternTxt = "[" +
                    sensorBlackLevelPattern.getOffsetForIndex(0,0) + "," +
                    sensorBlackLevelPattern.getOffsetForIndex(0,1) + "],[" +
                    sensorBlackLevelPattern.getOffsetForIndex(1,0) + "," +
                    sensorBlackLevelPattern.getOffsetForIndex(1,1) + "]"
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorBlackLevelPatternTxt, NONE))

        title = "Sensor Max Analog Sensitivity: "
        val sensorMaxAnalogSensitivity = characteristics.get(CameraCharacteristics.SENSOR_MAX_ANALOG_SENSITIVITY)
        var sensorMaxAnalogSensitivityTxt = "not supported"
        if (sensorMaxAnalogSensitivity != null) sensorMaxAnalogSensitivityTxt = sensorMaxAnalogSensitivity.toString()
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + sensorMaxAnalogSensitivityTxt, NONE))

        title = "Sensor Sensitivity Timestamp Source: "
        val timestampSource = characteristics.get(CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE)
        var timestampSourceTxt = "Could not get"
        if (timestampSource != null) {
            timestampSourceTxt = GetOverviewTimestampSource().get(timestampSource)
        }
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + timestampSourceTxt, NONE))

        title = "Color filters on sensor: "
        val colorFilterArrangement = characteristics.get(CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT)
        var colorFilterArrangementTxt = "Could not get"
        if (colorFilterArrangement != null) {
            colorFilterArrangementTxt = GetOverviewColorFilterArrangement().get(colorFilterArrangement)
        }
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + colorFilterArrangementTxt, NONE))

        title = "Sensor Test Pattern Modes: "
        val sensorTestPatternModes = characteristics.get(CameraCharacteristics.SENSOR_AVAILABLE_TEST_PATTERN_MODES)
        if (sensorTestPatternModes != null) {
            specs.addAll(getOverviewList(title, GetOverviewTestPatternModes().get(), sensorTestPatternModes))
        } else {
            val sensorTestPatternModesTxt = "not supported"
            specs.add(CameraSpecResult(KEY_NEWLINE, title + sensorTestPatternModesTxt, NONE))
        }

        title = "Sensor Optical Black Regions: "
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title, NONE))
        val blackRegions = characteristics.get(CameraCharacteristics.SENSOR_OPTICAL_BLACK_REGIONS)
        var blackRedionsTxt = "No support"
        if (blackRegions != null) {
            blackRegions.forEachIndexed { index, rect ->
                blackRedionsTxt = "[" + index.toString() + "] " + rect.toShortString()
                specs.add(CameraSpecResult(KEY_INDENT_PARA, rect.toShortString(), NONE))
            }
        } else
            specs.add(CameraSpecResult(KEY_INDENT_PARA, blackRedionsTxt, NONE))

        title = "Sensor Calibration Transform 1 : "
        specs.add(CameraSpecResult(KEY_RESET, "", NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title, NONE))
        val calibrationTransform1 = characteristics.get(CameraCharacteristics.SENSOR_CALIBRATION_TRANSFORM1)
        var calibrationTransform1Txt = "Could not get"
        // TODO I couldn't check if the value I got with toString() was correct
        if (calibrationTransform1 != null)
            calibrationTransform1Txt = calibrationTransform1.toString()
        specs.add(CameraSpecResult(KEY_INDENT_PARA, calibrationTransform1Txt, NONE))

        title = "Sensor Calibration Transform with 2nd ref illuminant: "
        specs.add(CameraSpecResult(KEY_RESET, "", NONE))
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title, NONE))
        val calibrationTransform2 = characteristics.get(CameraCharacteristics.SENSOR_CALIBRATION_TRANSFORM2)
        var calibrationTransform2Txt = "Could not get"
        // TODO I couldn't check if the value I got with toString() was correct
        if (calibrationTransform2 != null)
            calibrationTransform2Txt = calibrationTransform2.toString()
        specs.add(CameraSpecResult(KEY_INDENT_PARA, calibrationTransform2Txt, NONE))

        title = "Color Transform (D50 Whitepoint) 1: "
        val forwordMatrix1 = characteristics.get(CameraCharacteristics.SENSOR_FORWARD_MATRIX1)
        var forwordMatrix1Txt = "Could not get"
        // TODO I couldn't check if the value I got with toString() was correct
        if (forwordMatrix1 != null)
            forwordMatrix1Txt = forwordMatrix1.toString()
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + forwordMatrix1Txt, NONE))

        title = "Color Transform (D50 Whitepoint) with 2nd ref illuminant: "
        val forwordMatrix2 = characteristics.get(CameraCharacteristics.SENSOR_FORWARD_MATRIX2)
        var forwordMatrix2Txt = "Could not get"
        // TODO I couldn't check if the value I got with toString() was correct
        if (forwordMatrix2 != null)
            forwordMatrix2Txt = forwordMatrix2.toString()
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + forwordMatrix2Txt, NONE))

        title = "Reference Illuminant 1: "
        val referenceIlluminate1 = characteristics.get(CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1)
        var referenceIlluminate1Txt = "Could not get"
        if (referenceIlluminate1 != null)
            referenceIlluminate1Txt = GetOverviewReferenceIlluminant1().get(referenceIlluminate1)
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + referenceIlluminate1Txt, NONE))

        title = "Reference Illuminant 2: "
        val referenceIlluminate2 = characteristics.get(CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT2)
        var referenceIlluminate2Txt = "Could not get"
        if (referenceIlluminate2 != null)
            referenceIlluminate2Txt = referenceIlluminate2.toString(16)
        specs.add(CameraSpecResult(KEY_INDENT_PARA, title + referenceIlluminate2Txt, NONE))

        return specs
    }

}
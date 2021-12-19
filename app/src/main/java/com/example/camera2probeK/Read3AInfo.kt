// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import com.example.camera2probeK.CameraSpec.Companion.NONE

class Read3AInfo(characteristics: CameraCharacteristics) : CameraSpecs(characteristics) {
    var specs: MutableList<CameraSpecResult> = ArrayList()

    fun get(): List<CameraSpecResult> {
        set3ACapabilities()
        setAwbCapabilities()
        setAfCapabilities()
        setAeCapabilities()
        
        return specs
    }

    private fun set3ACapabilities() {
        val title = "3A: Auto-Exposure, -White balance, -Focus"
        val controlAvailableModes = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_MODES)
        specs.addAll(getOverviewList(title, GetOverviewAutoModes().get(), controlAvailableModes))
    }
    
    private fun setAwbCapabilities() {
        val title = "Auto White Balance Capabilities"
        specs.add(CameraSpecResult(CameraSpec.KEY_TITLE, title, NONE))

        var subtitle = "AWB max regions: "
        val maxRegions = characteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AWB)
        specs.add(CameraSpecResult(CameraSpec.KEY_NEWLINE, subtitle + maxRegions.toString(), NONE))

        val capabilities = characteristics.get(CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES)
        specs.addAll(getOverviewList(CameraSpec.KEY_NONE, GetOverviewAwbModes().get(), capabilities))

        subtitle = "AWB Lock Available"
        val awbLockAvailableErrTxt = "Could not get"
        val lockAvailable = characteristics.get(CameraCharacteristics.CONTROL_AWB_LOCK_AVAILABLE)
        if (lockAvailable != null) {
            val checkmark = if (lockAvailable) CameraSpec.CHECK else CameraSpec.CROSS
            specs.add(CameraSpecResult(CameraSpec.KEY_NEWLINE, subtitle, checkmark))
        } else
            specs.add(CameraSpecResult(CameraSpec.KEY_NEWLINE, subtitle + awbLockAvailableErrTxt, NONE))
    }

    private fun setAfCapabilities() {
        val title = "Auto Focus Capabilities"
        specs.add(CameraSpecResult(CameraSpec.KEY_TITLE, title, NONE))

        val subtitle = "AF max regions: "
        val maxRegions = characteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)
        specs.add(CameraSpecResult(CameraSpec.KEY_NEWLINE, subtitle + maxRegions.toString(), NONE))

        val capabilities = characteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES)
        specs.addAll(getOverviewList(CameraSpec.KEY_NONE, GetOverviewAfModes().get(), capabilities))
    }

    private fun setAeCapabilities() {
        val title = "Auto Exposure Capabilities"
        specs.add(CameraSpecResult(CameraSpec.KEY_TITLE, title, NONE))

        var subtitle = "AE max regions: "
        val maxRegions = characteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AE)
        specs.add(CameraSpecResult(CameraSpec.KEY_NEWLINE, subtitle + maxRegions.toString(), NONE))

        subtitle = "AE Compensation Range: "
        val aeCompensationRangeVal = characteristics.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE)
        val aeCompensationRange = aeCompensationRangeVal?.lower.toString() + " to " + aeCompensationRangeVal?.upper.toString()
        specs.add(CameraSpecResult(CameraSpec.KEY_NEWLINE, subtitle + aeCompensationRange, NONE))

        subtitle = "AE Compensation Step: "
        val aeCompensationStep = characteristics.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_STEP)
        specs.add(CameraSpecResult(CameraSpec.KEY_NEWLINE, subtitle + aeCompensationStep.toString(), NONE))

        subtitle = "AE Lock"
        val aeLockAvailable = characteristics.get(CameraCharacteristics.CONTROL_AE_LOCK_AVAILABLE)
        val checkmark = if (aeLockAvailable == true) CameraSpec.CHECK else CameraSpec.CROSS
        specs.add(CameraSpecResult(CameraSpec.KEY_NEWLINE, subtitle, checkmark))

        val capabilities = characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES)
        specs.addAll(getOverviewList(CameraSpec.KEY_NONE, GetOverviewAeModes().get(), capabilities))

        val aeAntibandingModes = characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES)
        specs.addAll(getOverviewList(CameraSpec.KEY_NONE, GetOverviewAeAntibandingModes().get(), aeAntibandingModes))
    }
}
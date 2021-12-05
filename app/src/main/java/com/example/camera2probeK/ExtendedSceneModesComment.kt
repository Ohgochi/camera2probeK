// OHGOCHI, Toyoaki  https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.R) class ExtendedSceneModesComment : CameraSpecsComment {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.CONTROL_EXTENDED_SCENE_MODE_DISABLED, "Extended scene mode is disabled"),
        Pair(CameraMetadata.CONTROL_EXTENDED_SCENE_MODE_BOKEH_STILL_CAPTURE, "High quality bokeh mode"),
        Pair(CameraMetadata.CONTROL_EXTENDED_SCENE_MODE_BOKEH_CONTINUOUS, "Bokeh effect"),
    )
}
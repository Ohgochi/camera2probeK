// OHGOCHI, Toyoaki @Ohgochi
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.S) class GetOverviewRotateAndCropModes : GetOverviewCameraSpecs {
    override val comments: MutableList<Pair<Int, String>> = mutableListOf(
        Pair(CameraMetadata.SCALER_ROTATE_AND_CROP_NONE, "No rotate and crop"),
        Pair(CameraMetadata.SCALER_ROTATE_AND_CROP_90, "Rotated by 90deg clockwise"),
        Pair(CameraMetadata.SCALER_ROTATE_AND_CROP_180, "Rotated by 180deg clockwise"),
        Pair(CameraMetadata.SCALER_ROTATE_AND_CROP_270, "Rotated by 270deg clockwise"),
        Pair(CameraMetadata.SCALER_ROTATE_AND_CROP_AUTO, "Automatically selects the best concrete value"),
    )
}
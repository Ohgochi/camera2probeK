// OHGOCHI, Toyoaki @Ohgochi
package com.example.camera2probeK

import android.hardware.camera2.CameraMetadata
import android.os.Build
import androidx.annotation.RequiresApi

class ModesComment(sdk: Int) : CameraSpecsComment {
    override var comments: MutableList<Pair<Int, String>> = mutableListOf()

    val commentsBaseQ: List<Pair<Int, String>> = listOf(
        Pair(CameraMetadata.CONTROL_MODE_AUTO, "3A AUTO: Use settings for each individual 3A"),
        Pair(CameraMetadata.CONTROL_MODE_OFF, "3A OFF: Manual, update BG"),
        Pair(CameraMetadata.CONTROL_MODE_OFF_KEEP_STATE, "3A OFF_KEEP_STATE: Manual, discarded BG"),
        Pair(CameraMetadata.CONTROL_MODE_USE_SCENE_MODE, "3A USE_SCENE_MODE: use scene mode"),
    )

    @RequiresApi(Build.VERSION_CODES.R) val commentsAddR: List<Pair<Int, String>> = listOf(
        Pair(CameraMetadata.CONTROL_MODE_USE_EXTENDED_SCENE_MODE, "3A EXTENDED_SCENE_MODE: use extended scene mode"),
    )

    init {
        if (sdk < Build.VERSION_CODES.R) {
            comments = listOf(
                commentsBaseQ,
            ).flatten() as MutableList<Pair<Int, String>>
        }

        @RequiresApi(Build.VERSION_CODES.R)
        if (sdk >= Build.VERSION_CODES.R) {
            comments = listOf(
                commentsBaseQ,
                commentsAddR,
            ).flatten() as MutableList<Pair<Int, String>>
        }
    }
}
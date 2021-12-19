// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics

abstract class CameraSpecs(val characteristics: CameraCharacteristics) {
    fun getOverviewList(title: String, commentList: List<Pair<Int, String>>, funcs: IntArray?) : List<CameraSpecResult> {
        val specTxt: MutableList<CameraSpecResult> = ArrayList()
        if (funcs != null) {
            if (title != CameraSpec.KEY_NONE)
                specTxt.add(CameraSpecResult(CameraSpec.KEY_TITLE, title, CameraSpec.NONE))
            commentList.forEach {
                val checkmark = if (funcs.contains(it.first)) CameraSpec.CHECK else CameraSpec.CROSS
                specTxt.add(CameraSpecResult(CameraSpec.KEY_NEWLINE, it.second, checkmark))
            }
            funcs.forEach { func ->
                var unknownFunc = true
                commentList.forEach one@{ comment ->
                    if (comment.first == func) {
                        unknownFunc = false
                        return@one
                    }
                }
                if (unknownFunc)
                    specTxt.add(CameraSpecResult(
                        CameraSpec.KEY_NEWLINE, "[$func]: Unknown Function",
                        CameraSpec.SPACE
                    ))
            }
        }
        return specTxt
    }
}
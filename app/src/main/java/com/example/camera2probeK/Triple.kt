// Toyoaki, OHGOCHI  https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import com.example.camera2probeK.CameraSpecResult
import android.hardware.camera2.CameraAccessException
import com.example.camera2probeK.CameraSpec
import android.os.Build
import com.example.camera2probeK.CameraSpecsComment
import androidx.appcompat.app.AppCompatActivity
import com.example.camera2probeK.CameraSpecToHtml
import android.os.Bundle
import com.example.camera2probeK.R
import android.webkit.WebView
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.result.ActivityResultCallback
import android.app.Activity
import android.hardware.camera2.CameraMetadata

open class Triple<K, V, S> internal constructor(
    private var Key: K?,
    private var Value: V?,
    private var State: S
) {
    fun first(): K? {
        return Key
    }

    fun second(): V? {
        return Value
    }

    fun third(): S {
        return State
    }

    fun setFirst(t1: K) {
        Key = t1
    }

    fun setSecond(t2: V) {
        Value = t2
    }

    fun setThird(t3: S) {
        State = t3
    }

    override fun toString(): String {
        return "($Key, $Value, $State)"
    }
}
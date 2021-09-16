/// Original Code: TobiasWeis / android-camera2probe / Old school Java
// https://github.com/TobiasWeis/android-camera2probe/wiki
//
// 1st) Ported to Android Studio 2020.3.1, API 29 and Java 8 (camera2probe4)
// 2nd) Ported to Kotlin 1.5
// Toyoaki, OHGOCHI  https://twitter.com/Ohgochi/

// The original code emailed the result to the embedded email address,
// but I changed this to file save
// Toyoaki, OHGOCHI  https://twitter.com/Ohgochi/

package com.example.camera2probeK

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.result.ActivityResultCallback
import android.view.View
import android.widget.Button
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val convHtml = CameraSpecToHtml(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_save = findViewById<View>(R.id.btn_save) as Button
        btn_save.setOnClickListener { saveInfoFile() }
        val specs = CameraSpec(this)
        specs.buildSpecs()
        convHtml.setUnencodedHtml(specs.getSpecs())
        val resultHtml = convHtml.unencodedHtml
        val wv = findViewById<View>(R.id.probe) as WebView
        wv.loadDataWithBaseURL(null, resultHtml, "text/html", "utf-8", null)
    }

    private fun saveInfoFile() {
        val htmlIntent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        htmlIntent.addCategory(Intent.CATEGORY_OPENABLE)
        htmlIntent.type = "text/html"
        htmlIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.file_name))
        saveInfoFile.launch(htmlIntent)
    }

    var saveInfoFile = registerForActivityResult(
        StartActivityForResult(),
        ActivityResultCallback { result ->
            if (result.resultCode != RESULT_OK) {
                // error logout
                return@ActivityResultCallback
            }
            val uri = result.data!!.data
            try {
                contentResolver.openOutputStream(uri!!).use { outStream ->
                    val resultHtml = convHtml.unencodedHtml
                    outStream?.write(resultHtml.toByteArray())
                    outStream?.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    )
}
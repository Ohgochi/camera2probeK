package com.example.camera2probeK

import android.content.Context
import java.util.function.Consumer

class CameraSpecToHtml(private val thisContext: Context) {
    val unencodedHtml: String
        get() {
            return Companion.unencodedHtml
        }

    fun setUnencodedHtml(specs: List<CameraSpecResult>) {
        Companion.unencodedHtml =
                (HTML_OPEN_HEAD + thisContext.getString(R.string.app_name) + HTML_BREAK_LINE
                + thisContext.getString(R.string.app_name_add) + HTML_OPEN_BODY)
        specs.forEach (Consumer onecase@{ p: CameraSpecResult ->
            if (p.first() == CameraSpec.KEY_TITLE) {
                Companion.unencodedHtml += HTML_OPEN_TITLE + p.second() + HTML_CLOSE_TITLE
                return@onecase
            }
            if (p.first() == CameraSpec.KEY_LOGICAL || p.first() == CameraSpec.KEY_PHYSICAL) {
                Companion.unencodedHtml += HTML_OPEN_TITLE + p.first() + STR_COLON + p.second() + HTML_CLOSE_TITLE
                return@onecase
            }
            if (p.third() == CameraSpec.NONE) {
                Companion.unencodedHtml += p.first()
                    .toString() + ": " + p.second() + HTML_BREAK_LINE
                return@onecase
            }
            if (p.third() == CameraSpec.CROSS) {
                Companion.unencodedHtml += FONT_CROSS + STYLE_OPEN_NEGATIVE + p.first() + STYLE_CLOSE_NEGAPOSI
                return@onecase
            }
            if (p.third() == CameraSpec.CHECK) {
                Companion.unencodedHtml += FONT_CHECK + STYLE_OPEN_POSITIVE + p.first() + STYLE_CLOSE_NEGAPOSI
                return@onecase
            }
        })
        Companion.unencodedHtml += HTML_CLOSE_BODY
    }

    companion object {
        var unencodedHtml: String = ""
        const val STYLE_OPEN_POSITIVE = "<font style=\"color:#00aa00;\">"
        const val STYLE_OPEN_NEGATIVE = "<font style=\"color:#990000;\">"
        const val STYLE_CLOSE_NEGAPOSI = "</font><br style=\"clear:both;\">"
        const val FONT_CHECK = "<div style=\"float:left;width:20px;color:#00aa00;\">&#x2713; </div>"
        const val FONT_CROSS = "<div style=\"float:left;width:20px;color:#990000;\">&#x2717; </div>"
        const val HTML_OPEN_HEAD = "<!DOCTYPE html><html><head><title>"
        const val HTML_OPEN_BODY = "</title></head><body>"
        const val HTML_CLOSE_BODY = "</body></html>"
        const val HTML_OPEN_TITLE = "<br><b>"
        const val HTML_CLOSE_TITLE = "</b><br>"
        const val HTML_BREAK_LINE = "<br>"
        const val STR_COLON = ": "
    }
}
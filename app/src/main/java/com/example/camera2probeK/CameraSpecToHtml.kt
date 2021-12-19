// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.content.Context

class CameraSpecToHtml(private val thisContext: Context) {
    val unencodedHtml: String
        get() {
            return Companion.unencodedHtml
        }

    fun setUnencodedHtml(specs: List<CameraSpecResult>) {
        Companion.unencodedHtml =
                (HTML_OPEN_HEAD + thisContext.getString(R.string.app_name) + HTML_BREAK_LINE
                + thisContext.getString(R.string.app_name_add) + HTML_CLOSE_HEAD + HTML_OPEN_BODY)
        specs.forEach onecase@{
            if (it.first() != CameraSpec.KEY_INDENT_PARA ) {
                if (indent) {
                    indent = false
                    Companion.unencodedHtml += HTML_CLOSE_INDENT
                }
            }
            if (it.first() == CameraSpec.KEY_INDENT_PARA) {
                if (!indent) {
                    indent = true
                    subtitle = true
                    Companion.unencodedHtml += HTML_OPEN_INDENT + HTML_OPEN_SUBTITLE
                } else
                    Companion.unencodedHtml += HTML_BREAK_LINE
            }
            if (it.third() == CameraSpec.NONE) {
                if (negaposi) negaposi = false
            }

            if (it.first() == CameraSpec.KEY_RESET) {
                // Do nothing
                return@onecase
            }
            if (it.first() == CameraSpec.KEY_BRAKE) {
                Companion.unencodedHtml += HTML_BREAK_LINE
                return@onecase
            }
            if (it.first() == CameraSpec.KEY_TITLE) {
                Companion.unencodedHtml += HTML_OPEN_TITLE + it.second() + HTML_CLOSE_TITLE
                return@onecase
            }
            if (it.first() == CameraSpec.KEY_L_TITLE) {
                indent = true
                Companion.unencodedHtml += HTML_OPEN_INDENT + HTML_OPEN_LTITLE + it.second() + HTML_CLOSE_LTITLE
                return@onecase
            }

            if (it.third() == CameraSpec.NONE) {
                if (it.first() == CameraSpec.KEY_NEWLINE)
                    Companion.unencodedHtml += HTML_BREAK_LINE
                //Companion.unencodedHtml += HTML_OPEN_LINE + it.second() + HTML_CLOSE_LINE
                Companion.unencodedHtml += it.second()
                if (subtitle) {
                    subtitle = false
                    Companion.unencodedHtml += HTML_CLOSE_SUBTITLE
                }
                return@onecase
            }

            if (it.third() == CameraSpec.SPACE) {
                if (!negaposi) {
                    negaposi = true
                    Companion.unencodedHtml += HTML_BREAK_LINE
                }
                Companion.unencodedHtml += FONT_SPACE + STYLE_OPEN_NEGATIVE + it.second() + STYLE_CLOSE_NEGAPOSI
                return@onecase
            }
            if (it.third() == CameraSpec.CROSS) {
                if (!negaposi) {
                    negaposi = true
                    Companion.unencodedHtml += HTML_BREAK_LINE
                }
                Companion.unencodedHtml += FONT_CROSS + STYLE_OPEN_NEGATIVE + it.second() + STYLE_CLOSE_NEGAPOSI
                return@onecase
            }
            if (it.third() == CameraSpec.CHECK) {
                if (!negaposi) {
                    negaposi = true
                    Companion.unencodedHtml += HTML_BREAK_LINE
                }
                Companion.unencodedHtml += FONT_CHECK + STYLE_OPEN_POSITIVE + it.second() + STYLE_CLOSE_NEGAPOSI
                return@onecase
            }
        }
        Companion.unencodedHtml += HTML_CLOSE_BODY
    }

    companion object {
        var unencodedHtml: String = ""
        var indent: Boolean = false
        var negaposi: Boolean = false
        var subtitle: Boolean = false
        const val STYLE_OPEN_POSITIVE = "<font style=\"color:#00aa00;\">"
        const val STYLE_OPEN_NEGATIVE = "<font style=\"color:#990000;\">"
        // TODO Nowrap: Contains unexpected openings between NEGAPOSI and the text
        const val STYLE_CLOSE_NEGAPOSI = "</font></nw><br style=\"clear:both;\">"
        const val FONT_CHECK = "<nw><div style=\"float:left;width:20px;color:#00aa00;\">&#x2713; </div>"
        const val FONT_CROSS = "<nw><div style=\"float:left;width:20px;color:#990000;\">&#x2717; </div>"
        const val FONT_SPACE = "&emsp;"
        const val HTML_OPEN_HEAD = "<!DOCTYPE html><html><head><title>"
        const val HTML_CLOSE_HEAD = "</title>" +
                                    "<style>p.ind1{margin-left: 1rem; text-indent: -1rem; white-space: nowrap}</style>" +
                                    "<style>wLineSpace{line-height: 150%}</style>" +
                                    "</head>"
        const val HTML_OPEN_BODY = "<body>"
        const val HTML_CLOSE_BODY = "</body></html>"
        const val HTML_OPEN_TITLE = "<br><b>"
        const val HTML_CLOSE_TITLE = "</b>"
        const val HTML_OPEN_LTITLE = "<b><big>"
        const val HTML_CLOSE_LTITLE = "</big></b>"
        const val HTML_OPEN_SUBTITLE = "<b>"
        const val HTML_CLOSE_SUBTITLE = "</b>"
        const val HTML_BREAK_LINE = "<br>"
        const val HTML_OPEN_INDENT = "<p class=\"ind1\">"
        const val HTML_CLOSE_INDENT = "</p>"
    }
}
// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.params.StreamConfigurationMap

class ReadScalerStreamConfigMap(characteristics: CameraCharacteristics) : CameraSpecs(characteristics) {
    var specs: MutableList<CameraSpecResult> = ArrayList()

    fun get(): List<CameraSpecResult> {
        val title = "Image Formats"
        specs.add(CameraSpecResult(CameraSpec.KEY_TITLE, title, CameraSpec.NONE))
        val configs = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

        readOutputFormats(configs)
        readInputFormats(configs)
        readOutputForInput(configs)
        readJpegThumbnailSizes()

        return specs
    }

    private fun readOutputFormats(configs: StreamConfigurationMap?) {
        var subtitle = "Output ("
        val outputFormats = configs?.outputFormats
        if (outputFormats != null) {
            GetOverviewImageFormats().get().forEach { comment ->
                if (outputFormats.contains(comment.first)) {
                    val outputFormatsTxt = comment.first.toString() + ")" + comment.second
                    specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, subtitle + outputFormatsTxt, CameraSpec.NONE))
                    val outputSizes = configs.getOutputSizes(comment.first)
                    outputSizes.forEachIndexed { index, size ->
                        val outputXY = "[$index]"  + size.width.toString() + "x" + size.height.toString()
                        val minFrame = ", Duration(min frame):" + configs.getOutputMinFrameDuration(comment.first, size)
                        val stall = " (stall):" + configs.getOutputStallDuration(comment.first, size)
                        specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, outputXY + minFrame + stall, CameraSpec.NONE))
                    }
                    specs.add(CameraSpecResult(CameraSpec.KEY_RESET, "", CameraSpec.NONE))
                }
            }

            subtitle = "High Resolution ("
            GetOverviewImageFormats().get().forEach {
                if (outputFormats.contains(it.first)) {
                    val highResolutionOutputs = configs.getHighResolutionOutputSizes(it.first)
                    if (highResolutionOutputs.isNotEmpty()) {
                        val highResolutionOutputsTxt = it.first.toString() + ")" + it.second
                        specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA,  subtitle + highResolutionOutputsTxt, CameraSpec.NONE))
                        highResolutionOutputs.forEachIndexed { index, size ->
                            val outputXY = "[$index]"  + size.width.toString() + "x" + size.height.toString()
                            val minFrame = ", Duration(min frame):" + configs.getOutputMinFrameDuration(it.first, size)
                            val stall = " (stall):" + configs.getOutputStallDuration(it.first, size)
                            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, outputXY + minFrame + stall, CameraSpec.NONE))
                        }
                        specs.add(CameraSpecResult(CameraSpec.KEY_RESET, "", CameraSpec.NONE))
                    }
                }
            }
        }
    }

    private fun readInputFormats(configs: StreamConfigurationMap?) {
        val subtitle = "Input ("
        val inputFormats = configs?.inputFormats
        if (inputFormats != null) {
            GetOverviewImageFormats().get().forEach {
                if (inputFormats.contains(it.first)) {
                    val inputsTxt = it.first.toString() + ")" + it.second
                    specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, subtitle + inputsTxt, CameraSpec.NONE))
                    val inputSizes = configs.getInputSizes(it.first)
                    inputSizes.forEachIndexed { index, size ->
                        val outputXY = "[$index]" + size.width.toString() + "x" + size.height.toString()
                        specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, outputXY, CameraSpec.NONE))
                    }
                    specs.add(CameraSpecResult(CameraSpec.KEY_RESET, "", CameraSpec.NONE))
                }
            }
        }
    }

    private fun readOutputForInput(configs: StreamConfigurationMap?) {
        val subtitle = "Output for Input ("
        val inputFormats = configs?.inputFormats
        if (inputFormats != null) {
            GetOverviewImageFormats().get().forEach {
                if (inputFormats.contains(it.first)) {
                    val outputs4Inputs = configs.getValidOutputFormatsForInput(it.first)
                    if (outputs4Inputs.size > 0) {
                        val outputs4InputsTxt = it.first.toString() + ")" + it.second
                        specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, subtitle + outputs4InputsTxt, CameraSpec.NONE))
                        outputs4Inputs.forEachIndexed { index, id ->
                            val io = "[$index] (" + id.toString() + ")" + GetOverviewImageFormats().get(id)
                            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, io, CameraSpec.NONE))
                        }
                        specs.add(CameraSpecResult(CameraSpec.KEY_RESET, "", CameraSpec.NONE))
                    }
                }
            }
        }
    }

    private fun readJpegThumbnailSizes() {
        val title = "Jpeg Thumbnail Sizes"
        val jpegThumbnailSizes = characteristics.get(CameraCharacteristics.JPEG_AVAILABLE_THUMBNAIL_SIZES)
        specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, title, CameraSpec.NONE))
        jpegThumbnailSizes?.forEachIndexed { index, size ->
            val sizeTxt = "[$index] " + size.width.toString() + " x " + size.height.toString()
            specs.add(CameraSpecResult(CameraSpec.KEY_INDENT_PARA, sizeTxt, CameraSpec.NONE))
        }
    }
}
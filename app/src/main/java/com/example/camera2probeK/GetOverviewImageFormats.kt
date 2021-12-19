// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

import android.graphics.ImageFormat
import android.os.Build
import androidx.annotation.RequiresApi

class GetOverviewImageFormats(sdk: Int = Build.VERSION.SDK_INT) : GetOverviewCameraSpecs {
    override var comments: MutableList<Pair<Int, String>> = mutableListOf()

    val commentsBaseQ: List<Pair<Int, String>> = listOf(
        Pair(ImageFormat.DEPTH16, "16bit dense depth image"),
        Pair(ImageFormat.DEPTH_JPEG, "Depth augmented compressed JPEG"),
        Pair(ImageFormat.DEPTH_POINT_CLOUD, "Sparse depth point cloud"),
        Pair(ImageFormat.FLEX_RGBA_8888, "FLEX_RGBA_8888: Multi-plane 8bits RGBA"),
        Pair(ImageFormat.FLEX_RGB_888, "FLEX_RGB_888: Multi-plane 8bits RGB"),
        Pair(ImageFormat.HEIC, "Compressed HEIC"),
        Pair(ImageFormat.JPEG, "Compressed JPEG"),
        Pair(ImageFormat.NV16, "NV16: YCbCr for video"),
        Pair(ImageFormat.NV21, "NV21: YCrCb for images"),
        Pair(ImageFormat.PRIVATE, "Private opaque image"),
        Pair(ImageFormat.RAW10, "10-bit raw Bayer-pattern"),
        Pair(ImageFormat.RAW12, "12-bit raw Bayer-pattern"),
        Pair(ImageFormat.RAW_PRIVATE, "RAW_PRIVATE: Single channel, sensor raw image"),
        Pair(ImageFormat.RAW_SENSOR, "RAW_SENSOR: Single channel, Bayer-mosaic image"),
        Pair(ImageFormat.RGB_565, "RGB_565"),
        Pair(ImageFormat.UNKNOWN, "UNKNOWN"),
        Pair(ImageFormat.Y8, "Y8: 8bits Y plane only"),
        Pair(ImageFormat.YUV_420_888, "YUV_420_888: YCbCr 4:2:0 chroma 8bits subsampled"),
        Pair(ImageFormat.YUV_422_888, "YUV_422_888: YCbCr 4:2:2 chroma 8bits subsampled"),
        Pair(ImageFormat.YUV_444_888, "YUV_444_888: YCbCr 4:4:4 chroma 8bits subsampled"),
        Pair(ImageFormat.YUY2, "YUY2: YCbCr for images"),
        Pair(ImageFormat.YV12, "YV12: 4:2:0 YCrCb planar"),
    )
    @RequiresApi(Build.VERSION_CODES.S) private val commentAddS = listOf(
        Pair(ImageFormat.YCBCR_P010, "P010: 4:2:0 YCbCr semiplanar"),
    )

    init {
        if (sdk < Build.VERSION_CODES.S) {
            comments = listOf(
                commentsBaseQ,
            ).flatten() as MutableList<Pair<Int, String>>
        }

        @RequiresApi(Build.VERSION_CODES.S)
        if (sdk >= Build.VERSION_CODES.S) {
            comments = listOf(
                commentsBaseQ,
                commentAddS,
            ).flatten() as MutableList<Pair<Int, String>>
        }
    }
}
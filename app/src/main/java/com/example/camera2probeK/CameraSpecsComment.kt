package com.example.camera2probeK

import java.util.*

sealed interface  CameraSpecsComment {
    val comments: MutableList<Pair<Int, String>>

    fun get() : List<Pair<Int, String>> {
        return comments
    }

    fun get(key: Int) : String {
        val matchLevel: Optional<Pair<Int, String>> =
            comments.stream().filter { p: Pair<Int, String> -> p.first == key }.findFirst()
        if (matchLevel.isPresent)
            return matchLevel.get().second
        return "Unknown"
    }
}
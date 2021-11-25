// OHGOCHI, Toyoaki https://twitter.com/Ohgochi/
package com.example.camera2probeK

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
package com.enginekt

class Time {

    var now: Long = 0
        get() = field
        private set(value) { field = value }

    var delta: Long = 0
        get() = field
        private set(value) { field = value }

    fun reset() {
        now = 0
        delta = 0
    }

    fun update(delta: Long) {
        this.delta = delta
        this.now += delta
    }

}
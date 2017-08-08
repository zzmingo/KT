package com.enginekt

class Time {

    var now: Int = 0
        get() = field
        private set(value) { field = value }

    var delta: Int = 0
        get() = field
        private set(value) { field = value }

    fun reset() {
        now = 0
        delta = 0
    }

    fun update(delta: Int) {
        this.delta = delta
        this.now += delta
    }

}
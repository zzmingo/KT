package com.enginekt.schedule

import com.enginekt.base.Task

abstract class ScheduleBase(
        override val id: String,
        val task: Task
) : Schedule {

    override var paused: Boolean = false
        get() = field
        protected set(value) { field = value }

    override var completed: Boolean = false
        get() = field
        protected set(value) { field = value }

    override var canceled: Boolean = false
        get() = field
        protected set(value) { field = value }

    override fun pause() {
        paused = true
    }

    override fun resume() {
        paused = false
    }

    override fun cancel() {
        canceled = true
    }

    override fun dispose() {
        cancel()
    }

    internal fun callTask(complete: Boolean = false) {
        task()
        if (complete) {
            completed = true
        }
    }

    internal abstract fun update(deltaTime: Long)

}
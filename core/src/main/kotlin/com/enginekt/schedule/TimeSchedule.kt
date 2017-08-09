package com.enginekt.schedule

import com.enginekt.base.Task

class TimeSchedule(id: String, val time: Int, task: Task) : ScheduleBase(id, task) {

    private var _waitTime: Long = 0

    override fun update(deltaTime: Long) {
        _waitTime += deltaTime
        if (_waitTime >= time) {
            callTask(true)
        }
    }

}
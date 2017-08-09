package com.enginekt.schedule

import com.enginekt.base.Task

class IntervalSchedule(id: String, val interval: Int, task: Task) : ScheduleBase(id, task) {

    private var _waitTime: Long = 0

    override fun update(deltaTime: Long) {
        _waitTime += deltaTime
        if (_waitTime >= interval) {
            callTask()
            _waitTime -= interval
        }
    }

}
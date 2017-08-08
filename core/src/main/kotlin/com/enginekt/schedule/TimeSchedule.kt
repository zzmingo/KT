package com.enginekt.schedule

class TimeSchedule(id: String, val time: Int, task: Task) : ScheduleBase(id, task) {

    private var _waitTime: Int = 0

    override fun update(deltaTime: Int) {
        _waitTime += deltaTime
        if (_waitTime >= time) {
            callTask(true)
        }
    }

}
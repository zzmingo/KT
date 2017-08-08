package com.enginekt.schedule

class IntervalSchedule(id: String, val interval: Int, task: Task) : ScheduleBase(id, task) {

    private var _waitTime: Int = 0

    override fun update(deltaTime: Int) {
        _waitTime += deltaTime
        if (_waitTime >= interval) {
            callTask()
            _waitTime -= interval
        }
    }

}
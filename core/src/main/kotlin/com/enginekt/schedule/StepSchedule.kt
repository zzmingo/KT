package com.enginekt.schedule

class StepSchedule(id: String, val steps: Int, task: Task) : ScheduleBase(id, task) {

    private var _waitStep: Int = 0

    override fun update(deltaTime: Int) {
        _waitStep ++
        if (_waitStep >= steps) {
            callTask(true)
        }
    }

}
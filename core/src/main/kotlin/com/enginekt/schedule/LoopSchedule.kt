package com.enginekt.schedule

class LoopSchedule(id: String, task: Task) : ScheduleBase(id, task) {

    override fun update(deltaTime: Int) {
        callTask()
    }

}
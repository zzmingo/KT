package com.enginekt.schedule

import com.enginekt.base.Task

class LoopSchedule(id: String, task: Task) : ScheduleBase(id, task) {

    override fun update(deltaTime: Long) {
        callTask()
    }

}
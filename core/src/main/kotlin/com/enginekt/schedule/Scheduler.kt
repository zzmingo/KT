package com.enginekt.schedule

import com.enginekt.base.utils.SafeList

class Scheduler {

    val count: Int
        get() = _schedules.size

    private var _idGen = 0
    private val _schedules = SafeList<Schedule>()

    fun add(schedule: Schedule): Schedule {
        _schedules.add(schedule)
        return schedule
    }

    fun remove(schedule: Schedule): Schedule {
        _schedules.remove(schedule)
        return schedule
    }

    fun clear() {
        _schedules.clear()
    }

    fun loop(task: Task): Schedule {
        return add(LoopSchedule(_generateId(), task))
    }

    fun time(time: Int, task: Task): Schedule {
        return add(TimeSchedule(_generateId(), time, task))
    }

    fun interval(interval: Int, task: Task): Schedule {
        return add(IntervalSchedule(_generateId(), interval, task))
    }

    fun step(steps: Int, task: Task): Schedule {
        return add(StepSchedule(_generateId(), steps, task))
    }

    internal fun update(deltaTime: Int) {
        if (_schedules.size == 0) {
            return
        }
        _schedules.forEach {
            if (it.canceled) {
                remove(it)
            } else {
                if (!it.paused) {
                    if (it is ScheduleBase) {
                        it.update(deltaTime)
                    }
                }
                if (it.completed) {
                    remove(it)
                }
            }
        }
    }

    private fun _generateId(): String {
        return "schedule_" + (_idGen++)
    }

}
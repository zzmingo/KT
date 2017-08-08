package com.enginekt.core

import com.enginekt.*
import com.enginekt.base.dispose.CompositeDisposableBase
import com.enginekt.base.event.Event
import com.enginekt.input.Input
import com.enginekt.input.InputEvent
import com.enginekt.input.InputProcessor
import com.enginekt.schedule.Scheduler

abstract class ApplicationBase(
        override val orientation: Orientation,
        override val resolution: Double
) : CompositeDisposableBase(), Application {

    override val app = this
    override val time: Time = Time()
    override val stage: StageImpl = StageImpl(this)
    override val scheduler = Scheduler()

    override val OnStart = Event<Any>()
    override val OnStop = Event<Any>()
    override val OnPause = Event<Any>()
    override val OnResume = Event<Any>()
    override val OnStepBegin = Event<Any>()
    override val OnStepEnd = Event<Any>()

    override val started: Boolean
        get() = _started
    private var _started: Boolean = false

    override val paused: Boolean
        get() = _paused
    private var _paused: Boolean = false

    override val stepping: Boolean
        get() = _stepping
    private var _stepping: Boolean = false

    override val stepId: Int
        get() = _stepId
    private var _stepId: Int = 1

    private var _lastTime: Int = 0

    private var input: Input<*>? = null
    private var inputProcess: InputProcessor<*>? = null

    fun initialize() {
        stage.initialize()
        renderingContext.initialize()
    }

    override fun start() {
        if (_started) {
            return
        }
        time.reset()
        _lastTime = currentMillis()
        _started = true
        OnStart.invoke(this)
    }

    override fun stop() {
        if (!_started) {
            return
        }
        _started = false
        _paused = false
        OnStop.invoke(this)
    }

    override fun pause() {
        if (!_started || _paused) {
            return
        }
        OnPause.invoke(this)
    }

    override fun resume() {
        if (!_started || !_paused) {
            return
        }
        _lastTime = currentMillis()
        OnResume.invoke(this)
    }

    override fun step() {
        if (!_started || _paused) {
            return
        }
        _stepping = true
        KT.enterLoop(this)
        time.update(currentMillis() - _lastTime)
        OnStepBegin.invoke(this)
        stage.updateStage()
        renderingContext.clear()
        stage.renderStage(renderingContext)
        renderingContext.flush()
        scheduler.update(time.delta)
        OnStepEnd.invoke(this)
        KT.exitLoop()
        _stepping = false
        _stepId ++
    }

    override fun post(task: () -> Unit) {
        scheduler.step(0, task)
    }

    override fun <T : InputEvent> useInputSuite(input: Input<T>, processor: InputProcessor<T>) {
        this.input?.dispose()
        this.inputProcess?.dispose()
        input.OnInput.add { event -> processor.processInputEvent(this, event) }
    }

    abstract fun currentMillis(): Int
}
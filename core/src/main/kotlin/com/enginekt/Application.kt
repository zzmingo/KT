package com.enginekt

import com.enginekt.base.dispose.Disposable
import com.enginekt.base.event.Event
import com.enginekt.input.Input
import com.enginekt.input.InputEvent
import com.enginekt.input.InputProcessor

/**
 * 整个项目的入口，根据不同的平台（Platform）提供不同的 `Application`,
 * 它管理了项目的多个方面，如下：
 *
 * * [Logger] 提供日志功能
 * * [Input] 提供设备输入，如touch事件等
 * * [FileSystem] 文件系统
 * * [RenderingContext] 渲染上下文环境
 * * [CoreFactory] 核心工厂
 *
 * 这些功能都分别需要不同的平台去实现
 *
 */
interface Application : Context, Disposable {

    /**
     * 平台类型
     */
    enum class Platform {
        WebGL, Canvas, DOM, Android, iOS
    }

    val OnStart: Event<Any>
    val OnStop: Event<Any>
    val OnPause: Event<Any>
    val OnResume: Event<Any>
    val OnStepBegin: Event<Any>
    val OnStepEnd: Event<Any>

    val resolution: Double

    val started: Boolean
    val paused: Boolean
    val stepping: Boolean
    val stepId: Int

    fun start()
    fun stop()
    fun pause()
    fun resume()
    fun step()
    fun post(task: () -> Unit)

    fun <T : InputEvent> useInputSuite(input: Input<T>, processor: InputProcessor<T>)
    fun useDefaultPointerInput(multiple: Boolean)

}
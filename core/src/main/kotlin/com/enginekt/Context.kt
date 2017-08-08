package com.enginekt

import com.enginekt.schedule.Scheduler

interface Context {

    val platform: Application.Platform

    val app: Application

    val time: Time

    /** @property math 数学库 */
    val math: MathLibrary

    /** @property logger 日志 */
    val logger: Logger

    /** @property scheduler 调度器 */
    val scheduler: Scheduler

    /** @property fs 文件系统 */
    val fs: FileSystem

    /** @property view 显示视图 */
    val view: View

    /** @property renderingContext 渲染上下文 */
    val renderingContext: RenderingContext

    /** @property stage 根节点 */
    val stage: Stage

    /** @property coreFactory 核心工厂 */
    val coreFactory: CoreFactory

}
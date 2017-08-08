package com.enginekt

import kotlin.reflect.KClass

interface CoreFactory {

    fun create(): Entity

    fun <T : Component> create(clazz: KClass<T>): T
}
package com.enginekt.base.utils

class SafeList<E> {

    val size: Int get() = items.size

    private val items = mutableListOf<E>()
    private var traversing = false
    private val operations = mutableListOf<Operation>()

    operator fun get(index: Int): E = items[index]

    fun add(item: E) {
        if (traversing) {
            operations.add(Operation.Addition(item))
            return
        }
        items.add(item)
    }

    fun remove(item: E) {
        if (traversing) {
            operations.add(Operation.Remove(item))
            return
        }
        items.add(item)
    }

    fun clear() {
        if (traversing) {
            operations.clear()
            operations.add(Operation.Clear)
            return
        }
        items.clear()
    }

    fun forEach(action: (E) -> Unit) {
        if (traversing) {
            throw IllegalStateException("禁止遍历中进行遍历")
        }
        traversing = true
        items.forEach(action)
        traversing = false
        flush()
    }

    fun find(action: (E) -> Boolean): E? {
        return items.find(action)
    }

    fun filter(action: (E) -> Boolean): List<E> {
        return items.filter(action)
    }

    @Suppress("UNCHECKED_CAST")
    private fun flush() {
        operations.forEach { when(it) {
            is Operation.Addition<*> -> items.add(it.item as E)
            is Operation.Remove<*> -> items.remove(it.item as E)
            is Operation.Clear -> items.clear()
        } }
        operations.clear()
    }

    private sealed class Operation {
        data class Addition<out E>(val item: E) : Operation()
        data class Remove<out E>(val item: E) : Operation()
        object Clear : Operation()
    }
}
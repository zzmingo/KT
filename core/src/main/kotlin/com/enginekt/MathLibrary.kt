package com.enginekt

interface MathLibrary {

    val E: Double
    val PI: Double

    fun abs(value: Number): Number
    fun sin(value: Double): Double
    fun cos(value: Double): Double

}
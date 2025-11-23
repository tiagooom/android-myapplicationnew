package com.example.myapplicationnew

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object CoordinatesHolder {
    private val _lastX = MutableStateFlow(0f)
    private val _lastY = MutableStateFlow(0f)

    val lastX: StateFlow<Float> get() = _lastX
    val lastY: StateFlow<Float> get() = _lastY

    fun update(x: Float, y: Float) {
        _lastX.value = x
        _lastY.value = y
    }
}

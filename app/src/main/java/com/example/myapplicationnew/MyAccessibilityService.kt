package com.example.myapplicationnew

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.content.Intent

class MyAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Não precisa tratar eventos por enquanto
    }

    override fun onInterrupt() {}

    companion object {
        var instance: MyAccessibilityService? = null
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }
    fun tap(x: Int, y: Int) {
        val path = Path().apply {
            moveTo(x.toFloat(), y.toFloat())
        }

        val stroke = GestureDescription.StrokeDescription(path, 0, 120)
        val gesture = GestureDescription.Builder()
            .addStroke(stroke)
            .build()

        dispatchGesture(
            gesture,
            null,  // sem callback
            null
        )
    }

    fun postDelayed(delay: Long, action: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(action, delay)
    }

    fun showOverlay() {
        val intent = Intent(this, TransparentOverlayActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}

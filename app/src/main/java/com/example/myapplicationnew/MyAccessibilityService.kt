package com.example.myapplicationnew

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.content.Intent
import android.view.accessibility.AccessibilityNodeInfo

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

    fun swipe(startX: Int, startY: Int, endX: Int, endY: Int, duration: Long) {
        val path = Path().apply {
            moveTo(startX.toFloat(), startY.toFloat())
            lineTo(endX.toFloat(), endY.toFloat())
        }

        val gesture = GestureDescription.Builder()
            .addStroke(
                GestureDescription.StrokeDescription(
                    path,
                    0,
                    duration
                )
            )
            .build()

        dispatchGesture(gesture, null, null)
    }


    fun showOverlay() {
        val intent = Intent(this, TransparentOverlayActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun procurarTextoNaTela(texto: String): Boolean {
        val root = rootInActiveWindow ?: return false
        return buscarTexto(root, texto)
    }

    fun buscarTexto(node: AccessibilityNodeInfo?, texto: String): Boolean {
        if (node == null) return false

        val nodeText = node.text?.toString() ?: ""
        if (nodeText.contains(texto, ignoreCase = true)) return true

        for (i in 0 until node.childCount) {
            if (buscarTexto(node.getChild(i), texto)) return true
        }

        return false
    }
}

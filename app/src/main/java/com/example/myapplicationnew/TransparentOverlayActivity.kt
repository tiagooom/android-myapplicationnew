package com.example.myapplicationnew

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.ComponentActivity

class TransparentOverlayActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Tela transparente
        window.setBackgroundDrawable(
            android.graphics.drawable.ColorDrawable(
                android.graphics.Color.parseColor("#88FF0000")
            )
        )

        // Não fechar ao clicar fora
        window.setFlags(
            android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {

            val x = event.x
            val y = event.y

            // 👉 Atualiza coordenadas corretamente
            CoordinatesHolder.update(x, y)

            Toast.makeText(this, "Clicado em: x=$x, y=$y", Toast.LENGTH_SHORT).show()

            finish()
        }
        return true
    }
}

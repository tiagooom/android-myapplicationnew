package com.example.myapplicationnew

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.ComponentActivity

class TransparentOverlayActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Tela transparente ocupando tudo
        window.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(android.graphics.Color.parseColor("#88FF0000")))


        // Necessário para não fechar ao clicar fora (até receber o toque do overlay)
        window.setFlags(
            android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {

            // Pega coordenadas do clique
            val x = event.x
            val y = event.y

            // Mostra um Toast com as coordenadas
            Toast.makeText(this, "Clicado em: x=$x, y=$y", Toast.LENGTH_SHORT).show()

            // Fecha a overlay após clique
            finish()
        }

        return super.onTouchEvent(event)
    }

}

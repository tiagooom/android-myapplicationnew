package com.example.myapplicationnew

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val lastX by CoordinatesHolder.lastX.collectAsState()
            val lastY by CoordinatesHolder.lastY.collectAsState()

            var captureEnabled by remember { mutableStateOf(false) }
            var boxOffsetX by remember { mutableStateOf(0f) }
            var boxOffsetY by remember { mutableStateOf(0f) }

            val targetX = 663
            val targetY = 1631
            val context = this@MainActivity

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { layout ->
                        val pos = layout.positionOnScreen()
                        boxOffsetX = pos.x
                        boxOffsetY = pos.y
                    }
                    .pointerInput(captureEnabled) {
                        if (captureEnabled) {

                            detectTapGestures { offset ->

                                val realX = boxOffsetX + offset.x
                                val realY = boxOffsetY + offset.y

                                // 👉 Aqui sim é o lugar certo
                                CoordinatesHolder.update(realX, realY)
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Button(onClick = {
                        MyAccessibilityService.instance?.performGlobalAction(
                            android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME
                        )
                    }) {
                        Text("HOME")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(onClick = {
                        MyAccessibilityService.instance?.performGlobalAction(
                            android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK
                        )
                    }) {
                        Text("VOLTAR")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(onClick = { captureEnabled = !captureEnabled }) {
                        Text(if (captureEnabled) "DESATIVAR" else "PEGAR TOQUE")
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(onClick = {
                        MyAccessibilityService.instance?.performGlobalAction(
                            android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME
                        )
                        MyAccessibilityService.instance?.postDelayed(1000) {
                            MyAccessibilityService.instance?.tap(targetX, targetY)
                        }
                    }) {
                        Text("BOTÃO HOME + CLICAR EM COORDENADA")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(onClick = {
                        // Abre apps recentes
                        MyAccessibilityService.instance?.performGlobalAction(
                            android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_RECENTS
                        )

                        // Volta automaticamente para o último app
                        MyAccessibilityService.instance?.postDelayed(200) {
                            MyAccessibilityService.instance?.performGlobalAction(
                                android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_RECENTS
                            )
                        }

                        // Agora espera o app abrir COMPLETAMENTE
                        MyAccessibilityService.instance?.postDelayed(700) {
                            val intent = Intent(context, TransparentOverlayActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        }
                    }) {
                        Text("Voltar + Overlay")
                    }


                    Spacer(modifier = Modifier.height(20.dp))

                    Button(onClick = {
                        MyAccessibilityService.instance?.performGlobalAction(
                            android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME
                        )
                        MyAccessibilityService.instance?.postDelayed(1000) {
                            MyAccessibilityService.instance?.tap(172, 697)
                        }

                        MyAccessibilityService.instance?.postDelayed(5000) {
                            MyAccessibilityService.instance?.tap(572, 2210)
                        }

                        MyAccessibilityService.instance?.postDelayed(6000) {
                            MyAccessibilityService.instance?.tap(236, 1230)
                        }

                        MyAccessibilityService.instance?.postDelayed(7000) {
                            MyAccessibilityService.instance?.tap(870, 1579)
                        }

                        MyAccessibilityService.instance?.postDelayed(8000) {
                            MyAccessibilityService.instance?.tap(894, 1244)
                        }

                        MyAccessibilityService.instance?.postDelayed(9000) {
                            MyAccessibilityService.instance?.tap(183, 1416)
                        }

                    }) {
                        Text("CLICAR EM CONJ DE COORDENADAS")
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(onClick = {
                        MyAccessibilityService.instance?.swipe(
                            startX = 540,   // meio da tela (ajuste se precisar)
                            startY = 10,    // bem no topo da tela
                            endX = 540,     // arrasta no mesmo eixo X
                            endY = 1400,    // até o meio da tela
                            duration = 300  // velocidade do swipe
                        )
                    }) {
                        Text("ATIVAR GRAVADOR")
                    }

                    Button(onClick = {
                        MyAccessibilityService.instance?.performGlobalAction(
                            android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME
                        )

                        MyAccessibilityService.instance?.postDelayed(1000) {
                            MyAccessibilityService.instance?.tap(705, 756)
                        }

                    }) {
                        Text("CLICAR NO TABLET FISICO")
                    }


                    Text(
                        text = "X: ${lastX.toInt()}\nY: ${lastY.toInt()}",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

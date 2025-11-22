package com.example.myapplicationnew

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

        // Verifica permissão para overlay
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        }

        setContent {
            // ==== Estados (dentro de setContent) ====
            var lastX by remember { mutableStateOf<Float?>(null) }
            var lastY by remember { mutableStateOf<Float?>(null) }
            var captureEnabled by remember { mutableStateOf(false) }

            var boxOffsetX by remember { mutableStateOf(0f) }
            var boxOffsetY by remember { mutableStateOf(0f) }

            val targetX = 932
            val targetY = 1593

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
                            awaitPointerEventScope {
                                while (true) {
                                    val event = awaitPointerEvent()
                                    val pos = event.changes.first().position

                                    // Coordenada real na tela:
                                    lastX = boxOffsetX + pos.x
                                    lastY = boxOffsetY + pos.y
                                }
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

                    // Botão HOME + Overlay
                    Button(onClick = {
                        // Vai para a Home
                        val home = Intent(Intent.ACTION_MAIN).apply {
                            addCategory(Intent.CATEGORY_HOME)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        context.startActivity(home)

                        // Abre overlay
                        val intent = Intent(context, TransparentOverlayActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }) {
                        Text("Home + Overlay2")
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    // Exibe coordenadas se existirem
                    if (lastX != null && lastY != null) {
                        Text(
                            text = "X: ${lastX!!.toInt()}\nY: ${lastY!!.toInt()}",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

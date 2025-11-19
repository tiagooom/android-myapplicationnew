package com.example.myapplicationnew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplicationnew.ui.theme.MyApplicationNewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationNewTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var lastX by remember { mutableStateOf<Float?>(null) }
    var lastY by remember { mutableStateOf<Float?>(null) }
    var captureEnabled by remember { mutableStateOf(false) }

    val targetX = 932
    val targetY = 1593

    var boxOffsetX by remember { mutableStateOf(0f) }
    var boxOffsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
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
            horizontalAlignment = Alignment.CenterHorizontally
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

            Spacer(modifier = Modifier.height(40.dp))

            if (lastX != null && lastY != null) {
                Text(
                    text = "X: ${lastX!!.toInt()}\n\nY: ${lastY!!.toInt()}",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

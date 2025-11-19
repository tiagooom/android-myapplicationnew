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

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(captureEnabled) {
                if (captureEnabled) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            val position = event.changes.first().position
                            lastX = position.x
                            lastY = position.y
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

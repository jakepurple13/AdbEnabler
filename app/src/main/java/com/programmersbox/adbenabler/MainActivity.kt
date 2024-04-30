package com.programmersbox.adbenabler

import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.programmersbox.adbenabler.ui.theme.AdbEnablerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //adb shell pm grant com.programmersbox.adbenabler android.permission.WRITE_SECURE_SETTINGS
            AdbEnablerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var ask = {}
    val permissions = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        if(it) {
            ask()
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        val context = LocalContext.current
        Button(
            onClick = {
                ask = {
                    Settings.Global.putInt(context.contentResolver, Settings.Global.ADB_ENABLED, 1)
                }
                permissions.launch(android.Manifest.permission.WRITE_SECURE_SETTINGS)
            }
        ) { Text("Enable ADB") }

        Button(
            onClick = {
                ask = {
                    Settings.Global.putInt(context.contentResolver, Settings.Global.ADB_ENABLED, 0)
                }
                permissions.launch(android.Manifest.permission.WRITE_SECURE_SETTINGS)
            }
        ) { Text("Disable ADB") }

        Text("Make sure to run")
        Card {
            SelectionContainer {
                Text("adb shell pm grant com.programmersbox.adbenabler android.permission.WRITE_SECURE_SETTINGS")
            }
        }
        Text("To make sure this can work")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdbEnablerTheme {
        Greeting()
    }
}
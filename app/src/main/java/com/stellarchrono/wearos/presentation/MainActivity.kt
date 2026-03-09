package com.stellarchrono.wearos.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

/**
 * Entry-point Activity for the Stellar Chrono Wear OS stopwatch.
 *
 * Keeps the screen on while the app is in the foreground so the user can
 * always see the running timer.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keep the screen awake while the stopwatch is visible
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            StopwatchScreen()
        }
    }
}

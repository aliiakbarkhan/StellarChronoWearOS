package com.stellarchrono.wearos.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stellarchrono.wearos.presentation.components.ActionButtons
import com.stellarchrono.wearos.presentation.components.LapList
import com.stellarchrono.wearos.presentation.components.TimerDisplay
import com.stellarchrono.wearos.presentation.theme.DarkBackground
import com.stellarchrono.wearos.presentation.theme.StellarChronoTheme
import com.stellarchrono.wearos.viewmodel.StopwatchViewModel

/**
 * Root composable for the Stopwatch screen.
 *
 * Layout:
 *  ┌──────────────────┐
 *  │   TOTAL TIME     │
 *  │    04:28.42      │
 *  │ [Lap][▶/❚❚][↺]  │
 *  │  Lap 3  01:12.05 │
 *  │  Lap 2  01:08.42 │
 *  │  Lap 1  02:07.95 │
 *  └──────────────────┘
 */
@Composable
fun StopwatchScreen(
    viewModel: StopwatchViewModel = viewModel()
) {
    val elapsedMs by viewModel.elapsedMs.collectAsState()
    val state by viewModel.state.collectAsState()
    val laps by viewModel.laps.collectAsState()

    StellarChronoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // ── Timer Display ──────────────────────
                TimerDisplay(
                    elapsedMs = elapsedMs,
                    stopwatchState = state,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ── Action Buttons ─────────────────────
                ActionButtons(
                    stopwatchState = state,
                    onStartPause = viewModel::toggleStartPause,
                    onLap = viewModel::lap,
                    onReset = viewModel::reset,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                // ── Lap List ───────────────────────────
                LapList(
                    laps = laps,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}

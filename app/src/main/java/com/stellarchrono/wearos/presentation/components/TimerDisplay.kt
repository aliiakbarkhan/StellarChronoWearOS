package com.stellarchrono.wearos.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.stellarchrono.wearos.data.StopwatchState
import com.stellarchrono.wearos.presentation.theme.PrimaryGreen
import com.stellarchrono.wearos.presentation.theme.PrimaryGreenDim
import com.stellarchrono.wearos.util.TimeFormatter

/**
 * Displays the main timer in MM:SS.cs format matching the Stellar Chrono design.
 * The "TOTAL TIME" label sits above, the large digits in white, and the
 * centiseconds portion in green.
 */
@Composable
fun TimerDisplay(
    elapsedMs: Long,
    stopwatchState: StopwatchState,
    modifier: Modifier = Modifier
) {
    // Subtle pulsing alpha when paused
    val alpha by animateFloatAsState(
        targetValue = if (stopwatchState == StopwatchState.PAUSED) 0.6f else 1f,
        animationSpec = tween(durationMillis = 600),
        label = "timerAlpha"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // "TOTAL TIME" label
        Text(
            text = "TOTAL TIME",
            style = MaterialTheme.typography.title1,
            color = PrimaryGreenDim,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        // Main timer digits
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.alpha(alpha)
        ) {
            // MM:SS portion — large, bold, white
            Text(
                text = TimeFormatter.formatMinutesSeconds(elapsedMs),
                color = MaterialTheme.colors.onBackground,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-2).sp
            )

            // .cs portion — smaller, green
            Text(
                text = TimeFormatter.formatCentiseconds(elapsedMs),
                color = PrimaryGreen,
                fontSize = 22.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = (-0.5).sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

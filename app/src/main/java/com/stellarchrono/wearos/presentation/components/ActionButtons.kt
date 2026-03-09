package com.stellarchrono.wearos.presentation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import com.stellarchrono.wearos.data.StopwatchState
import com.stellarchrono.wearos.presentation.theme.ButtonBackground
import com.stellarchrono.wearos.presentation.theme.ButtonBorder
import com.stellarchrono.wearos.presentation.theme.DarkBackground
import com.stellarchrono.wearos.presentation.theme.PrimaryGreen
import com.stellarchrono.wearos.presentation.theme.TextSecondary

/**
 * Row of three circular action buttons: Lap, Start/Pause, Reset.
 *
 * - The centre Play/Pause button is larger and uses the vibrant green.
 * - Side buttons use a dark surface with a subtle border.
 * - A spring scale animation triggers when the stopwatch state changes.
 */
@Composable
fun ActionButtons(
    stopwatchState: StopwatchState,
    onStartPause: () -> Unit,
    onLap: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animate the main button scale on state transitions
    val mainButtonScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "mainBtnScale"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ── Lap Button ───────────────────────
        SideActionButton(
            icon = Icons.Filled.Timer,
            contentDescription = "Lap",
            enabled = stopwatchState == StopwatchState.RUNNING,
            onClick = onLap
        )

        // ── Start / Pause (centre, large) ────
        Button(
            onClick = onStartPause,
            modifier = Modifier
                .size(52.dp)
                .scale(mainButtonScale),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = PrimaryGreen,
                contentColor = DarkBackground
            ),
            shape = CircleShape
        ) {
            Icon(
                imageVector = if (stopwatchState == StopwatchState.RUNNING)
                    Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = if (stopwatchState == StopwatchState.RUNNING) "Pause" else "Start",
                modifier = Modifier.size(28.dp),
                tint = DarkBackground
            )
        }

        // ── Reset Button ─────────────────────
        SideActionButton(
            icon = Icons.Filled.RestartAlt,
            contentDescription = "Reset",
            enabled = stopwatchState == StopwatchState.PAUSED,
            onClick = onReset
        )
    }
}

/**
 * A smaller circular button used for the Lap and Reset actions.
 */
@Composable
private fun SideActionButton(
    icon: ImageVector,
    contentDescription: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.4f,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "${contentDescription}Alpha"
    )

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .size(40.dp)
            .border(
                width = 1.dp,
                color = ButtonBorder.copy(alpha = alpha),
                shape = CircleShape
            ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ButtonBackground,
            contentColor = TextSecondary
        ),
        shape = CircleShape
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(20.dp),
            tint = TextSecondary.copy(alpha = alpha)
        )
    }
}

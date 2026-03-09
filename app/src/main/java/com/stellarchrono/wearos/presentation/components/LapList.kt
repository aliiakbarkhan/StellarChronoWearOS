package com.stellarchrono.wearos.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Divider
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.stellarchrono.wearos.data.LapRecord
import com.stellarchrono.wearos.presentation.theme.DiffNegative
import com.stellarchrono.wearos.presentation.theme.DiffPositive
import com.stellarchrono.wearos.presentation.theme.TextMuted
import com.stellarchrono.wearos.presentation.theme.TextPrimary
import com.stellarchrono.wearos.presentation.theme.TextSecondary
import com.stellarchrono.wearos.util.TimeFormatter

/**
 * Scrollable list of recorded laps, optimised for round watch screens
 * using [ScalingLazyColumn] which scales items at the edges.
 */
@Composable
fun LapList(
    laps: List<LapRecord>,
    modifier: Modifier = Modifier
) {
    if (laps.isEmpty()) return

    val listState = rememberScalingLazyListState()

    ScalingLazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(
            items = laps,
            key = { it.number }
        ) { lap ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically { it }
            ) {
                LapItem(lap = lap)
            }
        }
    }
}

/**
 * A single lap row: lap number | lap time | delta from previous lap.
 */
@Composable
private fun LapItem(lap: LapRecord) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Lap number
        Text(
            text = "Lap ${lap.number}",
            style = MaterialTheme.typography.body2,
            color = TextSecondary,
            modifier = Modifier.weight(1f)
        )

        // Lap time
        Text(
            text = TimeFormatter.formatCompact(lap.lapTimeMs),
            style = MaterialTheme.typography.body1,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1.2f)
        )

        // Difference from previous lap
        val diffText = TimeFormatter.formatDifference(lap.differenceMs)
        val diffColor = when {
            lap.differenceMs == null -> TextMuted
            lap.differenceMs >= 0 -> DiffPositive
            else -> DiffNegative
        }

        Text(
            text = diffText,
            fontSize = 9.sp,
            color = diffColor,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.8f)
        )
    }

    Divider(
        color = TextMuted.copy(alpha = 0.2f),
        modifier = Modifier.padding(horizontal = 12.dp)
    )
}

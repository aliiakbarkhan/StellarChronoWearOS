package com.stellarchrono.wearos.util

/**
 * Utility object for formatting stopwatch time values.
 */
object TimeFormatter {

    /**
     * Format milliseconds into "MM:SS" string.
     */
    fun formatMinutesSeconds(totalMs: Long): String {
        val totalSeconds = totalMs / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    /**
     * Format the centisecond (hundredths) portion: ".XX"
     */
    fun formatCentiseconds(totalMs: Long): String {
        val centiseconds = (totalMs % 1000) / 10
        return ".%02d".format(centiseconds)
    }

    /**
     * Format milliseconds into "MM:SS.XX" compact string for lap display.
     */
    fun formatCompact(totalMs: Long): String {
        return "${formatMinutesSeconds(totalMs)}${formatCentiseconds(totalMs)}"
    }

    /**
     * Format a time difference in milliseconds into a readable "+X.Xs" or "-X.Xs" string.
     * Returns "--" for null.
     */
    fun formatDifference(differenceMs: Long?): String {
        if (differenceMs == null) return "--"
        val sign = if (differenceMs >= 0) "+" else ""
        val seconds = differenceMs / 1000.0
        return "${sign}%.1fs".format(seconds)
    }
}

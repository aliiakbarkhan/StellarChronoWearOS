package com.stellarchrono.wearos.data

/**
 * Represents a single lap recording.
 *
 * @param number The lap number (1-based).
 * @param lapTimeMs The time taken for this specific lap in milliseconds.
 * @param totalTimeMs The total elapsed time when this lap was recorded.
 * @param differenceMs The difference in time from the previous lap (null for the first lap).
 */
data class LapRecord(
    val number: Int,
    val lapTimeMs: Long,
    val totalTimeMs: Long,
    val differenceMs: Long? = null
)

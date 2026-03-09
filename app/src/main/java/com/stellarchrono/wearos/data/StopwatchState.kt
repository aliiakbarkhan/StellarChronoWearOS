package com.stellarchrono.wearos.data

/**
 * Represents the possible states of the stopwatch.
 */
enum class StopwatchState {
    /** Stopwatch is idle / has not been started yet. */
    IDLE,

    /** Stopwatch is currently running. */
    RUNNING,

    /** Stopwatch is paused and can be resumed or reset. */
    PAUSED
}

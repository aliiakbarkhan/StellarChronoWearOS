package com.stellarchrono.wearos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellarchrono.wearos.data.LapRecord
import com.stellarchrono.wearos.data.StopwatchState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel managing all stopwatch logic.
 *
 * Uses [kotlinx.coroutines] with a tight loop (~16 ms cadence) to update the
 * elapsed time, keeping frame-rate-level accuracy while remaining battery-friendly
 * thanks to structured concurrency (the coroutine is automatically cancelled when
 * the ViewModel is cleared).
 */
class StopwatchViewModel : ViewModel() {

    // ── Exposed state ────────────────────────────────────────────────

    private val _elapsedMs = MutableStateFlow(0L)
    /** Total elapsed time in milliseconds. */
    val elapsedMs: StateFlow<Long> = _elapsedMs.asStateFlow()

    private val _state = MutableStateFlow(StopwatchState.IDLE)
    /** Current stopwatch state (IDLE, RUNNING, PAUSED). */
    val state: StateFlow<StopwatchState> = _state.asStateFlow()

    private val _laps = MutableStateFlow<List<LapRecord>>(emptyList())
    /** List of recorded laps, newest first. */
    val laps: StateFlow<List<LapRecord>> = _laps.asStateFlow()

    // ── Internal bookkeeping ─────────────────────────────────────────

    /** System time (via [System.nanoTime]) when the stopwatch was last resumed. */
    private var startNanos: Long = 0L

    /** Accumulated time in ms across pauses. */
    private var accumulatedMs: Long = 0L

    /** The elapsed time at which the last lap was recorded. */
    private var lastLapMs: Long = 0L

    /** Reference to the coroutine that ticks the timer. */
    private var tickerJob: Job? = null

    // ── Public API ───────────────────────────────────────────────────

    /** Start the stopwatch or resume after a pause. */
    fun start() {
        if (_state.value == StopwatchState.RUNNING) return

        startNanos = System.nanoTime()
        _state.value = StopwatchState.RUNNING
        startTicker()
    }

    /** Pause the running stopwatch. */
    fun pause() {
        if (_state.value != StopwatchState.RUNNING) return

        // Capture the time that passed since the last resume
        accumulatedMs += (System.nanoTime() - startNanos) / 1_000_000
        _state.value = StopwatchState.PAUSED
        stopTicker()
        // Make sure the displayed time is accurate right at the pause moment
        _elapsedMs.value = accumulatedMs
    }

    /** Toggle between start and pause. */
    fun toggleStartPause() {
        when (_state.value) {
            StopwatchState.IDLE, StopwatchState.PAUSED -> start()
            StopwatchState.RUNNING -> pause()
        }
    }

    /** Record a lap while the stopwatch is running. */
    fun lap() {
        if (_state.value != StopwatchState.RUNNING) return

        val currentElapsed = accumulatedMs + (System.nanoTime() - startNanos) / 1_000_000
        val lapTime = currentElapsed - lastLapMs

        val previousLapTime = if (_laps.value.isNotEmpty()) _laps.value.first().lapTimeMs else null
        val difference = if (previousLapTime != null) lapTime - previousLapTime else null

        val newLap = LapRecord(
            number = _laps.value.size + 1,
            lapTimeMs = lapTime,
            totalTimeMs = currentElapsed,
            differenceMs = difference
        )

        _laps.value = listOf(newLap) + _laps.value // Newest first
        lastLapMs = currentElapsed
    }

    /** Reset the stopwatch to its initial state. Only works when paused or idle. */
    fun reset() {
        if (_state.value == StopwatchState.RUNNING) return

        stopTicker()
        accumulatedMs = 0L
        lastLapMs = 0L
        _elapsedMs.value = 0L
        _laps.value = emptyList()
        _state.value = StopwatchState.IDLE
    }

    // ── Internal helpers ─────────────────────────────────────────────

    private fun startTicker() {
        tickerJob?.cancel()
        tickerJob = viewModelScope.launch {
            while (true) {
                val now = System.nanoTime()
                _elapsedMs.value = accumulatedMs + (now - startNanos) / 1_000_000
                // ~60 fps update cadence; keeps UI smooth without burning battery
                delay(16L)
            }
        }
    }

    private fun stopTicker() {
        tickerJob?.cancel()
        tickerJob = null
    }

    override fun onCleared() {
        super.onCleared()
        stopTicker()
    }
}

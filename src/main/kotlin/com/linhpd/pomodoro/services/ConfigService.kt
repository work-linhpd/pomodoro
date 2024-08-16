package com.linhpd.pomodoro.services

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.linhpd.pomodoro.models.entities.PluginConfig
import com.linhpd.pomodoro.models.enums.PomodoroRound

interface ConfigService {
    val config: PluginConfig
    fun roundInSeconds(round: PomodoroRound): Int
    fun roundInMinutes(round: PomodoroRound): Int
    fun updateConfig(newConfig: PluginConfig)
}


@State(
    name = "com.linhpd.pomodoro.ConfigService",
    storages = [Storage("PomodoroConfigService.xml")]
)
class ConfigServiceImpl : ConfigService, PersistentStateComponent<PluginConfig> {
    override var config: PluginConfig = PluginConfig()

    override fun getState(): PluginConfig {
        return config
    }

    override fun loadState(state: PluginConfig) {
        config = state
    }

    override fun roundInSeconds(round: PomodoroRound): Int {
        return roundInMinutes(round).times(60)
    }

    override fun roundInMinutes(round: PomodoroRound): Int {
        return config.intervals[round] ?: 10
    }

    override fun updateConfig(newConfig: PluginConfig) {
        config = newConfig
        loadState(config)
    }

}
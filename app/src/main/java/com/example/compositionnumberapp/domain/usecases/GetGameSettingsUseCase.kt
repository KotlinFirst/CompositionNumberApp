package com.example.compositionnumberapp.domain.usecases

import com.example.compositionnumberapp.domain.entity.GameSettings
import com.example.compositionnumberapp.domain.entity.Level
import com.example.compositionnumberapp.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}
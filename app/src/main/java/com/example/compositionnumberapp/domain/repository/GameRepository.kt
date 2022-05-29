package com.example.compositionnumberapp.domain.repository

import com.example.compositionnumberapp.domain.entity.GameSettings
import com.example.compositionnumberapp.domain.entity.Level
import com.example.compositionnumberapp.domain.entity.Question

interface GameRepository {
    fun generateQuestions(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}
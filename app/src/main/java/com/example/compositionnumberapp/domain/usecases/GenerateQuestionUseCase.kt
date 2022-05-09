package com.example.compositionnumberapp.domain.usecases

import com.example.compositionnumberapp.domain.entity.GameSettings
import com.example.compositionnumberapp.domain.entity.Question
import com.example.compositionnumberapp.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestions(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        const val COUNT_OF_OPTIONS = 6
    }

}
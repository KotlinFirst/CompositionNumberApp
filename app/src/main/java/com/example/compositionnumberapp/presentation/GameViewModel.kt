package com.example.compositionnumberapp.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.compositionnumberapp.R
import com.example.compositionnumberapp.data.GameRepositoryImpl
import com.example.compositionnumberapp.domain.entity.GameResult
import com.example.compositionnumberapp.domain.entity.GameSettings
import com.example.compositionnumberapp.domain.entity.Level
import com.example.compositionnumberapp.domain.entity.Question
import com.example.compositionnumberapp.domain.repository.GameRepository
import com.example.compositionnumberapp.domain.usecases.GenerateQuestionUseCase
import com.example.compositionnumberapp.domain.usecases.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GameRepositoryImpl
    private val context = application

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private var timer: CountDownTimer? = null

    private val _formatedTime = MutableLiveData<String>()
    val formatedTime: LiveData<String>
        get() = _formatedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int>
        get() = _percentOfRightAnswer

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0


    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        generateQuestion()
    }

    fun chooseAnswer(number: Int) {
        val rightAnswer = _question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
        updateProgress()
        generateQuestion()
    }


    private fun updateProgress() {
        val percent = (countOfRightAnswers * 100 / countOfQuestions)
        _percentOfRightAnswer.value = percent

        _progressAnswers.value = String.format(
            context.resources.getString(R.string.tv_answer_progress),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeSeconds * MILLES_IN_SECONDS, MILLES_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formatedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLES_IN_SECONDS
        val minutes = seconds / SECOND_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECOND_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        countOfRightAnswers = 0
        countOfQuestions = 0
        _gameResult.value = GameResult(
            _enoughPercent.value == true && _enoughCount.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        val MILLES_IN_SECONDS = 1000L
        val SECOND_IN_MINUTES = 60
    }
}
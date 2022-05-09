package com.example.compositionnumberapp.domain.entity

data class GameResult(
    val winner:Boolean,
    val countOfRightAnswer:Int,
    val countOfQuestion:Int,
    val gameSettings: GameSettings
) {
}
package com.example.compositionnumberapp.domain.entity

data class Question(
    val sum:Int,
    val VisibleNumber:Int,
    val options:List<Int>
) {
}
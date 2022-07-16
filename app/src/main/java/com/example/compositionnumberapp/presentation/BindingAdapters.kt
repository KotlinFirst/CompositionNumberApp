package com.example.compositionnumberapp.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.compositionnumberapp.R
import com.example.compositionnumberapp.domain.entity.GameResult

interface OnOptionClickListener{
    fun onOptionClick(option:Int)
}

@BindingAdapter("iv_result")
fun ivResult(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getSmileResId(winner))
}

private fun getSmileResId(winner: Boolean): Int {
    return if (winner) {
        R.drawable.victory
    } else R.drawable.lost
}

@BindingAdapter("need_right_answers")
fun bindRightAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.need_right_answers),
        count
    )
}

@BindingAdapter("game_score")
fun gameScore(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.game_score),
        count
    )
}

@BindingAdapter("need_percent_right_answer")
fun needPercentRightAnswer(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.need_percent_right_answer),
        count
    )
}

@BindingAdapter("percent_right_answer")
fun percentRightAnswer(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.percent_right_answer),
        getPercentOfRightAnswer(gameResult)
    )
}

private fun getPercentOfRightAnswer(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestion == 0) 0
    else (countOfRightAnswer * 100 / countOfQuestion)
}

@BindingAdapter("tv_sum")
fun bindSum(textView: TextView,count: Int){
    textView.text = count.toString()
}

@BindingAdapter("tv_left_number")
fun bindLeftNumber(textView: TextView,count: Int){
    textView.text = count.toString()
}

@BindingAdapter("tv_answer_progress_color")
fun bindAnswerProgress(textView: TextView,enoughtCount:Boolean){
textView.setTextColor(getEnoughColor(enoughtCount,textView.context))
}

@BindingAdapter("progress_bar")
fun bindProgressBar(progressBar: ProgressBar,percentOfRightAnswer:Int){
    progressBar.setProgress(percentOfRightAnswer, true)
}

@BindingAdapter("secondary_progress")
fun bindSecondaryProgress(progressBar: ProgressBar,minPercent:Int){
    progressBar.secondaryProgress = minPercent
}

@BindingAdapter("progress_tint_list")
fun bindProgressTintList(progressBar: ProgressBar,enoughtPercent:Boolean){
    val color = getEnoughColor(enoughtPercent,progressBar.context)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}
private fun getEnoughColor(boolean: Boolean,context:Context): Int {
    val colorResId = if (boolean) {
        android.R.color.holo_green_light
    } else android.R.color.holo_red_light
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("onOptionClickListener")
fun bindOptionClickListener(textView: TextView, clickListener:OnOptionClickListener){
textView.setOnClickListener {
    clickListener.onOptionClick(textView.text.toString().toInt())
}
}


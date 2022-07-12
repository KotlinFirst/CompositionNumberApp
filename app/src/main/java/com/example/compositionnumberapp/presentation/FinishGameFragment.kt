package com.example.compositionnumberapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.compositionnumberapp.R
import com.example.compositionnumberapp.databinding.FragmentFinishGameBinding
import com.example.compositionnumberapp.domain.entity.GameResult
import com.example.compositionnumberapp.domain.entity.Level
import java.lang.RuntimeException

class FinishGameFragment : Fragment() {

    private val args by navArgs<FinishGameFragmentArgs>()

    private var _binding: FragmentFinishGameBinding? = null
    private val binding: FragmentFinishGameBinding
        get() = _binding ?: throw RuntimeException("FragmentFinishGameBinding = null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        bindViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindViews() {
        binding.ivResult.setImageResource(getSmileResId())
        binding.needRightAnswers.text = String.format(
            getString(R.string.need_right_answers),
            args.result.gameSettings.minCountOfRightAnswers
        )
        binding.gameScore.text = String.format(
            getString(R.string.game_score),
            args.result.countOfRightAnswer
        )
        binding.needPercentRightAnswer.text = String.format(
            getString(R.string.need_percent_right_answer),
            args.result.gameSettings.minPercentOfRightAnswers
        )
        binding.percentRightAnswer.text = String.format(
            getString(R.string.percent_right_answer),
            getPercentOfRightAnswer(args.result.countOfQuestion,args.result.countOfRightAnswer)
        )
    }

    private fun getPercentOfRightAnswer(countOfQuestions: Int, countOfRightAnswers: Int): Int {
        if (countOfQuestions == 0) return 0
        return (countOfRightAnswers * 100 / countOfQuestions)
    }

    private fun getSmileResId(): Int {
        return if (args.result.winner) {
            R.drawable.victory
        } else R.drawable.lost
    }


    private fun retryGame() {
        findNavController().popBackStack()
    }

}
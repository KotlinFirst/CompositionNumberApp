package com.example.compositionnumberapp.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.compositionnumberapp.databinding.FragmentGameBinding
import com.example.compositionnumberapp.domain.entity.GameResult


class GameFragment : Fragment() {
    private val args by navArgs<GameFragmentArgs>()

    private val viewModelFactory: GameViewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this, viewModelFactory
        )[GameViewModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListenersToOption()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setClickListenersToOption() {
        for (tvOptions in tvOptions) {
            tvOptions.setOnClickListener {
                viewModel.chooseAnswer(tvOptions.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }
        }
        viewModel.percentOfRightAnswer.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.enoughCount.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.setTextColor(getEnoughColor(it))
        }
        viewModel.enoughPercent.observe(viewLifecycleOwner) {
            val color = getEnoughColor(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.formatedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchFinishFragment(it)
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
    }

    private fun getEnoughColor(boolean: Boolean): Int {
        val colorResId = if (boolean) {
            android.R.color.holo_green_light
        } else android.R.color.holo_red_light
        return ContextCompat.getColor(requireContext(), colorResId)
    }


    private fun launchFinishFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToFinishGameFragment(gameResult)
        )
    }

}
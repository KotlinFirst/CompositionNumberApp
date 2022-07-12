package com.example.compositionnumberapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.compositionnumberapp.R
import com.example.compositionnumberapp.databinding.FragmentLevelSelectionBinding
import com.example.compositionnumberapp.domain.entity.Level
import java.lang.RuntimeException


class LevelSelectionFragment : Fragment() {

    private var _binding: FragmentLevelSelectionBinding? = null
    val binding: FragmentLevelSelectionBinding
        get() = _binding ?: throw RuntimeException("FragmentLevelSelectionBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonLevelTest.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
            buttonLevelEasy.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            buttonLevelNormal.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }
    }

    private fun launchGameFragment(level: Level) {
        findNavController().navigate(
            LevelSelectionFragmentDirections.actionLevelSelectionFragmentToGameFragment(level))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
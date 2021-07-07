package com.hasib.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hasib.guesstheword.R
import com.hasib.guesstheword.databinding.FragmentScoreBinding

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: FragmentScoreBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModelFactory =
            ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(requireArguments()).score)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)

        binding.scoreViewModel = viewModel

        // Navigates back to game when button is pressed
        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(ScoreFragmentDirections.actionScoreToGame())
                viewModel.onPlayAgainComplete()
            }
        })

        return binding.root
    }
}
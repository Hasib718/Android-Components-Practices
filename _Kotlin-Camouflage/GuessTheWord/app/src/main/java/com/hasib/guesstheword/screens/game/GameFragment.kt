package com.hasib.guesstheword.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hasib.guesstheword.R
import com.hasib.guesstheword.databinding.FragmentGameBinding
import timber.log.Timber

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        Timber.i("Called ViewModelProvider")
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(GameViewModel::class.java)

        /** Set the viewmodel for databinding - this allows the bound layout access
        to all the data in the ViewModel **/
        binding.gameViewModel = viewModel

        /** Observer for the Game finished event **/
        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer {
            if (it) gameFinished()
        })

        return binding.root

    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score.value ?: 0
        findNavController().navigate(action)

        viewModel.onGameFinishComplete()
    }
}
package com.hasib.androidhiltdemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hasib.androidhiltdemo.data.LoggerDataSource
import com.hasib.androidhiltdemo.databinding.FragmentButtonsBinding
import com.hasib.androidhiltdemo.di.InMemoryLogger
import com.hasib.androidhiltdemo.navigator.AppNavigator
import com.hasib.androidhiltdemo.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment that displays the database logs.
 */
@AndroidEntryPoint
class ButtonsFragment : Fragment() {

    @InMemoryLogger
    @Inject
    lateinit var logger: LoggerDataSource

    @Inject
    lateinit var navigator: AppNavigator

    private lateinit var binding: FragmentButtonsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentButtonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button1.setOnClickListener {
            logger.addLog("Interaction with 'Button 1'")
        }

        binding.button2.setOnClickListener {
            logger.addLog("Interaction with 'Button 2'")
        }

        binding.button3.setOnClickListener {
            logger.addLog("Interaction with 'Button 3'")
        }

        binding.allLogs.setOnClickListener {
            navigator.navigateTo(Screens.LOGS)
        }

        binding.deleteLogs.setOnClickListener {
            logger.removeLogs()
        }
    }
}
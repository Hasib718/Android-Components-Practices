package com.hasib.androidhiltdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hasib.androidhiltdemo.R
import com.hasib.androidhiltdemo.navigator.AppNavigator
import com.hasib.androidhiltdemo.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Main activity of the application.
 *
 * Container for the Buttons & Logs fragments. This activity simply tracks clicks on buttons.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: navigator.navigateTo(Screens.BUTTONS)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}
package com.hasib.androidhiltdemo.navigator

import androidx.fragment.app.FragmentActivity
import com.hasib.androidhiltdemo.R
import com.hasib.androidhiltdemo.ui.ButtonsFragment
import com.hasib.androidhiltdemo.ui.LogsFragment
import javax.inject.Inject

/**
 * Navigator implementation.
 */
class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {

    override fun navigateTo(screen: Screens) {
        val fragment = when (screen) {
            Screens.BUTTONS -> ButtonsFragment()
            Screens.LOGS -> LogsFragment()
        }

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }
}
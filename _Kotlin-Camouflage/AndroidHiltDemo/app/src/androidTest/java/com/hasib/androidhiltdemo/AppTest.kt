package com.hasib.androidhiltdemo

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hasib.androidhiltdemo.ui.MainActivity
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppTest {

    @Test
    fun happyPath() {
        ActivityScenario.launch(MainActivity::class.java)

        // Check Buttons fragment screen is displayed
        Espresso.onView(withId(R.id.textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Tap on Button 1
        Espresso.onView(withId(R.id.button1)).perform(ViewActions.click())

        // Navigate to Logs screen
        Espresso.onView(withId(R.id.all_logs)).perform(ViewActions.click())

        // Check Logs fragment screen is displayed
        Espresso.onView(ViewMatchers.withText(Matchers.containsString("Interaction with 'Button 1'")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
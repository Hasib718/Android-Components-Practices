package com.example.hellokotlin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.hellokotlin.databinding.ActivityConstraintBinding
import java.lang.Math.random
import kotlin.random.Random

class ConstraintActivity : AppCompatActivity() {

    private lateinit var binding : ActivityConstraintBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstraintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
    }

    private fun setListeners() {
        val clickableViews = listOf(binding.boxOneText, binding.boxTwoText, binding.boxThreeText, binding.boxFourText, binding.boxFiveText, binding.constraintLayout)

        for (item in clickableViews)
            item.setOnClickListener { makeColored(it) }
    }

    private fun makeColored(view: View) {
        val color = Color.argb(255, Random.nextInt(0, 255), Random.nextInt(0, 255), Random.nextInt(0, 255))

        when(view.id) {
            R.id.box_one_text -> view.setBackgroundColor(color)
            R.id.box_two_text -> view.setBackgroundColor(color)
            R.id.box_three_text -> view.setBackgroundColor(color)
            R.id.box_four_text -> view.setBackgroundColor(color)
            R.id.box_five_text -> view.setBackgroundColor(color)
            else -> view.setBackgroundColor(color)
        }
    }
}
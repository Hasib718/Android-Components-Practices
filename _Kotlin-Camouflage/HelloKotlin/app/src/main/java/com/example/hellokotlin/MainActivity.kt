package com.example.hellokotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hellokotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.buttonText.text = "0"
        binding.tapButton.setOnClickListener {
            Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show()

            binding.buttonImage.setImageResource(when((1..6).random()) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                6 -> R.drawable.dice_6
                else -> R.drawable.empty_dice
            })

//            binding.buttonText.text = (binding.buttonText.text.toString().toInt() + 1).toString()
        }

        binding.colorMeButton.setOnClickListener {
            newActivity(ConstraintActivity::class.java)
        }

        binding.aboutMeButton.setOnClickListener {
            newActivity(AboutMeActivity::class.java)
        }
    }

    private fun <T: Activity> newActivity(activity: Class<T>) {
        startActivity(Intent(this, activity))
        finish()
    }
}
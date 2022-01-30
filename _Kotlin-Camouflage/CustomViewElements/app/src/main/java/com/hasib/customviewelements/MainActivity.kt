package com.hasib.customviewelements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val box = findViewById<ValueBox>(R.id.mainBox)
        box.apply {
            setLabel("X")
            setValue("100000")
            setOnClickListener{
                Toast.makeText(context, "Fucking working", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
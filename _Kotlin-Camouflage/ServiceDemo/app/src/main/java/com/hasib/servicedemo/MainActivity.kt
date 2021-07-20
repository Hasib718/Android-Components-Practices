 package com.hasib.servicedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hasib.servicedemo.databinding.ActivityMainBinding

 class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        serviceIntent = Intent(this, MediaPlayerService::class.java)

        binding.startService.setOnClickListener {
            startService(serviceIntent)
        }

        binding.stopService.setOnClickListener {
            stopService(serviceIntent)
        }
    }
}
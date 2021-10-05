package com.hasib.testapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.hasib.testapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {
    
    private val TAG = "MainActivity"

    @ObsoleteCoroutinesApi
    private val scope = CoroutineScope(newFixedThreadPoolContext(2, "scope"))
    
    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        lifecycleScope.launch {
            withContext(Dispatchers.Default) {
                flow<Int> {
                    for (i in 1..5) {
                        delay(1000)
                        emit(i)
                    }
                }.collect { binding.value = it }
            }
            Log.d(TAG, "onCreate: After flow")
        }
        Log.d(TAG, "onCreate: After Blocking")
    }
}
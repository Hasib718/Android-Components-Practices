package com.hasib.coroutinedemo.util

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * An executor service that can run [Runnable]s off the main thread.
 */
val BACKGROUND: ExecutorService = Executors.newFixedThreadPool(2)

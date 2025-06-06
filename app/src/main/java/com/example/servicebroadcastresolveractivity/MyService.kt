package com.example.servicebroadcastresolveractivity

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random.Default.nextInt

class MyService: Service() {

    private lateinit var job: Job
    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        job = scope.launch {
            while (isActive){
                Log.d("MyService", nextInt(5).toString())
                delay(1000)
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        job.cancel()
        Log.d("MyService", "Остановка сервиса")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
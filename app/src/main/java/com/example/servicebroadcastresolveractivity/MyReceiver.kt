package com.example.servicebroadcastresolveractivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_POWER_CONNECTED){
            Log.d("MyReceiver", "Зарядка подключена")
        }else if(intent?.action == Intent.ACTION_POWER_DISCONNECTED){
            Log.d("MyReceiver", "Зарядка отключена")
        }
    }
}

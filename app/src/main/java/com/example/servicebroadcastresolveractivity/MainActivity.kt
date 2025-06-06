package com.example.servicebroadcastresolveractivity

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

const val TAG_LIFECYCLE = "Lifecycle"

class MainActivity : AppCompatActivity() {

    private lateinit var receiver: MyReceiver
    private lateinit var adapter: RCAdapter

    val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            adapter.addAllNames(getContact())
        }else{
            Toast.makeText(this, "Включайте разрешение в настройках теперь", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG_LIFECYCLE, "onCreate()")

        var isServiceStarted = false
        receiver = MyReceiver()

        //Регистрация receiver и интент фильтра
        val filter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)

        registerReceiver(receiver, filter)

        //Кнопка для запуска и остановки сервиса
        findViewById<Button>(R.id.button).setOnClickListener {
            if (isServiceStarted){
                stopService(Intent(this, MyService::class.java))
                isServiceStarted = false
            } else {
                startService(Intent(this, MyService::class.java))
                isServiceStarted = true
            }
        }

        //Инициализация адаптера и RecyclerView
        adapter = RCAdapter()
        val rcView = findViewById<RecyclerView>(R.id.RCView)
        rcView.adapter = adapter
        rcView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        when{
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED -> {
                        adapter.addAllNames(getContact())
                    }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) ->{
                Toast.makeText(this, "Нужно теперь в настройках включать разрешение", Toast.LENGTH_LONG).show()
            }
            else->{
                permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG_LIFECYCLE, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG_LIFECYCLE, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG_LIFECYCLE, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG_LIFECYCLE, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG_LIFECYCLE, "onDestroy()")

        //Отписка от получения интентов
        unregisterReceiver(receiver)
    }

    //Метод для получения контактов
    private fun getContact(): List<String>{
        val currentNameList = mutableListOf<String>()
        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        cursor?.use {
            while (it.moveToNext()){
                val name = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                currentNameList.add(name)
            }
        }
        return currentNameList
    }
}
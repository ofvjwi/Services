package com.example.services.activity

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.services.databinding.ActivityMainBinding
import com.example.services.services.BoundService
import com.example.services.services.StartedService

class MainActivity : AppCompatActivity() {

    private var boundService: BoundService? = null
    private var isBound: Boolean = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.startStartedService.setOnClickListener { startStartedService() }
        binding.stopStartedService.setOnClickListener { stopStartedService() }
        binding.startBoundService.setOnClickListener { startBoundService() }
        binding.stopBoundService.setOnClickListener { stopBoundService() }
        binding.printTimeStamp.setOnClickListener { binding.timeStampTextView.text = boundService?.timeStamp }
    }


    // Start Service
    private fun startStartedService() {
        startService(Intent(this, StartedService::class.java))
    }
    private fun stopStartedService() {
        stopService(Intent(this, StartedService::class.java))
    }


    // Bound Service
    private fun startBoundService() {
        val intent = Intent(this, BoundService::class.java)
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
    }
    private fun stopBoundService() {
        if (isBound) {
            unbindService(mServiceConnection)
            isBound = false
        }
    }
    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder: BoundService.MyBinder = service as BoundService.MyBinder
            boundService = myBinder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }
    }
}



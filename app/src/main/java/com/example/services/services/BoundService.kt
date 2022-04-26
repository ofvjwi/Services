package com.example.services.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.Toast
import java.io.FileDescriptor
import java.io.PrintWriter

class BoundService : Service() {

    private val mBinder: IBinder = MyBinder()
    private var mChronometer: Chronometer? = null

    override fun onBind(intent: Intent?): IBinder? {
        Toast.makeText(this, "Bound Service onBind", Toast.LENGTH_SHORT).show()
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Bound Service Created", Toast.LENGTH_SHORT).show()
        mChronometer = Chronometer(this)
        mChronometer!!.base = SystemClock.elapsedRealtime()
        mChronometer!!.onChronometerTickListener =
            Chronometer.OnChronometerTickListener { chronometer ->
                Toast.makeText(application, chronometer.base.toString(), Toast.LENGTH_SHORT).show()
            }
        mChronometer!!.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Bound Service Stopped", Toast.LENGTH_SHORT).show()
        mChronometer!!.stop()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Toast.makeText(this, "Bound Service onUnBind", Toast.LENGTH_SHORT).show()
        //  return super.onUnbind(intent)
        return true
    }

    override fun onRebind(intent: Intent?) {
        Toast.makeText(this, "Bound Service onRebind", Toast.LENGTH_SHORT).show()
        super.onRebind(intent)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }

    override fun dump(fd: FileDescriptor?, writer: PrintWriter?, args: Array<out String>?) {
        super.dump(fd, writer, args)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    val timeStamp: String
        get() {
            val elapsedMillis = SystemClock.elapsedRealtime() - mChronometer!!.base
            val hours = (elapsedMillis / 3_600_000).toInt()
            val minutes = (elapsedMillis - hours * 3_600_000).toInt() / 60_000
            val seconds = (elapsedMillis - hours * 3_600_000 - minutes * 60_000).toInt() / 1_000
            val millis =
                (elapsedMillis - hours * 3_600_000 - minutes * 60_000 - seconds * 1_000).toInt()
            return "$hours:$minutes:$seconds:$millis"
        }

     inner class MyBinder : Binder() {
        // Return this instance of LocalService so clients can call public method
        fun getService(): BoundService = this@BoundService
    }

}







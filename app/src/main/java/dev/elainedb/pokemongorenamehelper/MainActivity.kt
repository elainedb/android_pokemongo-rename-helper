package dev.elainedb.pokemongorenamehelper

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "some_channel_id"
        val channelName = "Some Channel"
        val importance = NotificationManager.IMPORTANCE_LOW
        val notificationChannel = NotificationChannel(channelId, channelName, importance)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private var mNotifService: NotifService? = null
    private var mNotifBound = false

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.sample_main)
//    }

    /**
     * Create and show a notification with a custom layout.
     * This callback is defined through the 'onClick' attribute of the
     * 'Show Notification' button in the XML layout.
     *
     * @param v
     */
    fun showNotificationClicked(v: View) {
        mNotifService!!.createNotification()
    }

    //connect to the NotifService
    private val notifConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as NotifService.NotifBinder
            mNotifService = binder.service
            mNotifBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mNotifService = null
            mNotifBound = false
        }
    }

    private fun doUnbindNotifService() {
        unbindService(notifConnection)
        mNotifBound = false
    }

    private fun doBindToNotifService() {
        if (!mNotifBound) {
            val bindIntent = Intent(this, NotifService::class.java)
            mNotifBound = bindService(bindIntent, notifConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStart() {
        super.onStart()
        doBindToNotifService()
    }

    override fun onStop() {
        super.onStop()
        doUnbindNotifService() // HMMMMMM
    }

    override fun onDestroy() {
        super.onDestroy()

        if (isFinishing) {
            //            stop service as activity being destroyed and we won't use it any more
            val intentStopService = Intent(this, NotifService::class.java)
            stopService(intentStopService)
        }
    }
}

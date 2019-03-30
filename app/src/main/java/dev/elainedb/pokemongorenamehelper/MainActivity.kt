package dev.elainedb.pokemongorenamehelper

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    private lateinit var uiModel: MainUi
    private lateinit var mNotificationHelper: NotificationHelper

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        private const val NOTIFICATION = 1300
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mNotificationHelper = NotificationHelper(this)
        uiModel = MainUi()
    }

    private fun sendNotification(id: Int) {
        when (id) {
            NOTIFICATION -> mNotificationHelper.notify(
                id, mNotificationHelper.getNotification(
                    getString(R.string.title),
                    getString(R.string.body)
                )
            )
        }
    }

    internal inner class MainUi : View.OnClickListener {

        init {
            button.setOnClickListener(this)
//            dm_channel_settings_button.setOnClickListener(this)
//            go_to_settings_button.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            when (view.id) {
                R.id.button -> sendNotification(NOTIFICATION)
//                R.id.dm_channel_settings_button -> goToNotificationChannelSettings(DM_CHANNEL)
//                R.id.go_to_settings_button -> goToNotificationSettings()
                else -> Log.e(TAG, getString(R.string.error_click))
            }
        }
    }
}

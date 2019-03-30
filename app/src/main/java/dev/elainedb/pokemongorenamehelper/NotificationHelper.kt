package dev.elainedb.pokemongorenamehelper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.*
import android.widget.RemoteViews

/**
 * Helper class to manage notification channels, and create notifications.
 */
internal class NotificationHelper(context: Context) : ContextWrapper(context) {

    companion object {
        const val CHANNEL_ID = "channel"
        const val EXTRA_VALUE = "extra_value"
        const val PREFS_ID = "prefs"
        const val PREFS_BUFFER_KEY = "key_buffer"
        const val CLIPDATA_LABEL = "label"
    }

    private val notificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val smallIcon: Int
        get() = R.drawable.ic_notif

    init {
        val channel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )

        notificationManager.createNotificationChannel(channel)
        channel.setShowBadge(false)
    }

    private fun initPendingSwitchIntent(expandedView: RemoteViews, requestCode: Int, resId: Int) {
        val clickIntent = Intent(this, ClickNotificationBroadcastReceiver::class.java)
        clickIntent.putExtra(EXTRA_VALUE, requestCode)
        val pendingIntent =
            PendingIntent.getBroadcast(this, requestCode, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        expandedView.setOnClickPendingIntent(resId, pendingIntent)
    }

    fun getNotification(title: String, body: String): Notification.Builder {
        val contentView = RemoteViews(packageName, R.layout.notification)
        val text = resources.getString(R.string.collapsed)
        contentView.setTextViewText(R.id.textView, text)

        val expandedView = RemoteViews(packageName, R.layout.notification_expanded)

        val ids = listOf(
            R.id.ll_0, R.id.ll_1, R.id.ll_2, R.id.ll_3, R.id.ll_4, R.id.ll_5,
            R.id.ll_6, R.id.ll_7, R.id.ll_8, R.id.ll_9, R.id.ll_10,
            R.id.ll_11, R.id.ll_12, R.id.ll_13, R.id.ll_14, R.id.ll_15
        )

        ids.forEachIndexed { index, id ->
            initPendingSwitchIntent(expandedView, index, id)
        }

        return Notification.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(smallIcon)
            .setAutoCancel(true)
            .setCustomContentView(contentView)
            .setCustomBigContentView(expandedView)
    }

    fun notify(id: Int, notification: Notification.Builder) {
        notificationManager.notify(id, notification.build())
    }

    class ClickNotificationBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val value = intent.getIntExtra(EXTRA_VALUE, 0)

            val clipValue = getClip(value)

            var oldText: String?
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            val sharedPref = context.getSharedPreferences(PREFS_ID, Context.MODE_PRIVATE)
            oldText = sharedPref.getString(PREFS_BUFFER_KEY, "")

            val len = oldText.length
            if (oldText.length > 2) {
                oldText = oldText.substring(len - 2, len)
            }

            val clip = ClipData.newPlainText(CLIPDATA_LABEL, oldText + clipValue)
            clipboard.primaryClip = clip

            with(sharedPref.edit())
            {
                putString(PREFS_BUFFER_KEY, oldText + clipValue)
                commit()
            }
        }

        // Enclosed Alphanumerics
        private fun getClip(value: Int) = when (value) {
            0 -> "Ⓞ"
            1 -> "①"
            2 -> "②"
            3 -> "③"
            4 -> "④"
            5 -> "⑤"
            6 -> "⑥"
            7 -> "⑦"
            8 -> "⑧"
            9 -> "⑨"
            10 -> "⑩"
            11 -> "⑪"
            12 -> "⑫"
            13 -> "⑬"
            14 -> "⑭"
            else -> "⑮"
        }
    }
}

package dev.elainedb.pokemongorenamehelper

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.*
import android.os.Binder
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat.Builder
import java.text.DateFormat
import java.util.*
import androidx.core.content.ContextCompat.getSystemService



class NotifService: Service() {

    private val notifBinder = NotifBinder()

    private var NOTIFICATION_ID = 8855447
    private lateinit var mNotificationManager: NotificationManager
    private var mPendingSwitchIntentNormal: PendingIntent? = null
    private var mPendingSwitchIntentFire: PendingIntent? = null
    private var mPendingSwitchIntentWater: PendingIntent? = null
    private var mPendingSwitchIntentElectric: PendingIntent? = null
    private var mPendingSwitchIntentGrass: PendingIntent? = null
    private var mPendingSwitchIntentIce: PendingIntent? = null
    private var mPendingSwitchIntentFighting: PendingIntent? = null
    private var mPendingSwitchIntentPoison: PendingIntent? = null
    private var mPendingSwitchIntentGround: PendingIntent? = null
    private var mPendingSwitchIntentFlying: PendingIntent? = null
    private var mPendingSwitchIntentPsychic: PendingIntent? = null
    private var mPendingSwitchIntentBug: PendingIntent? = null
    private var mPendingSwitchIntentRock: PendingIntent? = null
    private var mPendingSwitchIntentGhost: PendingIntent? = null
    private var mPendingSwitchIntentDragon: PendingIntent? = null
    private var mPendingSwitchIntentDark: PendingIntent? = null

    override fun onBind(arg0: Intent): IBinder? {
        return notifBinder
    }

    inner class NotifBinder : Binder() {
        internal val service: NotifService
            get() = this@NotifService
    }

    // called when the service starts from a call to startService()
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        createNotification()
        return Service.START_STICKY
    }

    override fun onDestroy() {
        stopForeground(true)
    }

    fun createNotification() {
        // BEGIN_INCLUDE(notificationCompat)
        val builder = Builder(this, "some_channel_id")
        // END_INCLUDE(notificationCompat)

        // BEGIN_INCLUDE(ticker)
        // Sets the ticker text
        builder.setTicker(resources.getString(R.string.custom_notification))

        // Sets the small icon for the ticker
        builder.setSmallIcon(R.drawable.ic_notif)
        // END_INCLUDE(ticker)

        // BEGIN_INCLUDE(buildNotification)
        // Cancel the notification when clicked
        builder.setAutoCancel(true)

        // Build the notification
        val notification = builder.build()
        // END_INCLUDE(buildNotification)

        // BEGIN_INCLUDE(customLayout)
        // Inflate the notification layout as RemoteViews
        val contentView = RemoteViews(packageName, R.layout.notification)

        // Set text on a TextView in the RemoteViews programmatically.
        val time = DateFormat.getTimeInstance().format(Date()).toString()
        val text = resources.getString(R.string.collapsed, time)
        contentView.setTextViewText(R.id.textView, text)

        /* Workaround: Need to set the content view here directly on the notification.
         * NotificationCompatBuilder contains a bug that prevents this from working on platform
         * versions HoneyComb.
         * See https://code.google.com/p/android/issues/detail?id=30495
         */
        notification.contentView = contentView

        //        notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;

        // Add a big content view to the notification if supported.
        // Support for expanded notifications was added in API level 16.
        // (The normal contentView is shown when the notification is collapsed, when expanded the
        // big content view set here is displayed.)
        // Inflate and set the layout for the expanded notification view
        val expandedView = RemoteViews(packageName, R.layout.notification_expanded)
        notification.bigContentView = expandedView
        // END_INCLUDE(customLayout)

        initPendingSwitchIntents()

        expandedView.setOnClickPendingIntent(R.id.ll_normal, mPendingSwitchIntentNormal)
        expandedView.setOnClickPendingIntent(R.id.ll_fire, mPendingSwitchIntentFire)
        expandedView.setOnClickPendingIntent(R.id.ll_water, mPendingSwitchIntentWater)
        expandedView.setOnClickPendingIntent(R.id.ll_electric, mPendingSwitchIntentElectric)
        expandedView.setOnClickPendingIntent(R.id.ll_grass, mPendingSwitchIntentGrass)
        expandedView.setOnClickPendingIntent(R.id.ll_ice, mPendingSwitchIntentIce)
        expandedView.setOnClickPendingIntent(R.id.ll_fighting, mPendingSwitchIntentFighting)
        expandedView.setOnClickPendingIntent(R.id.ll_poison, mPendingSwitchIntentPoison)
        expandedView.setOnClickPendingIntent(R.id.ll_ground, mPendingSwitchIntentGround)
        expandedView.setOnClickPendingIntent(R.id.ll_flying, mPendingSwitchIntentFlying)
        expandedView.setOnClickPendingIntent(R.id.ll_psychic, mPendingSwitchIntentPsychic)
        expandedView.setOnClickPendingIntent(R.id.ll_bug, mPendingSwitchIntentBug)
        expandedView.setOnClickPendingIntent(R.id.ll_rock, mPendingSwitchIntentRock)
        expandedView.setOnClickPendingIntent(R.id.ll_ghost, mPendingSwitchIntentGhost)
        expandedView.setOnClickPendingIntent(R.id.ll_dragon, mPendingSwitchIntentDragon)
        expandedView.setOnClickPendingIntent(R.id.ll_dark, mPendingSwitchIntentDark)

        // START_INCLUDE(notify)
        // Use the NotificationManager to show the notification
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(NOTIFICATION_ID, notification)
        //        startForeground(NOTIFICATION_ID, notification);
        // END_INCLUDE(notify)
    }

    private fun initPendingSwitchIntents() {
        mPendingSwitchIntentNormal = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java),0)
        mPendingSwitchIntentFire = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 1)
        mPendingSwitchIntentWater = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 2)
        mPendingSwitchIntentElectric = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 3)
        mPendingSwitchIntentGrass = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 4)
        mPendingSwitchIntentIce = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 5)
        mPendingSwitchIntentFighting = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 6)
        mPendingSwitchIntentPoison = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 7)
        mPendingSwitchIntentGround = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 8)
        mPendingSwitchIntentFlying = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 9)
        mPendingSwitchIntentPsychic = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 10)
        mPendingSwitchIntentBug = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 11)
        mPendingSwitchIntentRock = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 12)
        mPendingSwitchIntentGhost = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 13)
        mPendingSwitchIntentDragon = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 14)
        mPendingSwitchIntentDark = initSinglePendingSwitchIntent(Intent(this, ClickButtonListener::class.java), 15)
    }

    private fun initSinglePendingSwitchIntent(clickIntent: Intent, requestCode: Int): PendingIntent {
        clickIntent.putExtra("value", requestCode)
        return PendingIntent.getBroadcast(this, requestCode, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    class ClickButtonListener : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val value = intent.getIntExtra("value", 0)
            var clipValue = "⑮"

            //Enclosed Alphanumerics

            when (value) {
                0 -> clipValue = "Ⓞ"
                1 -> clipValue = "①"
                2 -> clipValue = "②"
                3 -> clipValue = "③"
                4 -> clipValue = "④"
                5 -> clipValue = "⑤"
                6 -> clipValue = "⑥"
                7 -> clipValue = "⑦"
                8 -> clipValue = "⑧"
                9 -> clipValue = "⑨"
                10 -> clipValue = "⑩"
                11 -> clipValue = "⑪"
                12 -> clipValue = "⑫"
                13 -> clipValue = "⑬"
                14 -> clipValue = "⑭"
                15 -> clipValue = "⑮"

            }

            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val oldClip = clipboard.primaryClip?.getItemAt(0)
            var oldText = ""

            oldClip?.let {
                oldText = it.text.toString()
            }

            val clip = ClipData.newPlainText("label", oldText + clipValue)
            clipboard.primaryClip = clip
        }

    }

}
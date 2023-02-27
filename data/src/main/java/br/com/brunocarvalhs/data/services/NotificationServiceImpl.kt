package br.com.brunocarvalhs.data.services

import android.app.AppOpsManager
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import br.com.brunocarvalhs.domain.services.NotificationService
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class NotificationServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseMessaging: FirebaseMessaging,
    private val dataStoreService: DataStoreService,
) : NotificationService {

    override fun sendRemoteNotification(token: String, title: String, message: String) {
        val notification = RemoteMessage.Builder(token)
            .setMessageId(UUID.randomUUID().toString())
            .addData("title", title)
            .addData("message", message)
            .build()

        if (isPushNotificationEnabled()) firebaseMessaging.send(notification)
    }

    override fun sendRemoteNotification(tokens: List<String>, title: String, message: String) {
        tokens.forEach { sendRemoteNotification(it, title, message) }
    }

    override fun showLocalNotification(title: String, message: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (isPushNotificationEnabled()) notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun clearLocalNotifications() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (isPushNotificationEnabled()) notificationManager.cancelAll()
    }

    override fun isPushNotificationEnabled(): Boolean {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val areNotificationsEnabled: Boolean = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = notificationManager.getNotificationChannel(CHANNEL_ID)
                notificationChannel?.importance != NotificationManager.IMPORTANCE_NONE
            } else {
                val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
                val appInfo = context.applicationInfo
                val pkg = context.applicationContext.packageName
                val uid = appInfo.uid
                val appOpsClass = AppOpsManager::class.java
                val appOpsMethod = appOpsClass.getMethod(
                    "checkOpNoThrow",
                    Integer.TYPE,
                    Integer.TYPE,
                    String::class.java
                )
                val opPostNotificationValue =
                    appOpsClass.getDeclaredField("OP_POST_NOTIFICATION").getInt(appOps)
                val result = appOpsMethod.invoke(appOps, opPostNotificationValue, uid, pkg) as Int
                result == AppOpsManager.MODE_ALLOWED
            }
        } else {
            notificationManager.areNotificationsEnabled()
        }

        return areNotificationsEnabled && dataStoreService.get(
            "notifications",
            areNotificationsEnabled
        )
    }


    companion object {
        const val CHANNEL_ID: String = "CHANNELID"
        const val NOTIFICATION_ID: Int = 1893735679
    }
}
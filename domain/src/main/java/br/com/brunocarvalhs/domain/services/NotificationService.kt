package br.com.brunocarvalhs.domain.services


interface NotificationService {
    fun sendRemoteNotification(token: String, title: String, message: String)

    fun sendRemoteNotification(tokens: List<String>, title: String, message: String)

    fun clearLocalNotifications()

    fun isPushNotificationEnabled(): Boolean

    fun showLocalNotification(title: String, message: String)
}
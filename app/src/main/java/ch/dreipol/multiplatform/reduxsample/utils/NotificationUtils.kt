package ch.dreipol.multiplatform.reduxsample.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import ch.dreipol.multiplatform.reduxsample.MainActivity
import ch.dreipol.multiplatform.reduxsample.R
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import ch.dreipol.multiplatform.reduxsample.shared.utils.formatDisposalDateForNotification

private const val REMINDER_CHANNEL_ID = "reminder_channel"

fun getReminderNotificationText(context: Context, disposal: Disposal): String {
    // TODO return correct text
    val disposalType = context.getString(disposal.disposalType.translationKey)
    val date = formatDisposalDateForNotification(disposal)
    return String.format("$date: $disposalType")
}

fun showReminderNotification(context: Context, disposalType: DisposalType, text: String) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    createReminderChannel(notificationManager)
    val title: String = context.resources.getString(R.string.app_name_android)

    // Assign BigText style notification - can be opened by the user
    val bigText = NotificationCompat.BigTextStyle()
    bigText.setBigContentTitle(title)
    bigText.bigText(text)

    val mBuilder = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
        .setSmallIcon(context.resources.getIdentifier(disposalType.iconId, "drawable", context.packageName))
        .setContentTitle(title)
        .setChannelId(REMINDER_CHANNEL_ID)
        .setContentText(text)
        .setAutoCancel(true)
        .setColor(context.resources.getColor(R.color.test_app_blue, null))
        .setStyle(bigText)

    val resultIntent = Intent(context, MainActivity::class.java)
    val resultPendingIntent = PendingIntent.getActivity(
        context,
        0,
        resultIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    mBuilder.setContentIntent(resultPendingIntent)
    notificationManager.notify(disposalType.name.hashCode(), mBuilder.build())
}

private fun createReminderChannel(notificationManager: NotificationManager) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val reminderChannel = NotificationChannel(REMINDER_CHANNEL_ID, "TODO", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(reminderChannel)
    }
}
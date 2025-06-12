package com.vipint.newsapp.worker

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vipint.newsapp.R
import com.vipint.newsapp.data.api.NetworkService
import com.vipint.newsapp.data.local.DatabaseService
import com.vipint.newsapp.data.model.toArticleEntity
import com.vipint.newsapp.ui.main.MainActivity
import com.vipint.newsapp.utils.AppConstants
import com.vipint.newsapp.utils.AppConstants.NOTIFICATION_ID
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NewsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        lateinit var result: Result
        kotlin.runCatching {
            val articles =
                networkService.fetchTopHeadlines(AppConstants.COUNTRY).articles.map { it.toArticleEntity() }
            databaseService.deleteAllInsertAll(articles)
        }.onSuccess {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendNotification()
            }
            result = Result.success()
        }.onFailure {
            result = Result.retry()
        }
        return result
    }

    @SuppressLint("NewApi", "NotificationPermission")
    private fun sendNotification() {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel
        val channel = NotificationChannel(
            AppConstants.NOTIFICATION_CHANNEL_ID,
            AppConstants.NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        // Create an Intent for the NewsActivity
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("notification_id", NOTIFICATION_ID)

        // Create a PendingIntent
        val pendingIntent = getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val notification =
            NotificationCompat.Builder(applicationContext, AppConstants.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_news)
                .setContentText(AppConstants.NOTIFICATION_CONTENT_TEXT)
                .setContentTitle(AppConstants.NOTIFICATION_CONTENT_TITLE)
                .setContentIntent(pendingIntent)
                .build()

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}



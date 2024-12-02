package com.example.wif_ip_alerts

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class CyberAlertWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        // Simulating cyber alert scenarios
        sendNotification("New IP connection detected!")
        delay(5000)
        sendNotification("Wi-Fi connection failed!")
        delay(5000)
        sendNotification("New device connected via Wi-Fi!")

        return Result.success()
    }

    private fun sendNotification(message: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(applicationContext, "CYBER_ALERTS")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Cyber Alert")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
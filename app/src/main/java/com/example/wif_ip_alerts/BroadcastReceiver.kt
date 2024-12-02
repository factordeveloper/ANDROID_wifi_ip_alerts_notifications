package com.example.wif_ip_alerts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NetworkBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        when {
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> {
                sendNotification(context, "Connected to Wi-Fi", "You are now connected to a Wi-Fi network.")
            }
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> {
                sendNotification(context, "Using Mobile Data", "You are now using mobile data.")
            }
            else -> {
                sendNotification(context, "No Internet", "You are disconnected from the network.")
            }
        }
    }

    private fun sendNotification(context: Context, title: String, message: String) {
        val notification = NotificationCompat.Builder(context, "CYBER_ALERTS")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(System.currentTimeMillis().toInt(), notification)
    }
}
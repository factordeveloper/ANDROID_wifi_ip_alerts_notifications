package com.example.wif_ip_alerts

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.wif_ip_alerts.ui.theme.CyberAlertsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create Notification Channel
        createNotificationChannel()

        setContent {
            CyberAlertsTheme {
                MainScreen(
                    onScanWifi = { scanWifiNetworks() }
                )
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Cyber Alerts"
            val descriptionText = "Channel for Cyber Alerts notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CYBER_ALERTS", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(NetworkBroadcastReceiver(), intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(NetworkBroadcastReceiver())
    }

    private fun scanWifiNetworks() {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiList = if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        } else {
            
        }
        wifiManager.scanResults

        wifiList.forEach { result: ScanResult ->
            Log.d("WiFi", "SSID: ${result.SSID}, BSSID: ${result.BSSID}")
            sendNotification("Wi-Fi Detected", "Found network: ${result.SSID}")
        }
    }

    private fun sendNotification(title: String, message: String) {
        val notification = NotificationCompat.Builder(this, "CYBER_ALERTS")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(this).notify(System.currentTimeMillis().toInt(), notification)
    }
}

private fun Unit.forEach(any: Any) {

}

@Composable
fun MainScreen(onScanWifi: () -> Unit) {
    Column {
        Button(onClick = onScanWifi) {
            Text("Scan Wi-Fi Networks")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CyberAlertsTheme {
        MainScreen(onScanWifi = {})
    }
}

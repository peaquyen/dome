package com.github.peaquyen.dome

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

//private fun showTimePicker() {
//
//    var picker = MaterialTimePicker.Builder()
//        .setTimeFormat(TimeFormat.CLOCK_12H)
//        .setHour(12)
//        .setMinute(10)
//        .setTitleText("Select Alarm Time")
//        .build()
//
//    picker.show(supportFragmentManager, "xiupea")
//
//    picker.addOnPositiveButtonClickListener {
//        val hour = picker.hour
//        val minute = picker.minute
//        val calendar = Calendar.getInstance()
//        calendar.set(Calendar.HOUR_OF_DAY, hour)
//        calendar.set(Calendar.MINUTE, minute)
//        calendar.set(Calendar.SECOND, 0)
//        calendar.set(Calendar.MILLISECOND, 0)
//        startAlarm(calendar.timeInMillis)
//    }
//}
//
//private fun createNotificationChannel() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val name = "Channel for Alarm Manager"
//        val description = "Channel for notifications related to the alarm manager"
//        val importance = NotificationManager.IMPORTANCE_HIGH
//        val channel = NotificationChannel("xiupea", name, importance)
//        channel.description = description
//        val notificationManager = getSystemService(NotificationManager::class.java)
//        notificationManager.createNotificationChannel(channel)
//    }
//}
//
//private fun setAlarm() {
//    createNotificationChannel()
//    showTimePicker()
//}
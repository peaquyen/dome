//package com.github.peaquyen.dome
//
//import android.app.PendingIntent
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//
//class AlarmReceiver : BroadcastReceiver() {
//    override fun onReceive(context: Context?, intent: Intent?) {
//        // When user click the notification, the app will open the DestinationActivity
//        val destinationIntent = Intent(context, DestinationActivity::class.java)
//
//        // flags to clear the activity stack
//        destinationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//        // Create a PendingIntent for the DestinationActivity
//        val pendingIntent = PendingIntent.getActivity(context, 0, destinationIntent, PendingIntent.FLAG_IMMUTABLE)
//
//        // Create a NotificationCompat Builder
//        val builder = NotificationCompat.Builder(context!!,"xiupea")
//            .setSmallIcon(R.drawable.ic_launcher_background)
//            .setContentTitle("Time's Up!")
//            .setContentText("It's dawn time!")
//            .setAutoCancel(true)
//            .setDefaults(NotificationCompat.DEFAULT_ALL)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
//
//        // Get the NotificationManagerCompat
//        val notificationManager = NotificationManagerCompat.from(context)
//
//        // Notify the notification
//        notificationManager.notify(123, builder.build())
//    }
//}
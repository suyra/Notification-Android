package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val NOTIFICATION_ID = 1
    lateinit var  notificationManager : NotificationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager



        basic_notification.setOnClickListener {
            basicNotification()
        }
        cancel_notification.setOnClickListener {
            //to clear all notifications
            notificationManager.cancelAll()
        }


    }

    private fun basicNotification() {
        val micky = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.mickymouse)
        //big picture
        val bigPicStyle = NotificationCompat.BigPictureStyle().
        bigPicture(micky).
        bigLargeIcon(null)

        var builder = NotificationCompat.Builder(this, applicationContext.getString(R.string.notification_channel_id))
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Hurry Up!")
            .setContentText("Watch your favourite episode of Micky Mouse now!")
          //  .setVibrate(longArrayOf(0,1000))
            .setAutoCancel(true)
            .setStyle(bigPicStyle)
            .setLargeIcon(micky)

        //create explicit intent
        val actionIntent = Intent(this,WatchNow::class.java)
        //now create pending intent .
        val pendingIntent = PendingIntent.getActivity(this,NOTIFICATION_ID,actionIntent,PendingIntent.FLAG_UPDATE_CURRENT)

        //add the pendingIntent to the notification
        builder.setContentIntent(pendingIntent)
            //add action button to the notification
            .addAction(R.drawable.mickymouse,applicationContext.resources.getString(R.string.watch_now),pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)


        createChannel(applicationContext.getString(R.string.notification_channel_id),applicationContext.getString(R.string.notification_channel_name))


        notificationManager.notify(NOTIFICATION_ID,builder.build())

    }

    fun createChannel(channelID : String,channelName : String){
        //check version
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time for breakfast"

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }



}
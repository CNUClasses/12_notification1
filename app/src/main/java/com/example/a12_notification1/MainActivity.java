package com.example.a12_notification1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final int MYNOTIFICATION = 1;
    private static final int MYNOTIFICATION1 = 100;
    int progress = 0;
    private int myCount =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //forget this nothing works
        createNotificationChannel();
    }

    public void doNotification(View v) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Just a Notice")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

//		want a progressbar? Do this
        int PROGRESS_MAX = 100;
        int PROGRESS_CURRENT = progress;
        builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
        progress =progress+10;

        //get a manager and post notification

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(MYNOTIFICATION, builder.build());
    }

    public void doCancelNotification(View v) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(MYNOTIFICATION);

        //want a progressbar? Do this
//		progress=0;
    }

    public void doIncrementNotification(View view) {
        myCount++;

        Notification noti = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Notification")
                .setContentText("with user set content, number =" + Integer.toString(myCount))
                .setSmallIcon(R.drawable.ic_launcher)
                .setOngoing(false)
                .build();

        //the following will modify an existing notification over and over
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(MYNOTIFICATION1, noti);
    }

    private static final String CHANNEL_ID = "KP";
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
package com.example.notificationadvanceexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.EditText;

import static com.example.notificationadvanceexample.APP.CHANNEL_1_ID;
import static com.example.notificationadvanceexample.APP.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;

    private MediaSessionCompat mediaSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessage = findViewById(R.id.edit_text_message);

        mediaSession = new MediaSessionCompat(this, "tag");
    }

    public void sendOnChannel1(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);


        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", message);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.sifat);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_4k_24)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(picture)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText(getString(R.string.long_dummy_text))
//                        .setBigContentTitle("Big Content Title")
//                        .setSummaryText("Summary Text"))
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(picture)
                        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
//                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();
        notificationManager.notify(1, notification);
    }

    public void sendOnChannel2(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.drawable.sifat);


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_4k_24)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(artwork)
//                .setStyle(new NotificationCompat.InboxStyle()
//                        .addLine("This is line 1")
//                        .addLine("This is line 2")
//                        .addLine("This is line 3")
//                        .addLine("This is line 4")
//                        .addLine("This is line 5")
//                        .addLine("This is line 6")
//                        .addLine("This is line 7")
//                        .setBigContentTitle("Big Content Title")
//                        .setSummaryText("Summary Text"))
                .addAction(R.drawable.ic_baseline_4k_24, "Dislike", null)
                .addAction(R.drawable.ic_launcher_foreground, "Previous", null)
                .addAction(R.drawable.ic_baseline_4k_24, "Pause", null)
                .addAction(R.drawable.ic_baseline_4k_24, "Next", null)
                .addAction(R.drawable.ic_baseline_4k_24, "Like", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3)
                        .setMediaSession(mediaSession.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        notificationManager.notify(2, notification);
    }
}
package com.example.dwindibudimulia.moviecatalogue.notification;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.dwindibudimulia.moviecatalogue.R;
import com.example.dwindibudimulia.moviecatalogue.activity.MainActivity;
import com.example.dwindibudimulia.moviecatalogue.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class AlarmReceiver extends BroadcastReceiver {
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private static final String EXTRA_TYPE = "type";
    private static final String EXTRA_MESSAGE = "message";
    public static final String RELEASE_REMINDER = "ReleaseMovie";
    public static final String DAILY_REMINDER = "DailyMovie";
    private final static int ID_DAILY_REMINDER = 100;
    private final static int ID_RELEASE_REMINDER = 101;
    private int notifyId;

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        if (type != null) {
            if (type.equalsIgnoreCase(DAILY_REMINDER)) {
                String message = intent.getStringExtra(EXTRA_MESSAGE);
                String title = context.getResources().getString(R.string.app_name);
                notifyId = ID_DAILY_REMINDER;
                showNotification(context, title, message, notifyId);
            } else {
                notifyId = ID_RELEASE_REMINDER;
                checkMovieRelease(new ReleaseMovieCallbacks() {
                    @Override
                    public void onSuccess(ArrayList<Movie> movies) {
                        listMovies = movies;
                        for (int i = 0; i < listMovies.size(); i++) {
                            showNotification(context, listMovies.get(i).getnameMovie(), listMovies.get(i).getnameMovie() + "\t" + context.getResources().getString(R.string.release_today), notifyId);
                        }
                    }

                    @Override
                    public void onFailure(boolean failure) {
                        if (failure) {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    }

    private void showNotification(Context context, String title, String message, int notifyId) {
        String CHANNEL_ID = "Channel_01";
        String CHANNEL_NAME = "Channel_Alarm";

        Intent intent;
        intent = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000,})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            builder.setChannelId(CHANNEL_ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(notifyId, notification);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void checkMovieRelease(final ReleaseMovieCallbacks callbacks) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = simpleDateFormat.format(date);

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItem = new ArrayList<>();
        String API_KEY = " API_KEY";
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" + todayDate + "&primary_release_date.lte=" + todayDate;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movies = list.getJSONObject(i);
                        Movie movie = new Movie(movies, "movie");
                        listItem.add(movie);
                    }
                    callbacks.onSuccess(listItem);
                    callbacks.onFailure(false);
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
                callbacks.onFailure(true);
                callbacks.onSuccess(new ArrayList<Movie>());
            }
        });
    }

    public void setDailyReminder(Context context, String type, boolean check) {
        if (check) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.putExtra(EXTRA_MESSAGE, context.getResources().getString(R.string.daily_reminder));
            intent.putExtra(EXTRA_TYPE, type);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 7);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, intent, 0);
            if (alarmManager != null) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
            Toast.makeText(context, "Pengingat Harian dihidupkan", Toast.LENGTH_SHORT).show();
        } else {
            alarmOff(context, AlarmReceiver.DAILY_REMINDER);
        }
    }

    public void setReleaseReminder(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_REMINDER, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, "Pengingat Rilis dihidupkan", Toast.LENGTH_SHORT).show();
    }


    public void alarmOff(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(RELEASE_REMINDER) ? ID_DAILY_REMINDER : ID_RELEASE_REMINDER;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
//change language to english
        if (type.equalsIgnoreCase(DAILY_REMINDER)) {
            //typo Toast
            //Toast Change language from Indonesian to English
            Toast.makeText(context, "Daily Reminder is turned off", Toast.LENGTH_SHORT).show();
        } else {
            //Typo Toast
            //Toast Change language from Indonesian to English
            Toast.makeText(context, "Release Reminder is turned off", Toast.LENGTH_SHORT).show();
        }
    }
}

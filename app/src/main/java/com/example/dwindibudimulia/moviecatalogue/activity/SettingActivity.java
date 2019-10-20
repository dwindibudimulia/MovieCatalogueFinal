package com.example.dwindibudimulia.moviecatalogue.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dwindibudimulia.moviecatalogue.R;
import com.example.dwindibudimulia.moviecatalogue.notification.AlarmReceiver;

import java.util.Objects;


public class SettingActivity extends AppCompatActivity {
    private AlarmReceiver alarmReceiver;
    private Switch switchDaily, switchRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        switchDaily = findViewById(R.id.switch_daily);
        switchRelease = findViewById(R.id.switch_release);
        Button btnChangeLanguage = findViewById(R.id.btn_change_language);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Setting");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow);

        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        switchDaily.setChecked(sharedPreferences.getBoolean("value", false));
        switchRelease.setChecked(sharedPreferences.getBoolean("value2", false));
        alarmReceiver = new AlarmReceiver();


        switchDaily.setOnCheckedChangeListener((compoundButton, b) -> {
            boolean checked = switchDaily.isChecked();
            if (checked) {
                SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                editor.putBoolean("value", true);
                editor.apply();
                switchDaily.setChecked(true);
            } else {
                SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                editor.putBoolean("value", false);
                editor.apply();
                switchDaily.setChecked(false);
            }
            alarmReceiver.setDailyReminder(this, AlarmReceiver.DAILY_REMINDER, checked);
        });

        switchRelease.setOnCheckedChangeListener((compoundButton, b) -> {
            boolean checked2 = switchRelease.isChecked();
            if (checked2) {
                SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                editor.putBoolean("value2", true);
                editor.apply();
                switchRelease.setChecked(true);
                alarmReceiver.setReleaseReminder(this, AlarmReceiver.RELEASE_REMINDER);
            } else {
                SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                editor.putBoolean("value2", false);
                editor.apply();
                switchRelease.setChecked(false);
                alarmReceiver.alarmOff(this, AlarmReceiver.RELEASE_REMINDER);
            }
        });

        btnChangeLanguage.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}


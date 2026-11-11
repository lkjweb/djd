package com.example.telegramonlylauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TELEGRAM_PACKAGE = "org.telegram.messenger";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(40, 40, 40, 40);
        layout.setBackgroundColor(0xFF171719);

        TextView title = new TextView(this);
        title.setText("Telegram Only");
        title.setTextColor(0xFFFFFFFF);
        title.setTextSize(28);
        title.setGravity(Gravity.CENTER);
        layout.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView note = new TextView(this);
        note.setText("Добровольный режим лаунчера. Другие приложения не блокируются системой — для выхода смените приложение «Главный экран» в настройках.");
        note.setTextColor(0xFFCCCCCC);
        note.setTextSize(14);
        note.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams noteParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        noteParams.setMargins(0, 24, 0, 28);
        layout.addView(note, noteParams);

        Button telegram = new Button(this);
        telegram.setText("Открыть Telegram");
        telegram.setOnClickListener(v -> openTelegram());
        layout.addView(telegram, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        Button settings = new Button(this);
        settings.setText("Выйти / сменить лаунчер");
        settings.setOnClickListener(v -> openHomeSettings());
        LinearLayout.LayoutParams settingsParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        settingsParams.setMargins(0, 16, 0, 0);
        layout.addView(settings, settingsParams);

        setContentView(layout);
    }

    private void openTelegram() {
        PackageManager pm = getPackageManager();
        Intent launch = pm.getLaunchIntentForPackage(TELEGRAM_PACKAGE);
        if (launch == null) {
            Toast.makeText(this, "Telegram не найден. Установите официальный Telegram.", Toast.LENGTH_LONG).show();
            return;
        }
        launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(launch);
    }

    private void openHomeSettings() {
        try {
            startActivity(new Intent(Settings.ACTION_HOME_SETTINGS));
        } catch (Exception e) {
            startActivity(new Intent(Settings.ACTION_SETTINGS));
        }
    }
}

package com.example.laba6_v2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Lab6";
    private TextView tvTimer;
    private TextView textTitle;
    private  TextView resultText;
    private Button btnStartTimer;
    private  Button goStopwatchActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tvTimer);
        textTitle = findViewById(R.id.textTitle);
        resultText = findViewById(R.id.resultatText);
        btnStartTimer = findViewById(R.id.btnStartTimer);
        btnStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDelayedTask();
            }
        });

        goStopwatchActivity = findViewById(R.id.BtnGoStopwatchActivity);
        goStopwatchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StopwatchActivity.class);
                startActivity(intent);
            }
        });

    }

    private void startDelayedTask() {

        textTitle.setText("Расчёт значение X в диапазоне от -30 до 30");
        final int[] count = {-30};
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                count[0]++;
                if (count[0] > -30) {
                    runOnUiThread(() -> {
                        tvTimer.setText(String.format("Значение X=%d", count[0]));
                        try {
                            resultText.setText(String.format("Ответ: %d", CalculateX(count[0], 6)));
                        } catch (ArithmeticException e) {
                            Log.e(TAG, "На 0 делить нельзя: " + e.getMessage());
                        }
                    });
                }
                if (count[0] >= 29) {
                    runOnUiThread(() -> {
                        textTitle.setText("Подсчёт окончен окончен");
                        timer.cancel();
                    });
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    private int CalculateX(int x ,int b) {
        String w = "";
        if (x < 0 && b != 0) {
            w = String.format("X = %d; Math.pow(x, 2) = %f", x, Math.pow(x, 2));
            Log.i(TAG, w);
            w = String.format("X = %d; 5 * Math.pow(x, 2) = %f", x , 5 * Math.pow(x, 2));
            Log.i(TAG, w);
            w = String.format("X = %d; 5 * Math.pow(x, 2) + b = %f; b = %d", x , 5 * Math.pow(x, 2) + b, b);
            Log.i(TAG, w);
            return (int) (5 * Math.pow(x, 2) + b);
        } else if (x > 0 && b == 0) {
            w = String.format("X = %d; (x-5) = %d", x, (x-5));
            Log.i(TAG, w);
            w = String.format("X = %d; (x-2) = %d", x, (x-2));
            Log.i(TAG, w);
            w = String.format("X = %d; (x-5)/(x-2) = %f", x, (x-5)/(x-2));
            Log.i(TAG, w);
            return (int)((x-5)/(x-2));
        } else {

            return (int)(x/2);
        }
    }
}
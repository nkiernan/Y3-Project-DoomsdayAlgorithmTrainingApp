package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import utils.DateGenerator;

public class HardMode extends AppCompatActivity {

    private DateGenerator date;
    private int currentScore = 0;
    private int dateCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_mode);

        updateScreen();
    }

    @SuppressLint("DefaultLocale")
    public void updateScreen() {
        TextView givenDate = findViewById(R.id.dateLabel);
        TextView score = findViewById(R.id.runningScoreLabel);

        if (dateCount < 5) {
            date = new DateGenerator();
            givenDate.setText(date.toString());
            score.setText(String.format("%d/5", currentScore));
        }
        else {
            givenDate.setText(R.string.end_of_round);
            score.setText(String.format("%d/5", currentScore));
        }
    }

    public void processAnswer(int i) {
        dateCount++;
        if (date.getDayOfWeek() == i) {
            currentScore++;
        }
        updateScreen();
    }

    public void mondayOnClick(View v) {
        processAnswer(1);
    }

    public void tuesdayOnClick(View v) {
        processAnswer(2);
    }

    public void wednesdayOnClick(View v) {
        processAnswer(3);
    }

    public void thursdayOnClick(View v) {
        processAnswer(4);
    }

    public void fridayOnClick(View v) {
        processAnswer(5);
    }

    public void saturdayOnClick(View v) {
        processAnswer(6);
    }

    public void sundayOnClick(View v) {
        processAnswer(0);
    }
}
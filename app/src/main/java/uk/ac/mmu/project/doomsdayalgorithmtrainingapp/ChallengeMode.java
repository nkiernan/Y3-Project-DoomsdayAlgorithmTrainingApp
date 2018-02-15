package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import data.Scoreboard;
import io.realm.Realm;
import utils.DateGenerator;

public class ChallengeMode extends AppCompatActivity implements View.OnClickListener {
    private Realm realm;
    private DateGenerator date;
    private String dateFormat;
    private int currentScore = 0;
    private boolean acceptAnswer = true;
    private long startTime = 0L;
    private final Handler timer = new Handler();
    private long currentMs = 0L;
    private long timeSwap = 0L;
    private TextView runningTimeLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_mode);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        runningTimeLabel = findViewById(R.id.runningTimeLabel);

        ImageView mondayButton = findViewById(R.id.mondayButton);
        mondayButton.setOnClickListener(this);
        ImageView tuesdayButton = findViewById(R.id.tuesdayButton);
        tuesdayButton.setOnClickListener(this);
        ImageView wednesdayButton = findViewById(R.id.wednesdayButton);
        wednesdayButton.setOnClickListener(this);
        ImageView thursdayButton = findViewById(R.id.thursdayButton);
        thursdayButton.setOnClickListener(this);
        ImageView fridayButton = findViewById(R.id.fridayButton);
        fridayButton.setOnClickListener(this);
        ImageView saturdayButton = findViewById(R.id.saturdayButton);
        saturdayButton.setOnClickListener(this);
        ImageView sundayButton = findViewById(R.id.sundayButton);
        sundayButton.setOnClickListener(this);

        SharedPreferences chosenFormat = getSharedPreferences("DateFormat", Context.MODE_PRIVATE);
        dateFormat = chosenFormat.getString("DateFormat", "DateFormat");

        startTimer();
        updateScreen(true);
    }

    private void updateScreen(boolean correctAnswer) {
        TextView givenDate = findViewById(R.id.dateLabel);

        if (correctAnswer) {
            date = new DateGenerator("normal");
            givenDate.setText(date.toFormat(dateFormat));
            acceptAnswer = true;
        } else {
            givenDate.setText(R.string.end_of_round);
            acceptAnswer = false;
            ImageView refreshButton = findViewById(R.id.refreshButton);
            refreshButton.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("DefaultLocale")
    public void refreshMode(View v) {
        ImageView refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setVisibility(View.INVISIBLE);

        currentScore = 0;
        startTime = 0L;
        currentMs = 0L;
        timeSwap = 0L;

        TextView score = findViewById(R.id.runningScoreLabel);
        score.setText(String.valueOf(currentScore));
        startTimer();
        updateScreen(true);
    }

    @SuppressLint("DefaultLocale")
    private void processAnswer(int i) {
        int correctDate = date.getDayOfWeek();

        if (correctDate == i) {
            currentScore++;
            TextView score = findViewById(R.id.runningScoreLabel);
            score.setText(String.valueOf(currentScore));
            updateScreen(true);
        } else {
            pauseTimer();
            if (currentScore != 0) {
                TextView time = findViewById(R.id.runningTimeLabel);
                realm.beginTransaction();
                realm.copyToRealm(new Scoreboard("Challenge", currentScore, time.getText().toString()));
                realm.commitTransaction();
            }
            acceptAnswer = false;
            final Snackbar answerMessage;

            answerMessage = Snackbar.make(findViewById(R.id.activity_standard_mode),date.getWeekday(correctDate) + " was the correct answer!", Snackbar.LENGTH_INDEFINITE);
            answerMessage.setAction("Next", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    answerMessage.dismiss();
                    updateScreen(false);
                }
            });
            answerMessage.show();
        }
    }

    @Override
    public void onClick(View v) {
        if (acceptAnswer) {
            switch (v.getId()) {
                case R.id.mondayButton:
                    processAnswer(1);
                    break;
                case R.id.tuesdayButton:
                    processAnswer(2);
                    break;
                case R.id.wednesdayButton:
                    processAnswer(3);
                    break;
                case R.id.thursdayButton:
                    processAnswer(4);
                    break;
                case R.id.fridayButton:
                    processAnswer(5);
                    break;
                case R.id.saturdayButton:
                    processAnswer(6);
                    break;
                case R.id.sundayButton:
                    processAnswer(0);
                    break;
                default:
                    break;
            }
        }
    }

    private void startTimer() {
        startTime = SystemClock.uptimeMillis();
        timer.postDelayed(updateTimer, 0);
    }

    private void pauseTimer() {
        timeSwap += currentMs;
        timer.removeCallbacks(updateTimer);
    }

    private final Runnable updateTimer = new Runnable() {

        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        @Override
        public void run() {
            currentMs = SystemClock.uptimeMillis() - startTime;
            long endTime = timeSwap + currentMs;

            int seconds = (int) (endTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            runningTimeLabel.setText(minutes + ":" + String.format("%02d", seconds));
            timer.postDelayed(this, 0);
        }
    };
}
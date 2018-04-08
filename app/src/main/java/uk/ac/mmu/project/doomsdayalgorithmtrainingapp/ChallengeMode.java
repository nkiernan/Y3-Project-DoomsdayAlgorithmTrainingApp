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

        // Set up Realm Database
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        runningTimeLabel = findViewById(R.id.runningTimeLabel); // Get text label to display time

        // Get weekday buttons and set up click listeners
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

        // Get user's preferred date format for date display
        SharedPreferences chosenFormat = getSharedPreferences("DateFormat", Context.MODE_PRIVATE);
        dateFormat = chosenFormat.getString("DateFormat", "DateFormat");

        startTimer();
        updateScreen(true);
    }

    // Update screen after each answer given
    private void updateScreen(boolean correctAnswer) {
        TextView givenDate = findViewById(R.id.dateLabel);

        if (correctAnswer) {
            // Continues with each correct answer
            date = new DateGenerator("normal"); // Dates generated are not made easier
            givenDate.setText(date.toFormat(dateFormat)); // Display date in user's preferred format
            acceptAnswer = true;
        } else {
            // End round if answer is incorrect
            givenDate.setText(R.string.end_of_round);
            acceptAnswer = false;

            // Display button to give user option of restarting the mode
            ImageView refreshButton = findViewById(R.id.refreshButton);
            refreshButton.setVisibility(View.VISIBLE);
        }
    }

    // When refresh mode button is clicked
    @SuppressLint("DefaultLocale")
    public void refreshMode(View v) {
        // Hide refresh mode button
        ImageView refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setVisibility(View.INVISIBLE);

        // Reset score and timer values
        currentScore = 0;
        startTime = 0L;
        currentMs = 0L;
        timeSwap = 0L;

        // Begin mode again with default values
        TextView score = findViewById(R.id.runningScoreLabel);
        score.setText(String.valueOf(currentScore));
        startTimer();
        updateScreen(true);
    }

    // Deals with user's answer for all seven weekday choices
    @SuppressLint("DefaultLocale")
    private void processAnswer(int i) {
        int correctDate = date.getDayOfWeek(); // Get correct day of week to compare with user's answer

        if (correctDate == i) {
            // Increase and update user score if correct answer given, mode continues
            currentScore++;
            TextView score = findViewById(R.id.runningScoreLabel);
            score.setText(String.valueOf(currentScore));
            updateScreen(true);
        } else {
            pauseTimer(); // Stop timer if incorrect answer given
            if (currentScore != 0) {
                // If user has any correct answers, add score and time of attempt to Realm Database
                TextView time = findViewById(R.id.runningTimeLabel);
                realm.beginTransaction();
                realm.copyToRealm(new Scoreboard("Challenge", currentScore, time.getText().toString()));
                realm.commitTransaction();
            }
            acceptAnswer = false; // Disable answer buttons

            // Setup and display correct answer in message to user
            final Snackbar answerMessage;
            answerMessage = Snackbar.make(findViewById(R.id.activity_standard_mode), date.getWeekday(correctDate) + " was the correct answer!", Snackbar.LENGTH_INDEFINITE);
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

    // Pass value of selected weekday to processAnswer method to verify correctness
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

    // Methods to start and pause timer
    private void startTimer() {
        startTime = SystemClock.uptimeMillis();
        timer.postDelayed(updateTimer, 0);
    }

    private void pauseTimer() {
        timeSwap += currentMs;
        timer.removeCallbacks(updateTimer);
    }

    // Counts each second elapsed and updates timer label to reflect it
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
package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import utils.DateGenerator;

public class TwoPlayerMode extends AppCompatActivity implements View.OnClickListener {

    private DateGenerator date;
    private int p1currentScore = 0;
    private int p2currentScore = 0;
    private int dateCount = 0;
    private boolean acceptAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_mode);

        ImageView p1mondayButton = findViewById(R.id.p1mondayButton);
        p1mondayButton.setOnClickListener(this);
        ImageView p1tuesdayButton = findViewById(R.id.p1tuesdayButton);
        p1tuesdayButton.setOnClickListener(this);
        ImageView p1wednesdayButton = findViewById(R.id.p1wednesdayButton);
        p1wednesdayButton.setOnClickListener(this);
        ImageView p1thursdayButton = findViewById(R.id.p1thursdayButton);
        p1thursdayButton.setOnClickListener(this);
        ImageView p1fridayButton = findViewById(R.id.p1fridayButton);
        p1fridayButton.setOnClickListener(this);
        ImageView p1saturdayButton = findViewById(R.id.p1saturdayButton);
        p1saturdayButton.setOnClickListener(this);
        ImageView p1sundayButton = findViewById(R.id.p1sundayButton);
        p1sundayButton.setOnClickListener(this);

        ImageView p2mondayButton = findViewById(R.id.p2mondayButton);
        p2mondayButton.setOnClickListener(this);
        ImageView p2tuesdayButton = findViewById(R.id.p2tuesdayButton);
        p2tuesdayButton.setOnClickListener(this);
        ImageView p2wednesdayButton = findViewById(R.id.p2wednesdayButton);
        p2wednesdayButton.setOnClickListener(this);
        ImageView p2thursdayButton = findViewById(R.id.p2thursdayButton);
        p2thursdayButton.setOnClickListener(this);
        ImageView p2fridayButton = findViewById(R.id.p2fridayButton);
        p2fridayButton.setOnClickListener(this);
        ImageView p2saturdayButton = findViewById(R.id.p2saturdayButton);
        p2saturdayButton.setOnClickListener(this);
        ImageView p2sundayButton = findViewById(R.id.p2sundayButton);
        p2sundayButton.setOnClickListener(this);

        updateScreen();
    }

    public void updateScreen() {
        TextView p1date = findViewById(R.id.p1dateLabel);
        TextView p2date = findViewById(R.id.p2dateLabel);

        if (dateCount < 5) {
            date = new DateGenerator();
            p1date.setText(date.toString());
            p2date.setText(date.toString());
            acceptAnswer = true;

        } else {
            if (p1currentScore > p2currentScore) {
                p1date.setText(R.string.two_player_win);
                p2date.setText(R.string.two_player_lose);
            } else if (p1currentScore < p2currentScore) {
                p1date.setText(R.string.two_player_lose);
                p2date.setText(R.string.two_player_win);
            } else if (p1currentScore == p2currentScore) {
                p1date.setText(R.string.two_player_draw);
                p2date.setText(R.string.two_player_draw);
            }
            acceptAnswer = false;
        }
    }

    @SuppressLint("DefaultLocale")
    public void processAnswer(int player, int answer) {
        acceptAnswer = false;
        dateCount++;
        int correctDate = date.getDayOfWeek();

        if (correctDate == answer) {
            if (player == 1) {
                p1currentScore++;
                TextView p1score = findViewById(R.id.p1scoreLabel);
                TextView p2opponentScore = findViewById(R.id.p2opponentScoreLabel);
                p1score.setText(String.format("%d/5", p1currentScore));
                p2opponentScore.setText(String.format("%d/5", p1currentScore));
            } else if (player == 2) {
                p2currentScore++;
                TextView p2score = findViewById(R.id.p2scoreLabel);
                TextView p1opponentScore = findViewById(R.id.p1opponentScoreLabel);
                p2score.setText(String.format("%d/5", p2currentScore));
                p1opponentScore.setText(String.format("%d/5", p2currentScore));
            }
            updateScreen();
        } else {
            updateScreen();
        }
    }

    @Override
    public void onClick(View v) {
        if (acceptAnswer) {
            switch (v.getId()) {
                case R.id.p1mondayButton:
                    processAnswer(1,1);
                    break;
                case R.id.p1tuesdayButton:
                    processAnswer(1,2);
                    break;
                case R.id.p1wednesdayButton:
                    processAnswer(1,3);
                    break;
                case R.id.p1thursdayButton:
                    processAnswer(1,4);
                    break;
                case R.id.p1fridayButton:
                    processAnswer(1,5);
                    break;
                case R.id.p1saturdayButton:
                    processAnswer(1,6);
                    break;
                case R.id.p1sundayButton:
                    processAnswer(1,0);
                    break;
                case R.id.p2mondayButton:
                    processAnswer(2,1);
                    break;
                case R.id.p2tuesdayButton:
                    processAnswer(2,2);
                    break;
                case R.id.p2wednesdayButton:
                    processAnswer(2,3);
                    break;
                case R.id.p2thursdayButton:
                    processAnswer(2,4);
                    break;
                case R.id.p2fridayButton:
                    processAnswer(2,5);
                    break;
                case R.id.p2saturdayButton:
                    processAnswer(2,6);
                    break;
                case R.id.p2sundayButton:
                    processAnswer(2,0);
                    break;
                default:
                    break;
            }
        }
    }
}
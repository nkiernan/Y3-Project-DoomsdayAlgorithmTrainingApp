package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreboardsMode extends AppCompatActivity {
    int currentMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboards_mode);
    }

    private void scrollScoreboard(int currentMode) {
        ImageView leftArrow = findViewById(R.id.leftArrow);
        ImageView rightArrow = findViewById(R.id.rightArrow);
        TextView modeLabel = findViewById(R.id.modeLabel);

        switch (currentMode) {
            case 0:
                leftArrow.setVisibility(View.INVISIBLE);
                modeLabel.setText(R.string.easy_mode_item);
                break;
            case 1:
                leftArrow.setVisibility(View.VISIBLE);
                modeLabel.setText(R.string.normal_mode_item);
                break;
            case 2:
                rightArrow.setVisibility(View.VISIBLE);
                modeLabel.setText(R.string.hard_mode_item);
                changeTimeFormat();
                break;
            case 3:
                rightArrow.setVisibility(View.INVISIBLE);
                modeLabel.setText(R.string.challenge_mode_item);
                changeTimeFormat();
                break;
            default:
                break;
        }
    }

    public void scrollLeft(View v) {
        if (currentMode != 0) {
            currentMode--;
            scrollScoreboard(currentMode);
        }
    }

    public void scrollRight(View v) {
        if (currentMode != 3) {
            currentMode++;
            scrollScoreboard(currentMode);
        }
    }

    private void changeTimeFormat() {
        TextView[] timeLabels = new TextView[5];
        timeLabels[0] = findViewById(R.id.time1);
        timeLabels[1] = findViewById(R.id.time2);
        timeLabels[2] = findViewById(R.id.time3);
        timeLabels[3] = findViewById(R.id.time4);
        timeLabels[4] = findViewById(R.id.time5);

        for (TextView timeLabel : timeLabels) {
            if (currentMode == 2) {
                timeLabel.setText(R.string.default_standard_time);
            } else if (currentMode == 3) {
                timeLabel.setText(R.string.default_challenge_time);
            }
        }
    }
}
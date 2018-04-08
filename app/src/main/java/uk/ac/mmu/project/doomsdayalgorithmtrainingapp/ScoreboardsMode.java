package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import data.Scoreboard;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ScoreboardsMode extends AppCompatActivity {
    private int currentMode = 0;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboards_mode);

        // Set up Realm Database and look for existing scores
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        getScores();
    }

    private void scrollScoreboard(int currentMode) {
        // Display default score info before looking for scores in case they don't exist
        presetInfo();
        getScores();

        ImageView leftArrow = findViewById(R.id.leftArrow);
        ImageView rightArrow = findViewById(R.id.rightArrow);
        TextView modeLabel = findViewById(R.id.modeLabel);
        TextView timeColumnLabel = findViewById(R.id.timeColumn);

        // Manipulate screen according to which scoreboard is displayed
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
                modeLabel.setText(R.string.hard_mode_item);
                break;
            case 3:
                rightArrow.setVisibility(View.VISIBLE);
                modeLabel.setText(R.string.challenge_mode_item);
                timeColumnLabel.setText(R.string.time_overall);
                break;
            case 4:
                rightArrow.setVisibility(View.INVISIBLE);
                timeColumnLabel.setText(R.string.time_average);
                break;
            default:
                break;
        }
    }

    // Move to previous scoreboard
    public void scrollLeft(View v) {
        if (currentMode != 0) {
            currentMode--;
            scrollScoreboard(currentMode);
        }
    }

    // Move to next scoreboard
    public void scrollRight(View v) {
        if (currentMode != 4) {
            currentMode++;
            scrollScoreboard(currentMode);
        }
    }

    // Show default values of zero for score and time if no scores are found
    private void presetInfo() {
        TextView[] timeLabels = getTimeLabels();
        TextView[] scoreLabels = getScoreLabels();

        for (TextView timeLabel : timeLabels) {
            if (currentMode != 4) {
                timeLabel.setText(R.string.default_standard_time);
            } else if (currentMode == 4) {
                timeLabel.setText(R.string.default_average_time);
            }
        }

        for (TextView scoreLabel : scoreLabels) {
            scoreLabel.setText(R.string.default_challenge_score);
        }
    }

    // Delete scores for selected mode, warning user first
    public void deleteMode(View v) {
        final TextView modeLabel = findViewById(R.id.modeLabel);
        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Delete all scores for " + modeLabel.getText() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                final RealmResults<Scoreboard> modeScores = realm.where(Scoreboard.class)
                                        .equalTo("mode", findMode())
                                        .findAll();
                                modeScores.deleteAllFromRealm();
                                presetInfo();
                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    // Delete scores for all modes, warning user first
    public void deleteAll(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Delete all scores for every mode?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                final RealmResults<Scoreboard> allScores = realm.where(Scoreboard.class).findAll();
                                allScores.deleteAllFromRealm();
                                presetInfo();
                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    // Find and display scores for selected mode
    private void getScores() {
        TextView[] timeLabels = getTimeLabels();
        TextView[] scoreLabels = getScoreLabels();

        // Find all scores for selected mode
        RealmResults<Scoreboard> scores = realm.where(Scoreboard.class)
                .equalTo("mode", findMode())
                .findAll();

        // Sort scores by highest score first, then by fastest time taken
        scores = scores.sort("score", Sort.DESCENDING, "time", Sort.ASCENDING);

        for (int i = 0; i < scores.size(); i++) {
            if (i < 5) {
                String time = scores.get(i).getTime();
                int score = scores.get(i).getScore();
                if (currentMode != 4) {
                    timeLabels[i].setText(time);
                } else if (currentMode == 4){
                    // Calculate and display average answer time for challenge mode
                    int totalMins = Integer.parseInt(time.split(":")[0]);
                    double totalSeconds = Integer.parseInt(time.split(":")[1]);
                    int averageMins = totalMins / score;
                    double averageSeconds = ((totalMins * 60) + totalSeconds) / score;
                    averageSeconds = averageSeconds - (averageMins * 60);

                    String averageTime = String.valueOf(averageMins);
                    if (averageSeconds < 10) {
                        averageTime += ":0" + String.valueOf(averageSeconds);
                    } else {
                        averageTime += ":" + String.valueOf(averageSeconds);
                    }

                    averageTime = averageTime.substring(0, 6);
                    timeLabels[i].setText(averageTime);
                }
                scoreLabels[i].setText(String.valueOf(score));
            }
        }
    }

    // Get string representation of selected mode
    private String findMode() {
        switch (currentMode) {
            case 0:
                return "Easy";
            case 1:
                return "Normal";
            case 2:
                return "Hard";
            case 3:case 4:
                return "Challenge";
            default:
                return "";
        }
    }

    // Methods to get reference to group of time and score text labels
    private TextView[] getTimeLabels() {
        TextView[] timeLabels = new TextView[5];
        timeLabels[0] = findViewById(R.id.time1);
        timeLabels[1] = findViewById(R.id.time2);
        timeLabels[2] = findViewById(R.id.time3);
        timeLabels[3] = findViewById(R.id.time4);
        timeLabels[4] = findViewById(R.id.time5);
        return timeLabels;
    }

    private TextView[] getScoreLabels() {
        TextView[] scoreLabels = new TextView[5];
        scoreLabels[0] = findViewById(R.id.score1);
        scoreLabels[1] = findViewById(R.id.score2);
        scoreLabels[2] = findViewById(R.id.score3);
        scoreLabels[3] = findViewById(R.id.score4);
        scoreLabels[4] = findViewById(R.id.score5);
        return scoreLabels;
    }
}
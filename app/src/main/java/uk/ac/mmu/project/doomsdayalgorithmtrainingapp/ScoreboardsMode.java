package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        getScores();
    }

    private void scrollScoreboard(int currentMode) {
        presetInfo();
        getScores();
        ImageView leftArrow = findViewById(R.id.leftArrow);
        ImageView rightArrow = findViewById(R.id.rightArrow);
        TextView modeLabel = findViewById(R.id.modeLabel);
        TextView timeColumnLabel = findViewById(R.id.timeColumn);

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

    public void scrollLeft(View v) {
        if (currentMode != 0) {
            currentMode--;
            scrollScoreboard(currentMode);
        }
    }

    public void scrollRight(View v) {
        if (currentMode != 4) {
            currentMode++;
            scrollScoreboard(currentMode);
        }
    }

    private void presetInfo() {
        TextView[] timeLabels = new TextView[5];
        timeLabels[0] = findViewById(R.id.time1);
        timeLabels[1] = findViewById(R.id.time2);
        timeLabels[2] = findViewById(R.id.time3);
        timeLabels[3] = findViewById(R.id.time4);
        timeLabels[4] = findViewById(R.id.time5);

        TextView[] scoreLabels = new TextView[5];
        scoreLabels[0] = findViewById(R.id.score1);
        scoreLabels[1] = findViewById(R.id.score2);
        scoreLabels[2] = findViewById(R.id.score3);
        scoreLabels[3] = findViewById(R.id.score4);
        scoreLabels[4] = findViewById(R.id.score5);

        for (TextView timeLabel : timeLabels) {
            if (currentMode <= 2) {
                timeLabel.setText(R.string.default_standard_time);
            } else if (currentMode >= 3) {
                timeLabel.setText(R.string.default_challenge_time);
            }
        }

        for (TextView scoreLabel : scoreLabels) {
            scoreLabel.setText(R.string.default_challenge_score);
        }
    }

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

    private void getScores() {
        TextView[] timeLabels = new TextView[5];
        timeLabels[0] = findViewById(R.id.time1);
        timeLabels[1] = findViewById(R.id.time2);
        timeLabels[2] = findViewById(R.id.time3);
        timeLabels[3] = findViewById(R.id.time4);
        timeLabels[4] = findViewById(R.id.time5);

        TextView[] scoreLabels = new TextView[5];
        scoreLabels[0] = findViewById(R.id.score1);
        scoreLabels[1] = findViewById(R.id.score2);
        scoreLabels[2] = findViewById(R.id.score3);
        scoreLabels[3] = findViewById(R.id.score4);
        scoreLabels[4] = findViewById(R.id.score5);

        RealmResults<Scoreboard> scores = realm.where(Scoreboard.class)
                .equalTo("mode", findMode())
                .findAll();

        scores = scores.sort("score", Sort.DESCENDING);

        for (int i = 0; i < scores.size(); i++) {
            if (i < 5) {
                String time = scores.get(i).getTime();
                int score = scores.get(i).getScore();
                if (currentMode != 4) {
                    timeLabels[i].setText(time);
                } else if (currentMode == 4){
                    int totalMins = Integer.parseInt(time.split(":")[0]);
                    int totalSeconds = Integer.parseInt(time.split(":")[1]);
                    int totalMillis = Integer.parseInt(time.split(":")[2]);
                    String averageTime;

                    if (totalSeconds / score < 10) {
                        averageTime = String.valueOf(Math.round((totalMins / score))
                                + ":0" + String.valueOf(Math.round(totalSeconds / score))
                                + ":" + String.valueOf(Math.round(totalMillis / score)));
                    } else {
                        averageTime = String.valueOf(Math.round((totalMins / score))
                                + ":" + String.valueOf(Math.round(totalSeconds / score))
                                + ":" + String.valueOf(Math.round(totalMillis / score)));
                    }
                    timeLabels[i].setText(averageTime);
                }
                scoreLabels[i].setText(String.valueOf(score));
            }
        }
    }

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
}
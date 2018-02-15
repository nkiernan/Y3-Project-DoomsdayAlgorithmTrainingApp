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

public class StandardMode extends AppCompatActivity implements View.OnClickListener {

    private Realm realm;
    private String difficulty;
    private DateGenerator date;
    private String dateFormat;
    private int currentScore = 0;
    private int dateCount = 0;
    private boolean acceptAnswer = true;
    private long startTime = 0L;
    private final Handler timer = new Handler();
    private long currentMs = 0L;
    private long timeSwap = 0L;
    private TextView runningTimeLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_mode);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        difficulty = (String) extras.get("modeSelected");
        TextView leapYearLabel = findViewById(R.id.leapYearLabel);
        ImageView showAlgorithmButton = findViewById(R.id.showAlgorithmButton);
        ImageView anchorDisplay = findViewById(R.id.anchorDisplay);
        ImageView doomsdayButton = findViewById(R.id.doomsdayButton);
        runningTimeLabel = findViewById(R.id.runningTimeLabel);

        assert difficulty != null;
        if ("Normal".equals(difficulty)) {
            showAlgorithmButton.setVisibility(View.INVISIBLE);
        } else if ("Hard".equals(difficulty)) {
            leapYearLabel.setVisibility(View.INVISIBLE);
            showAlgorithmButton.setVisibility(View.INVISIBLE);
            anchorDisplay.setVisibility(View.INVISIBLE);
            doomsdayButton.setVisibility(View.INVISIBLE);
        }

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
        updateScreen();
    }

    private void updateScreen() {
        TextView givenDate = findViewById(R.id.dateLabel);
        TextView leapYearLabel = findViewById(R.id.leapYearLabel);

        if (dateCount < 5) {
            if ("Easy".equals(difficulty)) {
                date = new DateGenerator("easy");
            } else {
                date = new DateGenerator("normal");
            }
            givenDate.setText(date.toFormat(dateFormat));
            if (date.getYear() % 4 == 0) {
                leapYearLabel.setText(R.string.leap_year);
            } else if (date.getYear() % 4 != 0) {
                leapYearLabel.setText(R.string.common_year);
            }
            acceptAnswer = true;
            startTimer();
        } else {
            if (currentScore != 0) {
                TextView time = findViewById(R.id.runningTimeLabel);
                realm.beginTransaction();
                realm.copyToRealm(new Scoreboard(difficulty, currentScore, time.getText().toString()));
                realm.commitTransaction();
            }
            givenDate.setText(R.string.end_of_round);
            if (leapYearLabel.getVisibility() == View.VISIBLE) {
                leapYearLabel.setText("");
            }
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
        dateCount = 0;
        startTime = 0L;
        currentMs = 0L;
        timeSwap = 0L;

        TextView score = findViewById(R.id.runningScoreLabel);
        score.setText(String.format("%d/5", currentScore));
        updateScreen();
    }

    private void toggleAlgorithmDisplay(String display) {
        ImageView showAlgorithmButton = findViewById(R.id.showAlgorithmButton);
        ImageView doomsdayAlgorithm = findViewById(R.id.doomsdayAlgorithm);
        ImageView closeButton = findViewById(R.id.closeButton);

        if ("show".equals(display)) {
            showAlgorithmButton.setVisibility(View.INVISIBLE);
            doomsdayAlgorithm.setVisibility(View.VISIBLE);
            closeButton.setVisibility(View.VISIBLE);
        } else if ("hide".equals(display)) {
            showAlgorithmButton.setVisibility(View.VISIBLE);
            doomsdayAlgorithm.setVisibility(View.INVISIBLE);
            closeButton.setVisibility(View.INVISIBLE);
        }
    }

    public void showDoomsdayAlgorithm(View v) {
        if (acceptAnswer) {
            toggleAlgorithmDisplay("show");
        }
    }

    public void hideDoomsdayAlgorithm(View v) {
        if (acceptAnswer) {
            toggleAlgorithmDisplay("hide");
        }
    }

    @SuppressLint("DefaultLocale")
    private void processAnswer(int i) {
        pauseTimer();
        acceptAnswer = false;
        dateCount++;
        final Snackbar answerMessage;
        int correctDate = date.getDayOfWeek();

        if (correctDate == i) {
            currentScore++;
            TextView score = findViewById(R.id.runningScoreLabel);
            score.setText(String.format("%d/5", currentScore));
            answerMessage = Snackbar.make(findViewById(R.id.activity_standard_mode), "You are correct!", Snackbar.LENGTH_INDEFINITE);
            answerMessage.setAction("Next", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    answerMessage.dismiss();
                    updateScreen();
                }
            });
            answerMessage.show();
        } else {
            answerMessage = Snackbar.make(findViewById(R.id.activity_standard_mode), date.getWeekday(correctDate) + " was the correct answer!", Snackbar.LENGTH_INDEFINITE);
            answerMessage.setAction("Next", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    answerMessage.dismiss();
                    updateScreen();
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

    public void showAnchorDay(View v) {
        if (acceptAnswer) {
            String anchorDay = null;
            if (date.getYear() >= 1800 && date.getYear() <= 1899) {
                anchorDay = "Finally, TGIF!";
            } else if (date.getYear() >= 1900 && date.getYear() <= 1999) {
                anchorDay = "Two days til weekend!";
            } else if (date.getYear() >= 2000 && date.getYear() <= 2099) {
                anchorDay = "It's midweek tomorrow...";
            } else if (date.getYear() >= 2100 && date.getYear() <= 2199) {
                anchorDay = "Back to work tomorrow...";
            }
            assert anchorDay != null;
            Snackbar.make(v, "Anchor Hint: " + anchorDay, Snackbar.LENGTH_LONG).show();
        }
    }

    public void showDoomsday(View v) {
        if (acceptAnswer) {
            String doomsdayHint = null;
            if (date.getMonth() == 1) {
                doomsdayHint = "Happy (Belated) New Year!";
            } else if (date.getMonth() == 2) {
                doomsdayHint = "Do you need an extra day?";
            } else if (date.getMonth() == 3) {
                doomsdayHint = "Pi to 2 decimal places";
            } else if (date.getMonth() >= 4 && date.getMonth() % 2 == 0) {
                doomsdayHint = "Don't EVEN ask TWICE";
            } else if (date.getMonth() == 5 || date.getMonth() == 9) {
                doomsdayHint = "What a way to make a living...";
            } else if (date.getMonth() == 7 || date.getMonth() == 11) {
                doomsdayHint = "How convenient!";
            }
            Snackbar.make(v, "Doomsday Hint: " + doomsdayHint, Snackbar.LENGTH_LONG).show();
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
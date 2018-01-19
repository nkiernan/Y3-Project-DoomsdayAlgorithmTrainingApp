package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.annotation.SuppressLint;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import utils.DateGenerator;

public class StandardMode extends AppCompatActivity {

    private DateGenerator date;
    private int currentScore = 0;
    private int dateCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_mode);

        initialiseRound();
        updateScreen();
    }

    @SuppressLint("DefaultLocale")
    public void updateScreen() {
        TextView givenDate = findViewById(R.id.dateLabel);
        TextView score = findViewById(R.id.runningScoreLabel);
        TextView leapYearLabel = findViewById(R.id.leapYearLabel);

        if (dateCount < 5) {
            date = new DateGenerator();
            givenDate.setText(date.toString());
            if (date.getYear() % 4 == 0) {
                leapYearLabel.setText(R.string.leap_year);
            } else if (date.getYear() % 4 != 0) {
                leapYearLabel.setText(R.string.common_year);
            }
            score.setText(String.format("%d/5", currentScore));
        }
        else {
            givenDate.setText(R.string.end_of_round);
            if (leapYearLabel.getVisibility() == View.VISIBLE)
            {
                leapYearLabel.setVisibility(View.INVISIBLE);
            }
            score.setText(String.format("%d/5", currentScore));
        }
    }

    public void initialiseRound() {
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        String difficulty = (String) extras.get("modeSelected");
        TextView leapYearLabel = findViewById(R.id.leapYearLabel);
        ImageView showAlgorithmButton = findViewById(R.id.showAlgorithmButton);
        ImageView anchorDisplay = findViewById(R.id.anchorDisplay);
        ImageView doomsdayButton = findViewById(R.id.doomsdayButton);

        assert difficulty != null;
        switch (difficulty) {
            case "easy":
                leapYearLabel.setVisibility(View.VISIBLE);
                showAlgorithmButton.setVisibility(View.VISIBLE);
                anchorDisplay.setVisibility(View.VISIBLE);
                doomsdayButton.setVisibility(View.VISIBLE);
                break;
            case "normal":
                leapYearLabel.setVisibility(View.VISIBLE);
                showAlgorithmButton.setVisibility(View.INVISIBLE);
                anchorDisplay.setVisibility(View.VISIBLE);
                doomsdayButton.setVisibility(View.VISIBLE);
                break;
            case "hard":
                leapYearLabel.setVisibility(View.INVISIBLE);
                showAlgorithmButton.setVisibility(View.INVISIBLE);
                anchorDisplay.setVisibility(View.INVISIBLE);
                doomsdayButton.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void toggleAlgorithmDisplay(String display) {
        ImageView showAlgorithmButton = findViewById(R.id.showAlgorithmButton);
        ImageView doomsdayAlgorithm = findViewById(R.id.doomsdayAlgorithm);
        ImageView closeButton = findViewById(R.id.closeButton);

        switch (display) {
            case "show":
                showAlgorithmButton.setVisibility(View.INVISIBLE);
                doomsdayAlgorithm.setVisibility(View.VISIBLE);
                closeButton.setVisibility(View.VISIBLE);
                break;
            case "hide":
                showAlgorithmButton.setVisibility(View.VISIBLE);
                doomsdayAlgorithm.setVisibility(View.INVISIBLE);
                closeButton.setVisibility(View.INVISIBLE);
                break;
        }

    }

    public void showDoomsdayAlgorithm(View v) {
        toggleAlgorithmDisplay("show");
    }

    public void hideDoomsdayAlgorithm(View v) {
        toggleAlgorithmDisplay("hide");
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

    public void showAnchorDay(View v) {
        String anchorDay = null;
        if (date.getYear() >= 1800 && date.getYear() <= 1899) {
            anchorDay = "Finally, TGIF!";
        } else if (date.getYear() >= 1900 && date.getYear() <= 1999) {
            anchorDay = "Midweek! 2 more days...";
        } else if (date.getYear() >= 2000 && date.getYear() <= 2099) {
            anchorDay = "It's midweek tomorrow...";
        } else if (date.getYear() >= 2100 && date.getYear() <= 2199) {
            anchorDay = "Back to work tomorrow...";
        }
        assert anchorDay != null;
        Snackbar.make(v, "Anchor Hint: " + anchorDay, Snackbar.LENGTH_LONG).show();
    }

    public void showDoomsday(View v) {
        String doomsdayHint = null;
        switch (date.getMonth()) {
            case 1:
                doomsdayHint = "Happy (Belated) New Year!";
                break;
            case 2:
                doomsdayHint = "Do you need an extra day?";
                break;
            case 3:
                doomsdayHint = "Pi to 2 decimal places";
                break;
            case 4:case 6:case 8:case 10:case 12:
                doomsdayHint = "You don't EVEN have to ask TWICE";
                break;
            case 5:case 9:
                doomsdayHint = "What a way to make a living...";
                break;
            case 7:case 11:
                doomsdayHint = "How convenient!";
                break;
        }
        Snackbar.make(v, "Doomsday Hint: " + doomsdayHint, Snackbar.LENGTH_LONG).show();
    }
}
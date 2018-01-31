package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import utils.DateGenerator;

public class PracticeMode extends AppCompatActivity implements View.OnClickListener {

    private DateGenerator date;
    private String dateFormat;
    private boolean acceptAnswer = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_mode);

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

        updateScreen();
    }

    public void updateScreen() {
        TextView givenDate = findViewById(R.id.dateLabel);
        TextView leapYearLabel = findViewById(R.id.leapYearLabel);

        date = new DateGenerator("easy");
        givenDate.setText(date.toFormat(dateFormat));
        if (date.getYear() % 4 == 0) {
            leapYearLabel.setText(R.string.leap_year);
        } else if (date.getYear() % 4 != 0) {
            leapYearLabel.setText(R.string.common_year);
        }
        acceptAnswer = true;
    }

    public void refreshDate(View v) {
        if (acceptAnswer) {
            updateScreen();
        }
    }

    public void toggleAlgorithmDisplay(String display) {
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
    public void processAnswer(int i) {
        acceptAnswer = false;
        final Snackbar answerMessage;
        int correctDate = date.getDayOfWeek();

        if (correctDate == i) {
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
}
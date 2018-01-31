package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SettingsMode extends AppCompatActivity {
    String dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_mode);
        final RadioGroup localeButtons = findViewById(R.id.localeButtons);
        final RadioGroup formatButtons = findViewById(R.id.formatButtons);
        final TextView dateLabel = findViewById(R.id.dateLabel);

        SharedPreferences chosenFormat = getSharedPreferences("DateFormat", Context.MODE_PRIVATE);
        String currentFormat = chosenFormat.getString("DateFormat", "DateFormat");

        switch (currentFormat) {
                case "uk_alpha":
                    localeButtons.check(R.id.ukButton);
                    formatButtons.check(R.id.alphabeticButton);
                    dateLabel.setText(R.string.uk_aplha_date);
                    break;
                case "us_alpha":
                    localeButtons.check(R.id.usButton);
                    formatButtons.check(R.id.alphabeticButton);
                    dateLabel.setText(R.string.us_alpha_date);
                    break;
                case "uk_num":
                    localeButtons.check(R.id.ukButton);
                    formatButtons.check(R.id.numericButton);
                    dateLabel.setText(R.string.uk_num_date);
                    break;
                case "us_num":
                    localeButtons.check(R.id.usButton);
                    formatButtons.check(R.id.numericButton);
                    dateLabel.setText(R.string.us_num_date);
                    break;
                default:
                    localeButtons.check(R.id.ukButton);
                    formatButtons.check(R.id.numericButton);
                    dateLabel.setText(R.string.uk_num_date);
                    break;
        }

        localeButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (formatButtons.getCheckedRadioButtonId() == R.id.alphabeticButton) {
                    if (localeButtons.getCheckedRadioButtonId() == R.id.ukButton) {
                        dateLabel.setText(R.string.uk_aplha_date);
                        dateFormat = "uk_alpha";
                    } else if (localeButtons.getCheckedRadioButtonId() == R.id.usButton) {
                        dateLabel.setText(R.string.us_alpha_date);
                        dateFormat = "us_alpha";
                    }
                } else if (formatButtons.getCheckedRadioButtonId() == R.id.numericButton) {
                    if (localeButtons.getCheckedRadioButtonId() == R.id.ukButton) {
                        dateLabel.setText(R.string.uk_num_date);
                        dateFormat = "uk_num";
                    } else if (localeButtons.getCheckedRadioButtonId() == R.id.usButton) {
                        dateLabel.setText(R.string.us_num_date);
                        dateFormat = "us_num";
                    }
                }
            }
        });

        formatButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (localeButtons.getCheckedRadioButtonId() == R.id.ukButton) {
                    if (formatButtons.getCheckedRadioButtonId() == R.id.alphabeticButton) {
                        dateLabel.setText(R.string.uk_aplha_date);
                        dateFormat = "uk_alpha";
                    }
                    else if (formatButtons.getCheckedRadioButtonId() == R.id.numericButton) {
                        dateLabel.setText(R.string.uk_num_date);
                        dateFormat = "uk_num";
                    }
                } else if (localeButtons.getCheckedRadioButtonId() == R.id.usButton) {
                    if (formatButtons.getCheckedRadioButtonId() == R.id.alphabeticButton) {
                        dateLabel.setText(R.string.us_alpha_date);
                        dateFormat = "us_alpha";
                    }
                    else if (formatButtons.getCheckedRadioButtonId() == R.id.numericButton) {
                        dateLabel.setText(R.string.us_num_date);
                        dateFormat = "us_num";
                    }
                }
            }
        });
    }

    public void saveDateFormat(View v) {
        SharedPreferences preferences = getSharedPreferences("DateFormat", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("DateFormat", dateFormat);
        editor.apply();
        Snackbar.make(v, "Format Saved", Snackbar.LENGTH_SHORT).show();
    }
}
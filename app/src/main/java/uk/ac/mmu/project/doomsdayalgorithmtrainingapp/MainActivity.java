package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Start selected activity
    private void startMode(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
    }

    // Methods to commence each activity if selected
    public void practiceOnClick(View v) {
        startMode(PracticeSelection.class);
    }

    public void standardOnClick(View v) {
        startMode(StandardSelection.class);
    }

    public void challengeOnClick(View v) {
        startMode(ChallengeMode.class);
    }

    public void twoPlayerOnClick(View v) {
        startMode(TwoPlayerMode.class);
    }

    public void scoreboardsOnClick(View v) {
        startMode(ScoreboardsMode.class);
    }

    public void settingsOnClick(View v) {
        startMode(SettingsMode.class);
    }
}
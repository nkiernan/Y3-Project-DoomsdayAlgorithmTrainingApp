package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PracticeSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_selection);
    }

    private void startMode(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
    }

    public void tutorialOnClick(View v) {
        startMode(TutorialMode.class);
    }

    public void quizOnClick(View v) {
        startMode(QuizMode.class);
    }

    public void practiceOnClick(View v) {
        startMode(PracticeMode.class);
    }
}

package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class StandardSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_selection);
    }

    // Start selected activity
    private void startMode(String difficulty) {
        Intent intent = new Intent(getApplicationContext(), StandardMode.class);
        intent.putExtra("modeSelected", difficulty);
        startActivity(intent);
    }

    // Methods to commence each activity if selected
    public void easyOnClick(View v) {
        startMode("Easy");
    }

    public void normalOnClick(View v) {
        startMode("Normal");
    }

    public void hardOnClick(View v) {
        startMode("Hard");
    }
}
package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StandardSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_selection);
    }

    private void startMode(String difficulty) {
        Intent intent = new Intent(getApplicationContext(), StandardMode.class);
        intent.putExtra("modeSelected", difficulty);
        startActivity(intent);
    }

    public void easyOnClick(View v) {
        startMode("easy");
    }

    public void normalOnClick(View v) {
        startMode("normal");
    }

    public void hardOnClick(View v) {
        startMode("hard");
    }
}
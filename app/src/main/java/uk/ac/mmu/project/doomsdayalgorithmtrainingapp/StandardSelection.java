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

    private void startMode(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
    }

    public void easyOnClick(View v) {

    }

    public void normalOnClick(View v) {

    }

    public void hardOnClick(View v) {

    }
}

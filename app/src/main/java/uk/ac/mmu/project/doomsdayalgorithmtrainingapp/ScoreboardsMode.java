package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Collection;

import data.Scoreboard;
import io.realm.Realm;

public class ScoreboardsMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboards_mode);

        Realm realm;
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        TextView score = findViewById(R.id.score);
        Collection<Scoreboard> scores = realm.where(Scoreboard.class).findAll();
        score.setText(scores.toString());
    }
}
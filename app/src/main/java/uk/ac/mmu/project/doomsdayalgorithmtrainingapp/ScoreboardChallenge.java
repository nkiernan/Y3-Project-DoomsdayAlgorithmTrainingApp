package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScoreboardChallenge extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.scoreboard, container, false);
        TextView modeLabel = rootView.findViewById(R.id.modeLabel);
        modeLabel.setText(R.string.challenge_mode_item);
        return rootView;
    }
}
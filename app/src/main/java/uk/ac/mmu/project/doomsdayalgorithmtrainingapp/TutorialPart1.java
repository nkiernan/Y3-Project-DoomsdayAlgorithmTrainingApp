package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TutorialPart1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Display tutorial information from part 1's layout
        return inflater.inflate(R.layout.tutorial_part1, container, false);
    }
}

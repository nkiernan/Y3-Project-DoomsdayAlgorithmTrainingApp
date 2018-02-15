package uk.ac.mmu.project.doomsdayalgorithmtrainingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizMode extends AppCompatActivity {

    private int currentQuestion = 1;
    private int currentScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_mode);
    }

    public void selectedAnswer(View v) {
        ArrayList<RadioButton> buttons = new ArrayList<>();
        buttons.add((RadioButton) findViewById(R.id.answer1));
        buttons.add((RadioButton) findViewById(R.id.answer2));
        buttons.add((RadioButton) findViewById(R.id.answer3));
        buttons.add((RadioButton) findViewById(R.id.answer4));
        ImageView submitButton = findViewById(R.id.submitButton);

        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).isChecked()) {
                submitButton.setVisibility(View.VISIBLE);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateScreen() {
        TextView questionNumber = findViewById(R.id.questionNumberLabel);
        TextView scoreNumber = findViewById(R.id.runningScoreLabel);
        TextView displayedQuestion = findViewById(R.id.generatedQuestionLabel);
        ImageView submitButton = findViewById(R.id.submitButton);
        submitButton.setVisibility(View.INVISIBLE);

        ArrayList<RadioButton> buttons = new ArrayList<>();
        buttons.add((RadioButton) findViewById(R.id.answer1));
        buttons.add((RadioButton) findViewById(R.id.answer2));
        buttons.add((RadioButton) findViewById(R.id.answer3));
        buttons.add((RadioButton) findViewById(R.id.answer4));

        RadioGroup answers = findViewById(R.id.answers);
        answers.clearCheck();

        questionNumber.setText(String.valueOf(currentQuestion));
        scoreNumber.setText(currentScore + "/5");

        switch (currentQuestion) {
            case 2:
                displayedQuestion.setText(R.string.question_2);
                buttons.get(0).setText(R.string.answer_2a);
                buttons.get(1).setText(R.string.answer_2b);
                buttons.get(2).setText(R.string.answer_2c);
                buttons.get(3).setText(R.string.answer_2d);
                break;
            case 3:
                displayedQuestion.setText(R.string.question_3);
                buttons.get(0).setText(R.string.answer_3a);
                buttons.get(1).setText(R.string.answer_3b);
                buttons.get(2).setText(R.string.answer_3c);
                buttons.get(3).setText(R.string.answer_3d);
                break;
            case 4:
                displayedQuestion.setText(R.string.question_4);
                buttons.get(0).setText(R.string.answer_4a);
                buttons.get(1).setText(R.string.answer_4b);
                buttons.get(2).setText(R.string.answer_4c);
                buttons.get(3).setText(R.string.answer_4d);
                break;
            case 5:
                displayedQuestion.setText(R.string.question_5);
                buttons.get(0).setText(R.string.answer_5a);
                buttons.get(1).setText(R.string.answer_5b);
                buttons.get(2).setText(R.string.answer_5c);
                buttons.get(3).setText(R.string.answer_5d);
                break;
            case 6:
                answers.setVisibility(View.INVISIBLE);
                TextView questionLabel = findViewById(R.id.questionLabel);
                questionLabel.setVisibility(View.INVISIBLE);
                questionNumber.setVisibility(View.INVISIBLE);

                switch (currentScore) {
                    case 0:case 1:case 2:
                        displayedQuestion.setText("You need more practice!");
                        break;
                    case 3:case 4:
                        displayedQuestion.setText("Good, you're getting there!");
                        break;
                    case 5:
                        displayedQuestion.setText("Excellent! Time to put the algorithm into practice!");
                        break;
                        default:
                            break;
                }
                break;
                default:
                    break;
        }
    }

    public void answerButtonOnClick(View v) {
        ArrayList<RadioButton> buttons = new ArrayList<>();
        buttons.add((RadioButton) findViewById(R.id.answer1));
        buttons.add((RadioButton) findViewById(R.id.answer2));
        buttons.add((RadioButton) findViewById(R.id.answer3));
        buttons.add((RadioButton) findViewById(R.id.answer4));

        switch (currentQuestion) {
            case 1:
                if (buttons.get(2).isChecked()) {
                    currentScore++;
                }
                currentQuestion++;
                updateScreen();
                break;
            case 2:
                if (buttons.get(1).isChecked()) {
                    currentScore++;
                }
                currentQuestion++;
                updateScreen();
                break;
            case 3:
                if (buttons.get(0).isChecked()) {
                    currentScore++;
                }
                currentQuestion++;
                updateScreen();
                break;
            case 4:
                if (buttons.get(2).isChecked()) {
                    currentScore++;
                }
                currentQuestion++;
                updateScreen();
                break;
            case 5:
                if (buttons.get(3).isChecked()) {
                    currentScore++;
                }
                currentQuestion++;
                updateScreen();
                break;
                default:
                    break;
        }
    }
}

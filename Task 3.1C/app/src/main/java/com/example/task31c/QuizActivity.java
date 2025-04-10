package com.example.task31c;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private RadioButton option1, option2, option3, option4;
    private Button submitButton;
    private Button nextButton;
    private ProgressBar progressBar;
    private TextView progressTextView;

    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private String username;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get username from intent
        username = getIntent().getStringExtra("USER_NAME");

        // Initialize UI elements
        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);
        progressBar = findViewById(R.id.progressBar);
        progressTextView = findViewById(R.id.progressTextView);

        // Initialize quiz questions
        initializeQuestions();

        // Set progress max value
        progressBar.setMax(quizQuestions.size());
        updateProgressBar();

        // Display first question
        displayQuestion(currentQuestionIndex);

        // Set listeners
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    // Check if an option is selected
                    int selectedId = optionsRadioGroup.getCheckedRadioButtonId();
                    if (selectedId == -1) {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Get selected radio button
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    int selectedIndex = optionsRadioGroup.indexOfChild(selectedRadioButton);

                    // Get current question
                    QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

                    // Check answer
                    if (selectedIndex == currentQuestion.getCorrectAnswerIndex()) {
                        // Correct answer
                        correctAnswers++;
                        selectedRadioButton.setBackgroundColor(Color.GREEN);
                    } else {
                        // Wrong answer
                        selectedRadioButton.setBackgroundColor(Color.RED);
                        // Highlight correct answer
                        RadioButton correctButton = (RadioButton) optionsRadioGroup.getChildAt(currentQuestion.getCorrectAnswerIndex());
                        correctButton.setBackgroundColor(Color.GREEN);
                    }

                    answered = true;
                    submitButton.setEnabled(false);
                    nextButton.setEnabled(true);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex++;
                answered = false;
                submitButton.setEnabled(true);
                nextButton.setEnabled(false);

                // Reset radio button backgrounds
                option1.setBackgroundColor(Color.TRANSPARENT);
                option2.setBackgroundColor(Color.TRANSPARENT);
                option3.setBackgroundColor(Color.TRANSPARENT);
                option4.setBackgroundColor(Color.TRANSPARENT);

                // Clear selection
                optionsRadioGroup.clearCheck();

                if (currentQuestionIndex < quizQuestions.size()) {
                    // Display next question
                    displayQuestion(currentQuestionIndex);
                    updateProgressBar();
                } else {
                    // Quiz finished, show results
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("SCORE", correctAnswers);
                    intent.putExtra("TOTAL", quizQuestions.size());
                    intent.putExtra("USER_NAME", username);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void displayQuestion(int index) {
        QuizQuestion question = quizQuestions.get(index);
        questionTextView.setText(question.getQuestion());
        option1.setText(question.getOptions().get(0));
        option2.setText(question.getOptions().get(1));
        option3.setText(question.getOptions().get(2));
        option4.setText(question.getOptions().get(3));
    }

    private void updateProgressBar() {
        progressBar.setProgress(currentQuestionIndex + 1);
        progressTextView.setText((currentQuestionIndex + 1) + "/" + quizQuestions.size());
    }

    private void initializeQuestions() {
        quizQuestions = new ArrayList<>();

        // Question 1
        List<String> options1 = new ArrayList<>();
        options1.add("To display short messages to the user");
        options1.add("To navigate between activities");
        options1.add("To create UI layouts");
        options1.add("To store application data");
        quizQuestions.add(new QuizQuestion("What is the primary purpose of Toast in Android?", options1, 0));

        // Question 2
        List<String> options2 = new ArrayList<>();
        options2.add("startView()");
        options2.add("startActivity()");
        options2.add("beginIntent()");
        options2.add("launchActivity()");
        quizQuestions.add(new QuizQuestion("Which method is used to start a new activity using Intent?", options2, 1));

        // Question 3
        List<String> options3 = new ArrayList<>();
        options3.add("Explicit Intent");
        options3.add("Implicit Intent");
        options3.add("System Intent");
        options3.add("Action Intent");
        quizQuestions.add(new QuizQuestion("Which type of Intent is used when you don't know the specific component but know the action to perform?", options3, 1));

        // Question 4
        List<String> options4 = new ArrayList<>();
        options4.add("TextView");
        options4.add("Button");
        options4.add("ImageView");
        options4.add("View");
        quizQuestions.add(new QuizQuestion("Which of the following is the parent class for all UI components in Android?", options4, 3));

        // Question 5
        List<String> options5 = new ArrayList<>();
        options5.add("makeText()");
        options5.add("createToast()");
        options5.add("showToast()");
        options5.add("displayText()");
        quizQuestions.add(new QuizQuestion("Which method is used to create a Toast notification?", options5, 0));    }
}
package com.example.task31c;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity {

    private EditText firstNumberEditText;
    private EditText secondNumberEditText;
    private Button addButton;
    private Button subtractButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        // Initialize UI elements
        firstNumberEditText = findViewById(R.id.firstNumberEditText);
        secondNumberEditText = findViewById(R.id.secondNumberEditText);
        addButton = findViewById(R.id.addButton);
        subtractButton = findViewById(R.id.subtractButton);
        resultTextView = findViewById(R.id.resultTextView);

        // Set listeners
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult(true);
            }
        });

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult(false);
            }
        });
    }

    private void calculateResult(boolean isAddition) {
        // Get input values
        String firstNumberStr = firstNumberEditText.getText().toString().trim();
        String secondNumberStr = secondNumberEditText.getText().toString().trim();

        // Validate input
        if (firstNumberStr.isEmpty() || secondNumberStr.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double firstNumber = Double.parseDouble(firstNumberStr);
            double secondNumber = Double.parseDouble(secondNumberStr);
            double result;

            // Perform calculation
            if (isAddition) {
                result = firstNumber + secondNumber;
                if(resultTextView.getVisibility() == View.GONE) {
                    resultTextView.setVisibility(View.VISIBLE);
                }
                resultTextView.setText(firstNumber + " + " + secondNumber + " = " + result);
            } else {
                result = firstNumber - secondNumber;
                if(resultTextView.getVisibility() == View.GONE) {
                    resultTextView.setVisibility(View.VISIBLE);
                }
                resultTextView.setText(firstNumber + " - " + secondNumber + " = " + result);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }
}
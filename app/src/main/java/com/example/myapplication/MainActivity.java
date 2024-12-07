// MainActivity.java
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText ageEditText, weightEditText, heightEditText;
    private RadioGroup genderRadioGroup;
    private Button calculateButton, showRecommendationButton, showSportsButton, calculateBasalMetabolismButton;
    private TextView resultTextView;

    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ageEditText = findViewById(R.id.ageEditText);
        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);
        showRecommendationButton = findViewById(R.id.showRecommendationButton);
        showSportsButton = findViewById(R.id.showSportsButton);
        calculateBasalMetabolismButton = findViewById(R.id.calculateBasalMetabolismButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });

        showRecommendationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecommendations();
            }
        });

        showSportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSportsRecommendations();
            }
        });

        calculateBasalMetabolismButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBasalMetabolismActivity();
            }
        });
    }

    private void calculateBMI() {
        double weight = Double.parseDouble(weightEditText.getText().toString());
        double height = Double.parseDouble(heightEditText.getText().toString());
        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
        gender = selectedGenderRadioButton.getText().toString();
        double bmi = calculateBMIValue(weight, height);
        String category = determineBMICategory(bmi);
        displayResult(bmi, category);
    }

    private double calculateBMIValue(double weight, double height) {
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }

    private String determineBMICategory(double bmi) {
        return getBMICategory(bmi);
    }

    private String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return getString(R.string.category_underweight);
        } else if (bmi < 24.9) {
            return getString(R.string.category_normal);
        } else {
            return getString(R.string.category_obese);
        }
    }

    private void displayResult(double bmi, String category) {
        resultTextView.setText(getString(R.string.result_message, bmi, category));
    }

    private void showRecommendations() {
        double bmi = calculateBMIValue(
                Double.parseDouble(weightEditText.getText().toString()),
                Double.parseDouble(heightEditText.getText().toString()));
        String category = determineBMICategory(bmi);
        String recommendations = getRecommendations(category);

        if (!recommendations.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, DietActivity.class);
            intent.putExtra("recommendations", recommendations);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No recommendations available.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRecommendations(String category) {
        switch (category) {
            case "Underweight":
                return getString(R.string.diet_recommendation_underweight);
            case "Normal":
                return getString(R.string.diet_recommendation_normal);
            case "Obese":
                return getString(R.string.diet_recommendation_obese);
            default:
                return "";
        }
    }

    private void showSportsRecommendations() {
        Intent intent = new Intent(MainActivity.this, SportsActivity.class);
        startActivity(intent);
    }

    private void openBasalMetabolismActivity() {
        Intent intent = new Intent(MainActivity.this, BasalMetabolismActivity.class);
        startActivity(intent);
    }
}

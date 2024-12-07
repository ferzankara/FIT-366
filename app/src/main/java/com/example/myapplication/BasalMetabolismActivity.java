package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class BasalMetabolismActivity extends AppCompatActivity {

    private EditText ageEditText, weightEditText, heightEditText;
    private RadioGroup genderRadioGroup;
    private Button calculateBasalMetabolismButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basal_metabolism);

        ageEditText = findViewById(R.id.ageEditText);
        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        calculateBasalMetabolismButton = findViewById(R.id.calculateBasalMetabolismButton);
        resultTextView = findViewById(R.id.resultTextView);
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
        calculateBasalMetabolismButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBasalMetabolism();
            }
        });
    }
    private void goBack() {

        finish();
    }
    private void calculateBasalMetabolism() {
        // Data validation
        if (ageEditText.getText().toString().isEmpty() || weightEditText.getText().toString().isEmpty()
                || heightEditText.getText().toString().isEmpty() || genderRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageEditText.getText().toString());
        double weight = Double.parseDouble(weightEditText.getText().toString());
        double height = Double.parseDouble(heightEditText.getText().toString());

        // Age validation
        if (age <= 0) {
            Toast.makeText(this, "Invalid age", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            Toast.makeText(this, "Gender not selected", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
        String gender = selectedGenderRadioButton.getText().toString();

        double basalMetabolism = calculateHarrisBenedictBasalMetabolism(age, weight, height, gender);
        double dailyCalories = calculateDailyCalories(basalMetabolism);

        // Display the result in TextView
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String resultText = String.format("Basal Metabolism: %s\nDaily Calorie Intake: %s",
                decimalFormat.format(basalMetabolism), decimalFormat.format(dailyCalories));
        resultTextView.setText(resultText);
    }

    private double calculateHarrisBenedictBasalMetabolism(int age, double weight, double height, String gender) {
        if (gender.equals("Male")) {
            return 66.5 + (13.75 * weight) + (5.003 * height) - (6.755 * age);
        } else {
            return 655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age);
        }
    }

    private double calculateDailyCalories(double basalMetabolism) {
        // This is a simplistic method; you may want to replace it with a more accurate formula
        return basalMetabolism * 1.5; // Assuming a standard activity level
    }
}

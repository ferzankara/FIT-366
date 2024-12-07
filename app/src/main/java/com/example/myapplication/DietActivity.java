package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DietActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);


        TextView recommendationTextView = findViewById(R.id.recommendationTextView);

        String recommendations = getIntent().getStringExtra("recommendations");
        recommendationTextView.setText(recommendations);

    }

    }





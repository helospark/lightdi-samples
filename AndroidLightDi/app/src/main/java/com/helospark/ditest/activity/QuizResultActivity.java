package com.helospark.ditest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import test.helospark.com.ditest.R;

public class QuizResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        TextView finalScoreTextView = (TextView) findViewById(R.id.finalScoreTextView);

        Intent intent = getIntent();
        String correctAnswers = intent.getStringExtra("correctAnswers");

        finalScoreTextView.setText(correctAnswers);

        View viewById = findViewById(R.id.backToHome);
        viewById.setOnClickListener(data -> backToHome());
    }

    private void backToHome() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }
}

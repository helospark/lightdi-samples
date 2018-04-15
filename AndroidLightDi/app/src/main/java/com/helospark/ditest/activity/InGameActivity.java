package com.helospark.ditest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.helospark.ditest.activity.common.Logger;
import com.helospark.ditest.activity.repository.categories.initializer.JsonQuestion;
import com.helospark.ditest.activity.repository.categories.initializer.SelectorQuestion;
import com.helospark.ditest.activity.repository.categories.initializer.SelectorQuestionOption;
import com.helospark.lightdi.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import test.helospark.com.ditest.R;

import static android.graphics.Color.rgb;

public class InGameActivity extends CommonActivityBase {
    private List<JsonQuestion> questions = Collections.emptyList();
    private int answerTimeInSecond = 30; // TODO: Should question contain it?

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private Logger logger;

    // dynamic variables
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private int remainingTime = 0;
    private int currentIndex = 0;
    private Handler handler = new Handler(Looper.getMainLooper());

    private TextView inGameInfoBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
        String categoryId = getIntent().getStringExtra("categoryId");
        CompletableFuture.runAsync(() -> loadQuestionsAndStartGame(categoryId))
                .exceptionally(ex -> {
                    logger.error(this, ex.toString());
                    return null;
                });
        inGameInfoBox = (TextView)super.findViewById(R.id.inGameInfoTextView);
    }

    private void loadQuestionsAndStartGame(String categoryId) {
        List<JsonQuestion> jsonQuestions = questionRepository.loadQuestionsInRandomOrder(categoryId);
        super.runOnUiThread(() -> startQuiz(jsonQuestions));
    }

    private void startQuiz(List<JsonQuestion> q) {
        this.questions = q;
        this.currentIndex = 0;
        this.remainingTime = answerTimeInSecond;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        showQuestion();
    }

    private void showQuestion() {
        if (questions.size() > currentIndex) {
            this.remainingTime = answerTimeInSecond;
            ProgressBar progressBar = (ProgressBar)super.findViewById(R.id.progressBar);
            progressBar.setMax(answerTimeInSecond);
            progressBar.setProgress(answerTimeInSecond - remainingTime);

            inGameInfoBox.setVisibility(View.INVISIBLE);

            LinearLayout linerLayout = (LinearLayout)super.findViewById(R.id.quizInternalLayout);
            linerLayout.removeAllViews();

            JsonQuestion currentQuestion = questions.get(currentIndex);
            updateTime();

            if (currentQuestion instanceof SelectorQuestion) {
                SelectorQuestion selectorQuestion = (SelectorQuestion) currentQuestion;
                TextView textView = new TextView(this);
                textView.setText(selectorQuestion.getText());

                linerLayout.addView(textView);

                for (SelectorQuestionOption option : selectorQuestion.getOptions()) {
                    Button button = new Button(this);
                    button.setText(option.getOptionText());
                    button.setOnClickListener(data -> checkAnswer(currentQuestion, option, button));
                    linerLayout.addView(button);
                }

            } else {
                throw new IllegalStateException("Only selection question type supported"); // TODO!
            }

        } else {
            handler.postDelayed(() -> {
                Intent intent = new Intent(this, QuizResultActivity.class);
                intent.putExtra("correctAnswers", String.valueOf(correctAnswers));
                startActivity(intent);
            }, 1000);

        }
    }

    private void checkAnswer(JsonQuestion currentQuestion, SelectorQuestionOption option, Button button) {
        if (option.isCorrect()) { // TODO: maybe show the correct
            button.setBackgroundColor(rgb(0,255,0));
            inGameInfoBox.setText("Correct");
            ++correctAnswers;
        } else {
            button.setBackgroundColor(rgb(255,0,0));
            ++wrongAnswers;
            inGameInfoBox.setText("Wrong");
        }
        inGameInfoBox.setVisibility(View.VISIBLE);
        handler.postDelayed(() -> newQuestion(), 1000);
    }


    private void timeoutAnswer() {
        ++wrongAnswers;
        inGameInfoBox.setText("Time over");
        inGameInfoBox.setVisibility(View.VISIBLE);
        handler.postDelayed(() -> newQuestion(), 1000);
    }


    private void updateTime() {
        if (remainingTime > 0) {
            --remainingTime;
            ProgressBar progressBar = (ProgressBar)super.findViewById(R.id.progressBar);
            progressBar.setProgress(answerTimeInSecond - remainingTime);

            handler.postDelayed(() -> updateTime(), 1000);
        } else {
            timeoutAnswer();
        }
    }

    private void newQuestion() {
        ++currentIndex;
        showQuestion();
    }


}

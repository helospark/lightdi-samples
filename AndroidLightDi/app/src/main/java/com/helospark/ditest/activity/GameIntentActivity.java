package com.helospark.ditest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.helospark.ditest.activity.repository.categories.QuizCategoryDao;
import com.helospark.ditest.activity.repository.categories.QuizCategoryEntity;
import com.helospark.lightdi.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import test.helospark.com.ditest.R;

public class GameIntentActivity extends CommonActivityBase {
    @Autowired
    private QuizCategoryDao categoryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_intent);

        CompletableFuture.supplyAsync(() -> categoryDao.findAllCategories())
                    .thenAccept(data -> printData(data));
    }

    private void printData(List<QuizCategoryEntity> categories) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.gameCategoryLayout);
        for (QuizCategoryEntity category : categories) {
            Button button = new Button(this);
            button.setText(category.getName() + " (" + category.getLocale() + ")");
            button.setOnClickListener(data -> addClickListener(category.getId()));
            layout.addView(button);
        }
    }

    private void addClickListener(String id) {
        Intent intent = new Intent(this, InGameActivity.class);
        intent.putExtra("categoryId", id);
        startActivity(intent);
    }
}

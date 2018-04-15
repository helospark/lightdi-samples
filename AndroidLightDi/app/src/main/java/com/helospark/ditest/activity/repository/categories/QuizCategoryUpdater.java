package com.helospark.ditest.activity.repository.categories;

import android.support.v7.app.AppCompatActivity;

import com.helospark.ditest.activity.common.Logger;
import com.helospark.ditest.initializer.ApplicationStartupListener;
import com.helospark.lightdi.annotation.Component;

@Component
public class QuizCategoryUpdater implements ApplicationStartupListener {
    private Logger logger;

    public QuizCategoryUpdater(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onApplicationStartup(AppCompatActivity activity) {
        logger.info(this, "Dummy implementation that could check for new questions / categories and initialize DB");
        try {
            Thread.currentThread().sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package com.helospark.ditest.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.helospark.ditest.activity.common.Logger;
import com.helospark.ditest.initializer.ApplicationStartupListener;
import com.helospark.ditest.initializer.FirstApplicationStartupListener;
import com.helospark.lightdi.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import test.helospark.com.ditest.R;

public class MainActivity extends CommonActivityBase {
    private SharedPreferences prefs = null;
    @Autowired
    private List<FirstApplicationStartupListener> firstApplicationStartupListener;
    @Autowired
    private List<ApplicationStartupListener> startupListeners;
    @Autowired
    private Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("com.helospark.lightdiquiz", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        CompletableFuture.runAsync(() -> initializeApplication())
                    .thenAccept(ignored -> openHomeScreen());
    }

    private void openHomeScreen() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    private void initializeApplication() {
        if (prefs.getBoolean("firstrun", true)) {
            logger.info(this, "First startup init");
            for (FirstApplicationStartupListener listener : firstApplicationStartupListener) {
                listener.onFirstApplicationStartup(this);
            }
            prefs.edit().putBoolean("firstrun", false).commit();
        }
        logger.info(this,"Every startup init");
        for (ApplicationStartupListener listener : startupListeners) {
            listener.onApplicationStartup(this);
        }
    }
}

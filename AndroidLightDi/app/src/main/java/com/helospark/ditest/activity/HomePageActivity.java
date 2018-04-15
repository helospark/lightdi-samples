package com.helospark.ditest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import test.helospark.com.ditest.R;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button playButton = (Button)findViewById(R.id.playButton);
        playButton.setOnClickListener(data -> openGameActivity());
    }

    private void openGameActivity() {
        Intent intent = new Intent(this, GameIntentActivity.class);
        startActivity(intent);
    }

}

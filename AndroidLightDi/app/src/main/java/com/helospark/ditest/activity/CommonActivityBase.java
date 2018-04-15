package com.helospark.ditest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.helospark.ditest.DiContextHolder;

public class CommonActivityBase extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DiContextHolder.lazyInitializeContext(this.getApplicationContext());

        DiContextHolder.CONTEXT.processAutowireTo(this);
    }
}

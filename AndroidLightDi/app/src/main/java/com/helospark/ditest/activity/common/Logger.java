package com.helospark.ditest.activity.common;

import android.util.Log;

import com.helospark.lightdi.annotation.Component;

@Component
public class Logger {

    public void info(Object reference, String message) {
        Log.i(reference.getClass().getName(), message);
    }
    public void warn(Object reference, String message) {
        Log.w(reference.getClass().getName(), message);
    }
    public void error(Object reference, String message) {
        Log.e(reference.getClass().getName(), message);
    }
    public void error(Object reference, String message, Throwable t) {
        Log.e(reference.getClass().getName(), message, t);
    }

}

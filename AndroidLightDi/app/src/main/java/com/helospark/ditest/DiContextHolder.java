package com.helospark.ditest;

import android.content.Context;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;

public class DiContextHolder {
    private static Object lock = new Object();
    public static LightDiContext CONTEXT = null;

    public static void lazyInitializeContext(Context context) {
        if (CONTEXT == null) { // double locking for thread safety
            synchronized (lock) {
                if (CONTEXT == null) {
                    LightDiContext lightDiContext = new LightDiContext();
                    lightDiContext.registerSingleton(context, "androidContext");
                    lightDiContext.loadDependenciesFromClass(MainApplicationConfiguration.class);

                    CONTEXT = lightDiContext;
                }
            }
        }
    }

}

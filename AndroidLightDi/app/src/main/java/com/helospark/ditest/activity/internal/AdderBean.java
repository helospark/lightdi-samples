package com.helospark.ditest.activity.internal;

import com.helospark.lightdi.annotation.Component;

@Component
public class AdderBean {

    public int add(int a, int b) {
        return a + b;
    }

}

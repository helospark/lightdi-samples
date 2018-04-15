package com.helospark.ditest.activity.internal;

import com.helospark.lightdi.annotation.Component;

@Component
public class InternalBean {
    private AdderBean adderBean;

    public InternalBean(AdderBean adderBean) {
        this.adderBean = adderBean;
    }

    public int calculate() {
        return adderBean.add(1,2);
    }

}

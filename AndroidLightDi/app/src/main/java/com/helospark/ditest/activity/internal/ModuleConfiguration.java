package com.helospark.ditest.activity.internal;

import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.annotation.Configuration;

@Configuration
@ComponentScan(onlyScanThisJar = false)
public class ModuleConfiguration {
}

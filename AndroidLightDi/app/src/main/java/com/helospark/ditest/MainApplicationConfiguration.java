package com.helospark.ditest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.annotation.Configuration;

/**
 * Configuration for this application using LightDi.
 * Scans all classes under the current subpackage.
 */
@Configuration
@ComponentScan
public class MainApplicationConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

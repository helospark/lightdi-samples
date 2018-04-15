package com.helospark.ditest.activity.repository.categories;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.Configuration;

@Configuration
public class QuizCategoryDatabaseConfiguration {

    @Bean
    public QuizCategoryDao quizCategoryDao(com.helospark.ditest.activity.repository.RoomDatabase database) {
        return database.quizCategoryDao();
    }

}

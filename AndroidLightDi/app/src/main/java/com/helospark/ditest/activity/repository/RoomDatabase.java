package com.helospark.ditest.activity.repository;

import android.arch.persistence.room.Database;

import com.helospark.ditest.activity.repository.categories.QuizCategoryDao;
import com.helospark.ditest.activity.repository.categories.QuizCategoryEntity;

/**
 * This is the Room database definition.
 * It should contain all entities and daos in this application.
 */
@Database(entities = {QuizCategoryEntity.class}, version = 1, exportSchema = false)
public abstract class RoomDatabase extends android.arch.persistence.room.RoomDatabase {
    public abstract QuizCategoryDao quizCategoryDao();
}

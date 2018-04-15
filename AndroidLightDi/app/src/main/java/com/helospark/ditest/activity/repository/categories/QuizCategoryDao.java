package com.helospark.ditest.activity.repository.categories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface QuizCategoryDao {

    @Query("SELECT * from quiz_category")
    public List<QuizCategoryEntity> findAllCategories();

    @Query("SELECT * from quiz_category WHERE locale=:locale")
    public List<QuizCategoryEntity> findAllCategories(String locale);

    @Query("SELECT * from quiz_category WHERE id=:category")
    public QuizCategoryEntity findByCategory(String category);

    @Insert
    void saveCategory(QuizCategoryEntity newEntity);

    @Update
    void updateCategory(QuizCategoryEntity newCategoryEntity);
}

package com.helospark.ditest.activity.repository.categories;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;

import java.util.List;

@Entity(tableName = "quiz_category")
public class QuizCategoryEntity {
    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = "locale")
    private String locale;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "lastPlayTime")
    private Long lastPlayTime;
    @ColumnInfo(name = "files") // TODO: this is a list really, but for now represented as comma separated
    private String questionFiles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(Long lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    public String getQuestionFiles() {
        return questionFiles;
    }

    public void setQuestionFiles(String questionFiles) {
        this.questionFiles = questionFiles;
    }
}

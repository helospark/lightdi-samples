package com.helospark.ditest.activity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helospark.ditest.activity.common.AssetsReader;
import com.helospark.ditest.activity.common.Logger;
import com.helospark.ditest.activity.repository.categories.QuizCategoryDao;
import com.helospark.ditest.activity.repository.categories.QuizCategoryEntity;
import com.helospark.ditest.activity.repository.categories.initializer.JsonQuestion;
import com.helospark.lightdi.annotation.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class QuestionRepository {
    private static final TypeReference<List<JsonQuestion>> TYPE_REFERENCE = new TypeReference<List<JsonQuestion>>() {};

    private QuizCategoryDao categoryDao;
    private AssetsReader assetsReader;
    private ObjectMapper objectMapper;
    private Logger logger;

    public QuestionRepository(QuizCategoryDao categoryDao, AssetsReader assetsReader, ObjectMapper objectMapper, Logger logger) {
        this.categoryDao = categoryDao;
        this.assetsReader = assetsReader;
        this.objectMapper = objectMapper;
        this.logger = logger;
    }

    public List<JsonQuestion> loadQuestionsInRandomOrder(String categoryId) {
        QuizCategoryEntity categoryEntity = categoryDao.findByCategory(categoryId);
        if (categoryEntity == null) {
            throw new IllegalStateException("Unable to load entity");
        }

        List<JsonQuestion> result = new ArrayList<>();
        // TODO: remove duplication
        List<String> fileList = Arrays.asList(categoryEntity.getQuestionFiles().split(","));
        for (String file : fileList) {
            String fileContent = readFile(file);
            try {
                List<JsonQuestion> questionsInFile = objectMapper.readValue(fileContent, TYPE_REFERENCE);
                result.addAll(questionsInFile);
            } catch (IOException e) {
                logger.error(this, "Error loading file, ignoring", e);
            }
        }
        Collections.shuffle(result, new Random());
        return result;
    }

    private String readFile(String file) {
        if (file.startsWith("classpath:")) { // Move to chain
            return assetsReader.readAssetAsString(file.replaceFirst("classpath:", ""));
        } else {
            throw new IllegalStateException("Unknown file protocol");
        }
    }
}

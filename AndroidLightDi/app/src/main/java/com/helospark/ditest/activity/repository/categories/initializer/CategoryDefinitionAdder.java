package com.helospark.ditest.activity.repository.categories.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helospark.ditest.activity.common.AssetsWriter;
import com.helospark.ditest.activity.common.Logger;
import com.helospark.ditest.activity.common.RestClient;
import com.helospark.ditest.activity.repository.categories.QuizCategoryDao;
import com.helospark.ditest.activity.repository.categories.QuizCategoryEntity;
import com.helospark.lightdi.annotation.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryDefinitionAdder {
    private static final String FILE_SEPARATOR = ",";

    private AssetsWriter assetsWriter;
    private ObjectMapper objectMapper;
    private Logger logger;
    private QuizCategoryDao quizCategoryDao;
    private RestClient restClient;

    public CategoryDefinitionAdder(AssetsWriter assetsWriter, ObjectMapper objectMapper, Logger logger, QuizCategoryDao quizCategoryDao, RestClient restClient) {
        this.assetsWriter = assetsWriter;
        this.objectMapper = objectMapper;
        this.logger = logger;
        this.quizCategoryDao = quizCategoryDao;
        this.restClient = restClient;
    }

    public void addCategoryDefinition(String file) {
        try {
            JsonCategory result = objectMapper.readValue(file, JsonCategory.class);
            String category = result.getCategory();
            QuizCategoryEntity foundCategory = quizCategoryDao.findByCategory(category);
            QuizCategoryEntity newCategoryEntity = Optional.ofNullable(foundCategory)
                    .orElse(convertToCategory(result));

            List<String> uris = mergeQuestionUris(result, newCategoryEntity.getQuestionFiles());
            newCategoryEntity.setQuestionFiles(serializeUris(uris));
            if (foundCategory == null) {
                quizCategoryDao.saveCategory(newCategoryEntity);
            } else {
                quizCategoryDao.updateCategory(newCategoryEntity);
            }
        } catch (Exception e) {
            logger.error(this, "Unable to update DB", e);
            throw new RuntimeException(e);
        }
    }

    private List<String> mergeQuestionUris(JsonCategory result, String questionFiles) {
        Set<String> oldUris = new HashSet<>(deserializeUris(questionFiles));
        List<String> newUris = getFilesToLocalStorage(result.getUris());
        oldUris.addAll(newUris);
        return new ArrayList<>(newUris);
    }


    private List<String> getFilesToLocalStorage(List<String> uris) {
        List<String> result = new ArrayList<>();
        for (String uri : uris) {
            if (uri.startsWith("classpath")) {
                result.add(uri);
            } else if (uri.startsWith("http")) {
                String fileContent = restClient.downloadFile(uri);
                String fileName = uri.substring(uri.lastIndexOf("/"));
                assetsWriter.writeAsset(fileName, fileContent);
                result.add("assets:" + fileName);
            }
        }
        return result;
    }

    private QuizCategoryEntity convertToCategory(JsonCategory result) {
        QuizCategoryEntity newEntity = new QuizCategoryEntity();
        newEntity.setId(result.getCategory());
        newEntity.setName(result.getName());
        newEntity.setLocale(result.getLanguage());
        newEntity.setLastPlayTime(null);
        return newEntity;
    }

    private String serializeUris(List<String> uris) {
        return uris.stream()
                .collect(Collectors.joining(FILE_SEPARATOR));
    }

    private List<String> deserializeUris(String questionFiles) {
        return questionFiles == null ? Collections.emptyList() : Arrays.asList(questionFiles.split(FILE_SEPARATOR));
    }
}

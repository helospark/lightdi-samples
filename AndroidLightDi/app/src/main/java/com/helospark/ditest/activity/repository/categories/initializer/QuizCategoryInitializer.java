package com.helospark.ditest.activity.repository.categories.initializer;

import android.support.v7.app.AppCompatActivity;

import com.helospark.ditest.activity.common.AssetsReader;
import com.helospark.ditest.initializer.ApplicationStartupListener;
import com.helospark.lightdi.annotation.Component;

@Component
public class QuizCategoryInitializer implements ApplicationStartupListener {
    private static final String[] INIT_FILES = {
            "lightdi-category.json"
    };
    private CategoryDefinitionAdder categoryDefinitionAdder;
    private AssetsReader assetsReader;

    public QuizCategoryInitializer(CategoryDefinitionAdder categoryDefinitionAdder, AssetsReader assetsReader) {
        this.categoryDefinitionAdder = categoryDefinitionAdder;
        this.assetsReader = assetsReader;
    }

    // TODO: this could also be a firstAppStartListener, but for testing it simpler this way
    @Override
    public void onApplicationStartup(AppCompatActivity activity) {
        for (String file : INIT_FILES) {
            String fileContent = assetsReader.readAssetAsString(file);
            categoryDefinitionAdder.addCategoryDefinition(fileContent);
        }
    }


}

package com.helospark.ditest.activity.repository.categories.initializer;

import java.util.List;

public class SelectorQuestion extends JsonQuestion {
    private List<SelectorQuestionOption> options;

    public List<SelectorQuestionOption> getOptions() {
        return options;
    }

    public void setOptions(List<SelectorQuestionOption> options) {
        this.options = options;
    }
}

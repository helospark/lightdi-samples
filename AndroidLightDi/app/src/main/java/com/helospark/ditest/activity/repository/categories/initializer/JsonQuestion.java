package com.helospark.ditest.activity.repository.categories.initializer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
        @JsonSubTypes({
            @JsonSubTypes.Type(value = SelectorQuestion.class, name = "selector")
        })
public class JsonQuestion {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

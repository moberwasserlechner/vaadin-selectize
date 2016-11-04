package com.byteowls.vaadin.selectize.config.options;

import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionLabel;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSearch;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionValue;

public class BasicOption {

    @SelectizeOptionValue
    private Object value;
    @SelectizeOptionLabel
    @SelectizeOptionSearch
    private String text;

    public BasicOption(Object value, String text) {
        this.value = value;
        this.text = text;
    }

    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "BasicOption [value=" + value + ", text=" + text + "]";
    }

}

package com.byteowls.vaadin.selectize.config.options;

import com.byteowls.vaadin.selectize.config.annotation.SelectizeOption;

@SelectizeOption
public class BasicOption {

    private Object value;
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
    

}

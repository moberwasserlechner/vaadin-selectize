package com.byteowls.vaadin.selectize.config.options;

import com.byteowls.vaadin.selectize.config.annotation.SelectizeOption;

@SelectizeOption
public class BasicGroupOption {

    private Object value;
    private String label;
    private Object optGroupId;
    private String optGroupLabel;

    public BasicGroupOption(Object value, String label) {
        this.value = value;
        this.label = label;
    }

    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

}

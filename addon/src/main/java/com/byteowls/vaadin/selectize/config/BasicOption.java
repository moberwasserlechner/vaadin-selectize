package com.byteowls.vaadin.selectize.config;

public class BasicOption {

    private Object value;
    private String label;

    public BasicOption(Object value, String label) {
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

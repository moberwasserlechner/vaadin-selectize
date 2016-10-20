package com.byteowls.vaadin.selectize;

import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionValue;

public abstract class AbstractIdEntity {

    @SelectizeOptionValue
    private String id;
    
    
    public AbstractIdEntity(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}

package com.byteowls.vaadin.selectize;

import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionLabel;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSearch;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionValue;

/**
 * @author moberwasserlechner
 *
 */
public class Role {
    
    @SelectizeOptionValue
    private Long key;
    
    @SelectizeOptionLabel
    @SelectizeOptionSearch
    private String name;

    public Role(Long key, String name) {
        super();
        this.key = key;
        this.name = name;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Role [key=" + key + ", name=" + name + "]";
    }

}

package com.byteowls.vaadin.selectize.config;

import com.byteowls.vaadin.selectize.utils.JUtils;
import com.byteowls.vaadin.selectize.utils.JsonBuilder;

import elemental.json.Json;
import elemental.json.JsonObject;

public class SelectizeSort implements JsonBuilder {
    
    private String name;
    private boolean asc;
    
    public SelectizeSort(String name, boolean asc) {
        this.name = name;
        this.asc = asc;
    }

    public String getName() {
        return name;
    }

    public boolean isAsc() {
        return asc;
    }

    @Override
    public String toString() {
        return "SelectizeSort [name=" + name + ", asc=" + asc + "]";
    }

    @Override
    public JsonObject buildJson() {
        JsonObject obj = Json.createObject();
        JUtils.putNotNull(obj, "field", name);
        JUtils.putNotNull(obj, "direction ", asc ? "asc" : "desc");
        return obj;
    }
    
}

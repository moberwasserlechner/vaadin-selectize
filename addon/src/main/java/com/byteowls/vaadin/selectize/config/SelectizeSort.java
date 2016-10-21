package com.byteowls.vaadin.selectize.config;

import com.byteowls.vaadin.selectize.utils.JUtils;
import com.byteowls.vaadin.selectize.utils.JsonBuilder;

import elemental.json.Json;
import elemental.json.JsonObject;

public class SelectizeSort implements JsonBuilder, Comparable<SelectizeSort> {
    
    private String name;
    private boolean asc;
    private int order;
    
    public SelectizeSort(String name, boolean asc, int order) {
        this.name = name;
        this.asc = asc;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public boolean isAsc() {
        return asc;
    }

    public int getOrder() {
        return order;
    }
    
    @Override
    public String toString() {
        return "SelectizeSort [name=" + name + ", asc=" + asc + ", order=" + order + "]";
    }

    @Override
    public JsonObject buildJson() {
        JsonObject obj = Json.createObject();
        JUtils.putNotNull(obj, "field", name);
        JUtils.putNotNull(obj, "direction", asc ? "asc" : "desc");
        return obj;
    }

    @Override
    public int compareTo(SelectizeSort o) {
        return Integer.valueOf(getOrder()).compareTo(o.getOrder());
    }

}

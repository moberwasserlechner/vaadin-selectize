package com.byteowls.vaadin.selectize;

import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSort;

public class SortedEntity extends AbstractIdEntity {
    
    @SelectizeOptionSort(order = 6)
    private String a;
    private String b;
    @SelectizeOptionSort(order = 1)
    private String c;
    @SelectizeOptionSort(order = 7)
    private String d;

    public SortedEntity(String id) {
        super(id);
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }
    
}

package com.byteowls.vaadin.selectize.utils;

/**
 * @author michael@byteowls.com
 */
public abstract class And<T> {

    protected T parent;

    public And(T parent) {
        this.parent = parent;
    }

    public T and() {
        return parent;
    }

}

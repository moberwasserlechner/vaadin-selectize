package com.byteowls.vaadin.selectize;

import java.util.List;

import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

public class SelectizeTypedListField<T> extends CustomField<List<T>> {

    private static final long serialVersionUID = -2787759838519415740L;

    private Selectize<T> selectize;
    private Class<T> fieldType;

    public SelectizeTypedListField(Class<T> fieldType) {
        addStyleName("selectize-typed-list");
        this.fieldType = fieldType;
        this.selectize = new Selectize<>();
        this.selectize.setSizeFull();
        this.config();
        this.selectize.addValueChangeListener(new Selectize.ValueChangeListener<T>() {
            private static final long serialVersionUID = 2514784415666543312L;
            @Override
            public void valueChanged(List<T> items) {
                setValue(items);
            }
        });
    }

    public SelectizeTypedListField(Class<T> fieldType, String caption) {
        this(fieldType);
        setCaption(caption);
    }

    public SelectizeConfig<T> config() {
        return this.selectize.config(fieldType).infiniteItems(true);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setPropertyDataSource(Property newDataSource) {
        super.setPropertyDataSource(newDataSource);
        selectize.replaceItems(getValue());
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        selectize.setReadOnly(readOnly);
    }

    @Override
    protected Component initContent() {
        return selectize;
    }

    public void replaceOptions(List<T> options) {
        selectize.replaceOptions(options);
    }

    public void clearOptions() {
        selectize.clearOptions();
    }

    public void replaceItems(List<T> items) {
        selectize.replaceItems(items);
    }

    public void clearItems() {
        selectize.clearItems();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Class<? extends List<T>> getType() {
        return (Class) List.class;
    }

}

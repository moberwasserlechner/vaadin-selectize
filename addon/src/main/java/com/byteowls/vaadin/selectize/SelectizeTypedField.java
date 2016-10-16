package com.byteowls.vaadin.selectize;

import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

public class SelectizeTypedField<T> extends CustomField<T> {

    private static final long serialVersionUID = -2787759838519415740L;

    private Selectize<T> selectize;
    private Class<T> fieldType;

    public SelectizeTypedField(Class<T> fieldType) {
        super();
        this.fieldType = fieldType;
        this.selectize = new Selectize<>();
        this.selectize.setSizeFull();
    }

    public SelectizeTypedField(Class<T> fieldType, String caption) {
        this(fieldType);
        setCaption(caption);
    }

    public SelectizeConfig<T> config() {
        return this.selectize.config();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setPropertyDataSource(Property newDataSource) {
        super.setPropertyDataSource(newDataSource);
        selectize.config().item(getValue());
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

    @Override
    public Class<? extends T> getType() {
        return fieldType;
    }

}

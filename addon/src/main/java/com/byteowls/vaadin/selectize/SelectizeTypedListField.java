package com.byteowls.vaadin.selectize;

import java.util.List;

import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

public class SelectizeTypedListField<T> extends CustomField<List<T>> {

    private static final long serialVersionUID = -2787759838519415740L;

    private Selectize<T> selectize;

    public SelectizeTypedListField() {
        super();
        this.selectize = new Selectize<>();
        this.selectize.setSizeFull();
    }

    public SelectizeTypedListField(String caption) {
        this();
        setCaption(caption);
    }

    public SelectizeConfig<T> config() {
        return this.selectize.config();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setPropertyDataSource(Property newDataSource) {
        super.setPropertyDataSource(newDataSource);
        selectize.config().items(getValue());
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
    public Class<? extends List<T>> getType() {
        return (Class) List.class;
    }


}

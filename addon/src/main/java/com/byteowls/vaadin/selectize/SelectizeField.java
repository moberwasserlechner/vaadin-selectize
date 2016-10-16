package com.byteowls.vaadin.selectize;

import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

public class SelectizeField extends CustomField<Object> {

    private static final long serialVersionUID = -2787759838519415740L;

    private Selectize<Object> selectize;

    public SelectizeField() {
        super();
        this.selectize = new Selectize<>();
        this.selectize.setSizeFull();
    }
    
    public SelectizeField(String caption) {
        this();
        setCaption(caption);
    }

    public SelectizeConfig<Object> config() {
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
    public Class<Object> getType() {
        return Object.class;
    }

}

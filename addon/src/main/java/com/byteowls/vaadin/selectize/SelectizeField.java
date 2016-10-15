package com.byteowls.vaadin.selectize;

import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

public class SelectizeField<T> extends CustomField<T> {
    
    private static final long serialVersionUID = -2787759838519415740L;
    
    private Selectize<T> selectize;
    
    public SelectizeField() {
        super();
    }
    
    public SelectizeField(String caption) {
        setCaption(caption);
    }
    
    public SelectizeConfig<T> config() {
        if (this.selectize == null) {
            this.selectize = new Selectize<>();
        }
        return this.selectize.config();
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    public void setPropertyDataSource(Property newDataSource) {
        super.setPropertyDataSource(newDataSource);
        // TODO
//        selectize.config().items(getValue())
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
        return null;
    }

}

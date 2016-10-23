package com.byteowls.vaadin.selectize;

import java.util.List;

import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

public class SelectizeTypedField<T> extends CustomField<T> {

    private static final long serialVersionUID = -2787759838519415740L;

    private Selectize<T> selectize;
    private Class<T> fieldType;

    public SelectizeTypedField(Class<T> fieldType) {
        addStyleName("selectize-typed");
        this.fieldType = fieldType;
        this.selectize = new Selectize<>();
        this.selectize.setSizeFull();
        this.selectize.addValueChangeListener(new Selectize.ValueChangeListener<T>() {
            private static final long serialVersionUID = 3565961306222180148L;
            @Override
            public void valueChanged(List<T> items) {
                if (items != null && !items.isEmpty()) {
                    setValue(items.get(0));
                } else {
                    setValue(null);
                }
            }
        });
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
        selectize.replaceItem(getValue());
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

    public void replaceItem(T item) {
        selectize.replaceItem(item);
    }

    public void clearItem() {
        selectize.clearItems();
    }

    @Override
    public Class<? extends T> getType() {
        return fieldType;
    }

}

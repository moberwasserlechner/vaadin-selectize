package com.byteowls.vaadin.selectize;

import com.byteowls.vaadin.selectize.config.SelectizeTheme;
import com.vaadin.shared.ui.JavaScriptComponentState;
import elemental.json.JsonValue;

public class SelectizeState extends JavaScriptComponentState {

    private static final long serialVersionUID = -8607752152999308185L;

    public boolean loggingEnabled;
    public boolean multiple;
    public boolean required;
    public SelectizeTheme theme = SelectizeTheme.DEFAULT;
    public JsonValue configurationJson;

}

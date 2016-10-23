package com.byteowls.vaadin.selectize.demo.ui;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;

public enum MenuStructure {
    
    SINGLE(FontAwesome.EDIT), 
    MULTIPLE(FontAwesome.GLOBE), 
    FORM(FontAwesome.BARS);
    
    FontIcon icon;
    
    private MenuStructure(FontIcon icon) {
        this.icon = icon;
    }

    public FontIcon getIcon() {
        return icon;
    }
    
}

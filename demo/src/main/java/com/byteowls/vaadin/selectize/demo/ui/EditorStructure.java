package com.byteowls.vaadin.selectize.demo.ui;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;

public enum EditorStructure {
    
    SINGLE(FontAwesome.EDIT), 
    MULTIPLE(FontAwesome.GLOBE), 
    THEMED(FontAwesome.PAINT_BRUSH);
    
    FontIcon icon;
    
    private EditorStructure(FontIcon icon) {
        this.icon = icon;
    }

    public FontIcon getIcon() {
        return icon;
    }
    
}

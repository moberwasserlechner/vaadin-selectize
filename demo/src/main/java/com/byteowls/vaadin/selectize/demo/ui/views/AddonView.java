package com.byteowls.vaadin.selectize.demo.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;

public interface AddonView extends View {
    
    Component getAddonComponent();
    
    String getSource();
    
    String getViewName();
    

}

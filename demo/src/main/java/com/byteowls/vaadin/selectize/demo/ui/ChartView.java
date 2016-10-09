package com.byteowls.vaadin.selectize.demo.ui;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;

public interface ChartView extends View {
    
    Component getChart();
    
    String getSource();
    
    String getViewName();
    

}

package com.byteowls.vaadin.selectize.demo.ui.views.contacts;

import java.util.Locale;

import com.byteowls.vaadin.selectize.Selectize;
import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.byteowls.vaadin.selectize.config.options.BasicOption;
import com.byteowls.vaadin.selectize.demo.ui.views.AbstractAddonView;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView
public class SingleCountryView extends AbstractAddonView {

    private static final long serialVersionUID = -5516501224827050267L;

    // ### start source
    @Override
    public Component getAddonComponent() {
        VerticalLayout component = new VerticalLayout();
        component.setSizeFull();
        component.setSpacing(true);
        
        Selectize<BasicOption> s1 = new Selectize<>();
        s1.setSizeFull();
        s1.setJsLoggingEnabled(true);
        SelectizeConfig<BasicOption> selectizeConfig = s1.config()
                .persist(false)
                .searchFields("value", "text");
        
        for (Locale l : Locale.getAvailableLocales()) {
            // Build in BasicOption class uses default fieldnames
            selectizeConfig.option(new BasicOption(l.getCountry(), l.getDisplayCountry()));
        }
        component.addComponent(s1);
        
        return component;
    }
    
    // ### end source

}

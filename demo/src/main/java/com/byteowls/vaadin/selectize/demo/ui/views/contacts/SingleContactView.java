package com.byteowls.vaadin.selectize.demo.ui.views.contacts;

import com.byteowls.vaadin.selectize.Selectize;
import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.byteowls.vaadin.selectize.demo.ui.views.AbstractAddonView;
import com.thedeanda.lorem.LoremIpsum;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView
public class SingleContactView extends AbstractAddonView {

    private static final long serialVersionUID = -5516501224827050267L;

    @Override
    public Component getAddonComponent() {
        LoremIpsum loremIpsum = LoremIpsum.getInstance();
        VerticalLayout component = new VerticalLayout();
        component.setSizeFull();
        component.setSpacing(true);
        
        Selectize<Contact> s2 = new Selectize<>();
        s2.setWidth(80, Unit.PERCENTAGE);
        s2.setJsLoggingEnabled(true);
        SelectizeConfig<Contact> s2Config = s2.config()
                .persist(false)
                //.maxItems(1) // default
                .valueField("id")
                .optionLabelGenerator(c -> {
                    return c.getFirstname() + " " + c.getLastname();
                });
        
        for (int i = 0; i < 10; i++) {
            s2Config.option(
                    new Contact(i, loremIpsum.getFirstName(), loremIpsum.getLastName(), loremIpsum.getEmail()));
        }
        component.addComponent(s2);
        
        return component;
    }

}

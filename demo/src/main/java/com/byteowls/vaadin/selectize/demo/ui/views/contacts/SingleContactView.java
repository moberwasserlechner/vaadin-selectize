package com.byteowls.vaadin.selectize.demo.ui.views.contacts;

import com.byteowls.vaadin.selectize.Selectize;
import com.byteowls.vaadin.selectize.demo.ui.views.AbstractAddonView;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;

@UIScope
@SpringView
public class SingleContactView extends AbstractAddonView {

    private static final long serialVersionUID = -5516501224827050267L;

    @Override
    public Component getAddonComponent() {
        Selectize<Contact> component = new Selectize<>();
        component.setJsLoggingEnabled(true);
        component
            .config()
                .persist(false)
                .infiniteItems(true)
                .valueField("id")
                .labelField("label")
                .optionLabelGenerator(c -> {
                    return c.getFirstname() + " " + c.getLastname();
                })
                .option(new Contact(1L, "Michael", "Oberwasserlechner", "michael@gmail.com"))
                .option(new Contact(2L, "Max", "Mustermann", "max@gmail.com"));
        return component;
    }

}

package com.byteowls.vaadin.selectize.demo.ui.views.contacts;

import com.byteowls.vaadin.selectize.Selectize;
import com.byteowls.vaadin.selectize.demo.ui.views.AbstractAddonView;
import com.vaadin.ui.Component;

public class ContactView extends AbstractAddonView {

    private static final long serialVersionUID = -5516501224827050267L;

    @Override
    public Component getAddonComponent() {
        Selectize selectize = new Selectize();
        selectize.setMultiple(true);
        selectize
            .config()
                .persist(false)
                .maxItems(-1)
                .option(new Contact(1L, "Michael", "Oberwasserlechner", "michael@gmail.com"))
                .option(new Contact(2L, "Max", "Mustermann", "max@gmail.com"));
        return selectize;
    }

}

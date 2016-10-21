package com.byteowls.vaadin.selectize.demo.ui.views.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.byteowls.vaadin.selectize.Selectize;
import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.byteowls.vaadin.selectize.config.SelectizeConfig.Plugin;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSearch;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSort;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionValue;
import com.byteowls.vaadin.selectize.demo.ui.views.AbstractAddonView;
import com.thedeanda.lorem.LoremIpsum;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView
public class AnnotatedMultiCompView  extends AbstractAddonView {

    private static final long serialVersionUID = -5516501224827050267L;
    
    // ### start source

    @Override
    public Component getAddonComponent() {
        
        VerticalLayout component = new VerticalLayout();
        component.setWidth(100, Unit.PERCENTAGE);
        component.setSpacing(true);
        for (int i = 0; i < 10; i++) {
            component.addComponent(buildSelectize(i != ThreadLocalRandom.current().nextInt(0, 9)));
        }
        return component;
    }
    
    private Component buildSelectize(boolean infinite) {
        LoremIpsum loremIpsum = LoremIpsum.getInstance();
        Selectize<PersonEntity> s2 = new Selectize<>();
        s2.setSizeFull();
        s2.setJsLoggingEnabled(true);
        SelectizeConfig<PersonEntity> s2Config = s2.config()
                .infiniteItems(infinite)
                .useOnlyConfiguredFields(true).plugins(Plugin.REMOVE_BUTTON)
                .optionLabelGenerator(c -> {
                    return c.getFirstname() + " " + c.getLastname().toUpperCase();
                });
        
        List<PersonEntity> selected = new ArrayList<>();
        int max = ThreadLocalRandom.current().nextInt(1, 20);
        for (int i = 0; i < max; i++) {
            PersonEntity p = new PersonEntity(String.valueOf(i), loremIpsum.getFirstName(), loremIpsum.getLastName(), loremIpsum.getEmail());
            s2Config.option(p);
            if (i == ThreadLocalRandom.current().nextInt(0, max)) {
                selected.add(p);
            }
        }
        
        if (infinite) {
            s2Config.items(selected);
        } else {
            if (!selected.isEmpty()) {
                s2Config.item(selected.get(0));
            }
        }
        return s2;
    }
    
    public abstract class AbstractIdEntity {

        @SelectizeOptionValue
        private String id;
        
        public AbstractIdEntity(String id) {
            super();
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
    
    public class PersonEntity extends AbstractIdEntity {
        
        @SelectizeOptionSearch
        private String firstname;
        @SelectizeOptionSearch
        @SelectizeOptionSort
        private String lastname;
        
        private String email;

        public PersonEntity(String id, String firstname, String lastname, String email) {
            super(id);
            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;
        }
        public String getFirstname() {
            return firstname;
        }
        public String getLastname() {
            return lastname;
        }
        public String getEmail() {
            return email;
        }
        
    }
    
    // ### end source

}

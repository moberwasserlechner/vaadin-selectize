package com.byteowls.vaadin.selectize.demo.ui.views.annotation;

import com.byteowls.vaadin.selectize.Selectize;
import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionLabel;
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
public class AnnotatedClassView  extends AbstractAddonView {

    private static final long serialVersionUID = -5516501224827050267L;
    
    // ### start source

    @Override
    public Component getAddonComponent() {
        LoremIpsum loremIpsum = LoremIpsum.getInstance();
        VerticalLayout component = new VerticalLayout();
        component.setSizeFull();
        component.setSpacing(true);
        
        Selectize<PersonEntity> s2 = new Selectize<>();
        s2.setSizeFull();
        s2.setJsLoggingEnabled(true);
        SelectizeConfig<PersonEntity> s2Config = s2.config()
                .infiniteItems(true).useOnlyConfiguredFields(true)
                .optionLabelGenerator(c -> {
                    return c.getFirstname() + " " + c.getLastname() + " (" + c.getEmail() + ")";
                });
        
        for (int i = 0; i < 10; i++) {
            s2Config.option(new PersonEntity(String.valueOf(i), loremIpsum.getFirstName(), loremIpsum.getLastName(), loremIpsum.getEmail()));
        }
        component.addComponent(s2);
        
        return component;
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
        @SelectizeOptionSort(order = 2)
        private String firstname;
        @SelectizeOptionSort(asc = false, order = 1)
        private String lastname;
        @SelectizeOptionLabel
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

package com.byteowls.vaadin.selectize.demo.ui.views.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.byteowls.vaadin.selectize.Selectize;
import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionLabel;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSearch;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionValue;
import com.byteowls.vaadin.selectize.demo.ui.views.AbstractAddonView;
import com.thedeanda.lorem.LoremIpsum;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView
public class ReadOnlyView  extends AbstractAddonView {

    private static final long serialVersionUID = -5516501224827050267L;

    // ### start source

    @Override
    public Component getAddonComponent() {

        VerticalLayout component = new VerticalLayout();
        component.setWidth(100, Unit.PERCENTAGE);
        component.setSpacing(true);

        HorizontalLayout toolbar = new HorizontalLayout();
        component.addComponent(toolbar);

        Selectize<PersonEntity> selectize = new Selectize<>();
        selectize.setSizeFull();
        selectize.setJsLoggingEnabled(true);
        SelectizeConfig<PersonEntity> config = selectize.config()
                .infiniteItems(true)
                .placeholder("Please choose a person")
                .optionLabelGenerator(c -> {
                    return c.getFirstname() + " " + c.getLastname() + " (" + c.getEmail() + ")";
                });
        config.options(getRandomOptions(ThreadLocalRandom.current().nextInt(10, 100)));

        Button readOnlyToggle = new Button("Make readonly");
        readOnlyToggle.addClickListener(e -> {
            selectize.setReadOnly(!selectize.isReadOnly());
            readOnlyToggle.setCaption(selectize.isReadOnly() ? "Make writable" : "Make readonly");
        });
        toolbar.addComponent(readOnlyToggle);

        component.addComponent(selectize);
        component.setExpandRatio(selectize, 1);
        return component;
    }

    private List<PersonEntity> getRandomOptions(int size) {
        LoremIpsum loremIpsum = LoremIpsum.getInstance();
        List<PersonEntity> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new PersonEntity(String.valueOf(i), loremIpsum.getFirstName(), loremIpsum.getLastName(), loremIpsum.getEmail()));
        }
        return list;
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

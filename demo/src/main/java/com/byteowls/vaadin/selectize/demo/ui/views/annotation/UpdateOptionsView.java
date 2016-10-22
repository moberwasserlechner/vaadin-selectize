package com.byteowls.vaadin.selectize.demo.ui.views.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView
public class UpdateOptionsView  extends AbstractAddonView {

    private static final long serialVersionUID = -5516501224827050267L;

    // ### start source

    @Override
    public Component getAddonComponent() {

        VerticalLayout component = new VerticalLayout();
        component.setWidth(100, Unit.PERCENTAGE);
        component.setSpacing(true);

        HorizontalLayout toolbar = new HorizontalLayout();
        component.addComponent(toolbar);

        Selectize<PersonEntity> s2 = new Selectize<>();
        s2.setSizeFull();
        s2.setJsLoggingEnabled(true);
        SelectizeConfig<PersonEntity> s2Config = s2.config()
                .infiniteItems(true).useOnlyConfiguredFields(true)
                .optionLabelGenerator(c -> {
                    return c.getFirstname() + " " + c.getLastname() + " (" + c.getEmail() + ")";
                });

        s2Config.options(getRandomOptions(4));

        Button newOptions = new Button("Replace options");
        newOptions.addClickListener(e -> {
            s2.replaceOptions(getRandomOptions(ThreadLocalRandom.current().nextInt(2, 100)));
        });
        toolbar.addComponent(newOptions);

        Button clearOptions = new Button("Clear options");
        clearOptions.addClickListener(e -> {
            s2.clearOptions();
        });

        toolbar.addComponent(clearOptions);

        component.addComponent(s2);
        component.setExpandRatio(s2, 1);
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

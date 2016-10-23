package com.byteowls.vaadin.selectize.demo.ui.views.annotation;

import java.util.ArrayList;
import java.util.List;

import com.byteowls.vaadin.selectize.Selectize;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionLabel;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSearch;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSort;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionValue;
import com.byteowls.vaadin.selectize.demo.ui.views.AbstractAddonView;
import com.thedeanda.lorem.LoremIpsum;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView
public class BlurListenerView  extends AbstractAddonView {

    private static final long serialVersionUID = -5516501224827050267L;

    // ### start source

    @Override
    public Component getAddonComponent() {

        VerticalLayout component = new VerticalLayout();
        component.setWidth(100, Unit.PERCENTAGE);
        component.setSpacing(true);

        Selectize<PersonEntity> smulti = new Selectize<>();
        smulti.setSizeFull();
        smulti.setJsLoggingEnabled(true);
        smulti.addBlurListener(list -> {
            Notification.show(list != null ? list.toString() : "Empty!", Type.HUMANIZED_MESSAGE);
        });
        smulti.config().infiniteItems(true).optionLabelGenerator(c -> {
            return c.getFirstname() + " " + c.getLastname() + " (" + c.getEmail() + ")";
        }).options(getRandomOptions(10));

        component.addComponent(smulti);

        Selectize<PersonEntity> ssingle = new Selectize<>();
        ssingle.setSizeFull();
        ssingle.setJsLoggingEnabled(true);
        ssingle.addBlurListener(list -> {
            Notification.show(list != null ? list.toString() : "Empty!", Type.HUMANIZED_MESSAGE);
        });
        ssingle.config().optionLabelGenerator(c -> { return c.getFirstname() + " " + c.getLastname(); }).options(getRandomOptions(10));
        component.addComponent(ssingle);

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
        @Override
        public String toString() {
            return "PersonEntity [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + "]";
        }
    }

    // ### end source

}

package com.byteowls.vaadin.selectize.demo.ui.views.annotation;

import java.util.ArrayList;
import java.util.List;

import com.byteowls.vaadin.selectize.SelectizeDropDownField;
import com.byteowls.vaadin.selectize.SelectizeTokenField;
import com.byteowls.vaadin.selectize.config.SelectizeConfig.Plugin;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionLabel;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSearch;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSort;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionValue;
import com.byteowls.vaadin.selectize.demo.ui.views.AbstractAddonView;
import com.thedeanda.lorem.LoremIpsum;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@SpringView
public class FormLayoutLightView  extends AbstractAddonView {

    private static final long serialVersionUID = -5516501224827050267L;

    // ### start source

    @Override
    public Component getAddonComponent() {
        Panel p = new Panel();
        FormLayout layout = new FormLayout();
        layout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        TextField firstname = new TextField("Firstname");
        firstname.setNullRepresentation("");
        layout.addComponent(firstname);
        TextField lastname = new TextField("Lastname");
        lastname.setNullRepresentation("");
        layout.addComponent(lastname);
        
        layout.addComponent(new TextArea("Notes"));
        SelectizeTokenField<PersonEntity> tokenField = new SelectizeTokenField<>(PersonEntity.class, "Tokens");
        tokenField.config().plugins(Plugin.REMOVE_BUTTON).placeholder("Choose multiple items").optionLabelGenerator(c -> { return c.getFirstname() + " (" + c.getEmail() + ")"; }).options(getRandomOptions(10));
        layout.addComponent(tokenField);

        SelectizeDropDownField<PersonEntity> dropDownField = new SelectizeDropDownField<>(PersonEntity.class, "Dropdown");
        dropDownField.config().placeholder("Choose one item").optionLabelGenerator(c -> { return c.getFirstname() + " " + c.getLastname(); }).options(getRandomOptions(10));
        layout.addComponent(dropDownField);
        
        
        layout.addComponent(new ComboBox("Vaadin Combo"));
        
        p.setContent(layout);
        return p;
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

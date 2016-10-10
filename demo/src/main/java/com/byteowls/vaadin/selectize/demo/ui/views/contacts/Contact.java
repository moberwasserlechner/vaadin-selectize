package com.byteowls.vaadin.selectize.demo.ui.views.contacts;

import com.byteowls.vaadin.selectize.config.annotation.SelectizeOption;

@SelectizeOption(valueField = "id")
public class Contact {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;

    public Contact(Long id, String firstname, String lastname, String email) {
        super();
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLabel() {
        return firstname + " " + lastname;
    }

}

package com.byteowls.vaadin.selectize.demo.ui.views.contacts;

import com.byteowls.vaadin.selectize.config.annotation.SelectizeOption;

@SelectizeOption(valueField = "id")
public class Contact {

    private Integer id;
    private String firstname;
    private String lastname;
    private String email;

    public Contact(Integer id, String firstname, String lastname, String email) {
        super();
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

}

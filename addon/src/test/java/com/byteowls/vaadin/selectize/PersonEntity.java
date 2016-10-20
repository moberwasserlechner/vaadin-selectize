package com.byteowls.vaadin.selectize;

import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSearch;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSort;

public class PersonEntity extends AbstractIdEntity {
    
    @SelectizeOptionSearch
    private String firstname;
    @SelectizeOptionSearch
    private String lastname;
    @SelectizeOptionSort(asc = false)
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

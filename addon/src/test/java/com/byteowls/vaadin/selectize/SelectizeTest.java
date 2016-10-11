package com.byteowls.vaadin.selectize;

import org.junit.Assert;
import org.junit.Test;

import com.byteowls.vaadin.selectize.config.SelectizeConfig;

import elemental.json.JsonArray;

/**
 * @author michael@byteowls.com
 */
public class SelectizeTest {

    @Test
    public void includeAllBeanMembers() {
        Selectize selectize = new Selectize();
        selectize.setMultiple(true);
        SelectizeConfig selectizeConfig = selectize
            .config()
                .persist(false)
                .maxItems(-1)
                    .option(new AddressPlain(1L, "Test", 9000, "Klagenfurt", "AT"));

        JsonArray optionsJson = selectizeConfig.getOptionsJson();
        String json = optionsJson.toJson();
        Assert.assertEquals("[{\"id\":1,\"street\":\"Test\",\"postcode\":9000,\"city\":\"Klagenfurt\",\"country\":\"AT\"}]", json);
   }
    
    @Test
    public void includeOnlyConfiguredFields() {
        Selectize selectize = new Selectize();
        selectize.setMultiple(true);
        SelectizeConfig selectizeConfig = selectize
            .config()
                .persist(false)
                .maxItems(-1)
                    .valueField("id")
                    .labelField("city")
                    .useOnlyConfiguredFields(true)
                    .option(new AddressPlain(42L, "Test", 9000, "Klagenfurt", "AT"));

        JsonArray optionsJson = selectizeConfig.getOptionsJson();
        String json = optionsJson.toJson();
        Assert.assertEquals("[{\"id\":42,\"city\":\"Klagenfurt\"}]", json);
    }
    
    public class AddressPlain {

        private Long id;
        private String street;
        private int postcode;
        private String city;
        private String country;
        
        public AddressPlain(Long id, String street, int postcode, String city, String country) {
            super();
            this.id = id;
            this.street = street;
            this.postcode = postcode;
            this.city = city;
            this.country = country;
        }
        
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }



        public String getStreet() {
            return street;
        }
        public void setStreet(String street) {
            this.street = street;
        }
        public int getPostcode() {
            return postcode;
        }
        public void setPostcode(int postcode) {
            this.postcode = postcode;
        }
        public String getCity() {
            return city;
        }
        public void setCity(String city) {
            this.city = city;
        }
        public String getCountry() {
            return country;
        }
        public void setCountry(String country) {
            this.country = country;
        }

    }

}

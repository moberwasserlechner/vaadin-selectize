package com.byteowls.vaadin.selectize;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.byteowls.vaadin.selectize.utils.LabelGenerator;

import elemental.json.JsonArray;

/**
 * @author michael@byteowls.com
 */
public class MethodFieldTest {

    @Test
    public void includeAllBeanMembers() {
        Selectize<AddressPlain> selectize = new Selectize<>();
        SelectizeConfig<AddressPlain> selectizeConfig = selectize
                .config()
                .persist(false)
                .maxItems(-1)
                .useOnlyConfiguredFields(false)
                .option(new AddressPlain(1L, "Test", 9000, "Klagenfurt", "AT"));

        JsonArray optionsJson = selectizeConfig.getOptionsJson();
        String json = optionsJson.toJson();
        Assert.assertEquals("[{\"id\":1,\"street\":\"Test\",\"postcode\":9000,\"city\":\"Klagenfurt\",\"country\":\"AT\"}]", json);
    }

    @Test
    public void includeOnlyConfiguredFields() {
        Selectize<AddressPlain> selectize = new Selectize<>();
        SelectizeConfig<AddressPlain> selectizeConfig = selectize
                .config()
                .persist(false)
                .maxItems(-1)
                .valueField("id")
                .labelField("city")
                .option(new AddressPlain(42L, "Test", 9000, "Klagenfurt", "AT"));

        JsonArray optionsJson = selectizeConfig.getOptionsJson();
        String json = optionsJson.toJson();
        Assert.assertEquals("[{\"id\":42,\"city\":\"Klagenfurt\"}]", json);
    }

    @Test
    public void useLabelGenerator() {
        Selectize<AddressPlain> selectize = new Selectize<>();
        SelectizeConfig<AddressPlain> selectizeConfig = selectize
                .config()
                .persist(false)
                .maxItems(-1)
                .valueField("id")
                .optionLabelGenerator(new LabelGenerator<AddressPlain>() {
                    private static final long serialVersionUID = -5420372836423660745L;

                    @Override
                    public String getLabel(AddressPlain option) {
                        return option.getPostcode() + " " + option.getCity();
                    }
                })
                .option(new AddressPlain(42L, "Test", 9000, "Klagenfurt", "AT"));

        JsonArray optionsJson = selectizeConfig.getOptionsJson();
        String json = optionsJson.toJson();
        Assert.assertEquals("[{\"id\":42,\""+SelectizeConfig.DEFAULT_LABEL_FIELD+"\":\"9000 Klagenfurt\"}]", json);
    }

    @Test
    public void useLabelGeneratorCustomField() {
        Selectize<AddressPlain> selectize = new Selectize<>();
        SelectizeConfig<AddressPlain> selectizeConfig = selectize
                .config()
                .persist(false)
                .maxItems(-1)
                .valueField("id")
                .generatedLabelField("labelTest")
                .optionLabelGenerator(new LabelGenerator<AddressPlain>() {
                    private static final long serialVersionUID = -5420372836423660745L;

                    @Override
                    public String getLabel(AddressPlain option) {
                        return option.getPostcode() + " " + option.getCity();
                    }
                })
                .option(new AddressPlain(42L, "Test", 9000, "Klagenfurt", "AT"));

        JsonArray optionsJson = selectizeConfig.getOptionsJson();
        String json = optionsJson.toJson();
        Assert.assertEquals("[{\"id\":42,\"labelTest\":\"9000 Klagenfurt\"}]", json);
    }

    @Test
    public void itemJson() {
        Selectize<AddressPlain> selectize = new Selectize<>();

        List<AddressPlain> list = new ArrayList<>();
        AddressPlain addressPlain = new AddressPlain(42L, "Test", 9000, "Klagenfurt", "AT");
        list.add(addressPlain);
        list.add(new AddressPlain(1L, "Hilmgasse 4", 8010, "Graz", "AT"));

        SelectizeConfig<AddressPlain> selectizeConfig = selectize
                .config()
                .persist(false)
                .maxItems(-1)
                .valueField("id")
                .options(list)
                .item(addressPlain);

        JsonArray jsonObj = selectizeConfig.getItemsJson();
        String json = jsonObj.toJson();
        Assert.assertEquals("[42]", json);
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

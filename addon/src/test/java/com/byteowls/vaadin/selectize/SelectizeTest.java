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
    public void testBeanConvertion() {
        Selectize selectize = new Selectize();
        selectize.setMultiple(true);
        SelectizeConfig selectizeConfig = selectize
            .config()
                .persist(false)
                .maxItems(-1)
                    .option(new Address("Test", 9000, "Klagenfurt", "AT"));

        JsonArray optionsJson = selectizeConfig.getOptionsJson();
        String json = optionsJson.toJson();
        Assert.assertEquals("[{\"street\":\"Test\",\"postcode\":9000,\"city\":\"Klagenfurt\",\"country\":\"AT\"}]", json);
    }

}

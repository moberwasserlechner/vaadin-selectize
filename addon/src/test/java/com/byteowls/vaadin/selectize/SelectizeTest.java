package com.byteowls.vaadin.selectize;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import elemental.json.JsonValue;

/**
 * @author michael@byteowls.com
 */
public class SelectizeTest {

    @Test
    public void testConfigGeneral() {
        List<?> options = new ArrayList<>();
        
        Selectize s = new Selectize();
        s.config()
            .create(true)
            .options(options);


        JsonValue jsonValue = s.getState().configurationJson;
        Assert.assertNotNull(jsonValue);
    }

}

package com.byteowls.vaadin.selectize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.byteowls.vaadin.selectize.config.SelectizeSort;

public class AnnotationTest {

    @Test
    public void annotationInClass() {
        Selectize<Role> selectize = new Selectize<>();
        SelectizeConfig<Role> selectizeConfig = selectize
                .config()
                .persist(false)
                .option(new Role(42L, "Test"));
        
        selectizeConfig.resolveAnnotations();
        
        Assert.assertEquals("key", selectizeConfig.getValueField());
        Assert.assertEquals("name", selectizeConfig.getLabelField());
    }
    
    @Test
    public void annotationInSuperClass() {
        Selectize<PersonEntity> selectize = new Selectize<>();
        SelectizeConfig<PersonEntity> selectizeConfig =
                selectize.config(PersonEntity.class);
        
        selectizeConfig.resolveAnnotations();
        
        Assert.assertEquals("id", selectizeConfig.getValueField());
    }
    
    
    @Test
    public void annotatedSearchFields() {
        Selectize<PersonEntity> selectize = new Selectize<>();
        SelectizeConfig<PersonEntity> selectizeConfig = selectize.config(PersonEntity.class);
        selectizeConfig.resolveAnnotations();
        
        Set<String> expectedSearchNames = new HashSet<>();
        expectedSearchNames.add("firstname");
        expectedSearchNames.add("lastname");
        
        Assert.assertEquals(expectedSearchNames, selectizeConfig.getSearchFields());
    }
    
    @Test
    public void annotatedSortFields() {
        Selectize<PersonEntity> selectize = new Selectize<>();
        SelectizeConfig<PersonEntity> selectizeConfig = selectize.config(PersonEntity.class);
        selectizeConfig.resolveAnnotations();
        
        List<SelectizeSort> sortFields = selectizeConfig.getSortFields();
        SelectizeSort emailSort = sortFields.get(0);
        
        Assert.assertEquals("email", emailSort.getName());
        Assert.assertEquals(false, emailSort.isAsc());
    }
    
    @Test
    public void annotatedSortOrderFields() {
        Selectize<SortedEntity> selectize = new Selectize<>();
        SelectizeConfig<SortedEntity> selectizeConfig = selectize.config(SortedEntity.class);
        selectizeConfig.resolveAnnotations();
        
        List<SelectizeSort> sortFields = selectizeConfig.getSortFields();
        String[] expected = new String[] {"c", "a", "d"};
        List<String> actual = new ArrayList<>();
        for(SelectizeSort s : sortFields) {
            actual.add(s.getName());
        }
        Assert.assertArrayEquals(expected, actual.toArray(new String[actual.size()]));
    }
    
    @Test
    public void checkValueClass() {
        Selectize<PersonEntity> selectize = new Selectize<>();
        SelectizeConfig<PersonEntity> selectizeConfig = selectize.config(PersonEntity.class);
        selectizeConfig.resolveAnnotations();
        
        Assert.assertEquals(String.class, selectizeConfig.getValueClass());
    }
    
    @Test
    public void getOptionsByObjectList() {
        Selectize<PersonEntity> selectize = new Selectize<>();
        SelectizeConfig<PersonEntity> selectizeConfig = selectize.config(PersonEntity.class);
        selectizeConfig.resolveAnnotations();
        
        Assert.assertEquals(String.class, selectizeConfig.getValueClass());
        
        List<PersonEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new PersonEntity(String.valueOf(i), "Michael"+i, "Oberwasserlechner" + 1, "test@test.com"));
        }
        selectizeConfig.options(list);
        
        List<Object> selected = new ArrayList<>();
        selected.add("1");
        selected.add("3");
        List<PersonEntity> optionsByValues = selectizeConfig.getOptionsByValues(selected);
        // TODO better check
        Assert.assertNotNull(optionsByValues);
    }
    
    
}

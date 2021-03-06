package com.byteowls.vaadin.selectize.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionLabel;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSearch;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSort;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionValue;
import com.byteowls.vaadin.selectize.utils.JUtils;
import com.byteowls.vaadin.selectize.utils.JsonBuilder;
import com.byteowls.vaadin.selectize.utils.LabelGenerator;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;

public class SelectizeConfig<T> implements JsonBuilder {

    public static final String DEFAULT_LABEL_FIELD = "text";
    public static final String DEFAULT_VALUE_FIELD = "value";
    public static final String DEFAULT_OPTGROUP_LABEL_FIELD = "label";
    public static final String DEFAULT_OPTGROUP_FIELD = "optgroup";

    public enum SearchConjunction {
        AND, OR;
    }

    public enum Plugin {
        REMOVE_BUTTON, DROPDOWN_HEADER, OPTGROUP_COLUMNS, RESTORE_ON_BACKSPACE; // TODO #8 DRAG_DROP
    }

    private Class<T> optionsClass;
    private Class<?> valueClass;
    private List<T> options;
    private LabelGenerator<T> optionLabelGenerator;
    private String generatedLabelField;
    private List<T> items;
    private boolean useOnlyConfiguredFields = true;

    // general
    private String delimiter;
    private Boolean createOnBlur;
    private String createFilter;
    private Boolean highlight;
    private Boolean persist;
    private Boolean openOnFocus;
    private Integer maxOptions;
    private Integer maxItems;
    private boolean infiniteItems;
    private Boolean hideSelected;
    private Boolean closeAfterSelect;
    private Boolean allowEmptyOption;
    private Integer scrollDuration;
    private Integer loadThrottle;
    private String loadingClass;
    private String placeholder;
    private Boolean preload;
    private Boolean preloadOnFocus;
    private String dropdownParent;
    private Boolean addPrecedence;
    private Boolean selectOnTab;
    private Boolean diacritics;
    // data filter / search
    private List<T> optgroups;
    private LabelGenerator<T> optgroupLabelGenerator;
    private String dataAttr;

    private String valueField;
    private String optgroupValueField;
    private String labelField;
    private String optgroupLabelField;
    private String optgroupField;
    private List<SelectizeSort> sortFields;
    private Set<String> searchFields;

    private SearchConjunction searchConjunction;
    private Boolean lockOptgroupOrder;
    private Boolean copyClassesToDropdown;
    private Set<String> plugins;

    /**
     * Set the class of the option beans. Mainly for annotation processing if not items are loaded yet.
     * @param optionsClass the class of the option beans.
     * @return This for chaining.
     */
    public SelectizeConfig<T> optionsClass(Class<T> optionsClass) {
        this.optionsClass = optionsClass;
        return this;
    }

    public SelectizeConfig<T> valueClass(Class<?> valueClass) {
        this.valueClass = valueClass;
        return this;
    }

    /**
     * An array of the initial options beans available.
     * @param optionBeans a list of option beans
     * @return This for chaining.
     */
    public SelectizeConfig<T> options(List<T> optionBeans) {
        this.options = optionBeans;
        return this;
    }

    /**
     * Add a option bean to the list of initial available options.
     * @param optionBean
     * @return This for chaining.
     */
    public SelectizeConfig<T> option(T optionBean) {
        if (optionBean != null) {
            if (this.options == null) {
                this.options = new ArrayList<>();
            }
            if (!this.options.contains(optionBean)) {
                this.options.add(optionBean);
            }
        }
        return this;
    }

    /**
     * 
     * @param optionLabelGenerator
     * @return This for chaining.
     */
    public SelectizeConfig<T> optionLabelGenerator(LabelGenerator<T> optionLabelGenerator) {
        this.optionLabelGenerator = optionLabelGenerator;
        return this;
    }

    public SelectizeConfig<T> generatedLabelField(String generatedLabelField) {
        this.generatedLabelField = generatedLabelField;
        return this;
    }

    /**
     * If true only bean members defined in the field methods are provided to the client side. Defaults to true.
     * @return This for chaining.
     */
    public SelectizeConfig<T> useOnlyConfiguredFields(boolean useOnlyConfiguredFields) {
        this.useOnlyConfiguredFields = useOnlyConfiguredFields;
        return this;
    }

    /**
     * An array of the initial selected values.
     * @param items
     * @return This for chaining.
     */
    public SelectizeConfig<T> items(List<T> items) {
        this.items = items;
        return this;
    }

    /**
     * Add a item bean to the list of selected options.
     * @param itemBean
     * @return This for chaining.
     */
    public SelectizeConfig<T> item(T itemBean) {
        if (itemBean != null) {
            if (this.items == null) {
                this.items = new ArrayList<>();
            }
            this.items.add(itemBean);
        }
        return this;
    }


    /**
     * The string to separate items by. When typing an item in a multi-selection control allowing creation, 
     * then the delimiter, the item is added. If you paste delimiter-separated items in such control, the items are added at once. 
     * 
     * The delimiter is also used in the getValue API call on a text <input> tag to separate the multiple values.
     * 
     * Defaults to semicolon.
     * @param delimiter
     * @return This for chaining.
     */
    public SelectizeConfig<T> delimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    /**
     * If true, when user exits the field (clicks outside of input), a new option is created and selected (if create setting is enabled). Defaults to false.
     * @param createOnBlur
     * @return This for chaining.
     */
    public SelectizeConfig<T> createOnBlur(boolean createOnBlur) {
        this.createOnBlur = createOnBlur;
        return this;
    }

    /**
     * Specifies a RegExp or a string containing a regular expression that the current search filter must match to be allowed to be created.
     * @param createFilter
     * @return This for chaining.
     */
    public SelectizeConfig<T> createFilter(String createFilter) {
        this.createFilter = createFilter;
        return this;
    }

    /**
     * Toggles match highlighting within the dropdown menu. Defaults to true.
     * @param highlight
     * @return This for chaining.
     */
    public SelectizeConfig<T> highlight(boolean highlight) {
        this.highlight = highlight;
        return this;
    }

    /**
     * If false, items created by the user will not show up as available options once they are unselected. Defaults to true.
     * @param persist
     * @return This for chaining.
     */
    public SelectizeConfig<T> persist(boolean persist) {
        this.persist = persist;
        return this;
    }

    /**
     * Show the dropdown immediately when the control receives focus. Defaults to true.
     * @param openOnFocus
     * @return This for chaining.
     */
    public SelectizeConfig<T> openOnFocus(boolean openOnFocus) {
        this.openOnFocus = openOnFocus;
        return this;
    }

    /**
     * The max number of items to render at once in the dropdown list of options. Defaults to 1000.
     * @param maxOptions
     * @return This for chaining.
     */
    public SelectizeConfig<T> maxOptions(int maxOptions) {
        this.maxOptions = maxOptions;
        return this;
    }

    /**
     * The max number of items the user can select. 1 makes the control mono-selection, null allows an unlimited number of items. Defaults to 1.
     * @param maxItems
     * @return This for chaining.
     */
    public SelectizeConfig<T> maxItems(Integer maxItems) {
        this.infiniteItems = (maxItems == null);
        if (!infiniteItems && maxItems > 0) {
            this.maxItems = maxItems;
        }
        return this;
    }

    /**
     * If true the maxItems parameters is set to infinite.
     * @param infiniteItems
     * @return This for chaining.
     */
    public SelectizeConfig<T> infiniteItems(boolean infiniteItems) {
        this.infiniteItems = infiniteItems;
        return this;
    }

    /**
     * If true, the items that are currently selected will not be shown in the dropdown list of available options. Defaults to false.
     * @param hideSelected
     * @return This for chaining.
     */
    public SelectizeConfig<T> hideSelected(boolean hideSelected) {
        this.hideSelected = hideSelected;
        return this;
    }

    /**
     * If true, the dropdown will be closed after a selection is made. Defaults to false.
     * @param closeAfterSelect
     * @return This for chaining.
     */
    public SelectizeConfig<T> closeAfterSelect(boolean closeAfterSelect) {
        this.closeAfterSelect = closeAfterSelect;
        return this;
    }

    /**
     * If true, Selectize will treat any options with a "" value like normal. 
     * 
     * This defaults to false to accomodate the common <select> practice of having the first empty option to act as a placeholder. 
     * @param allowEmptyOption
     * @return This for chaining.
     */
    public SelectizeConfig<T> allowEmptyOption(boolean allowEmptyOption) {
        this.allowEmptyOption = allowEmptyOption;
        return this;
    }

    /**
     * The animation duration (in milliseconds) of the scroll animation triggered when going [up] and [down] in the options dropdown. Defaults to 60.
     * @param scrollDuration
     * @return This for chaining.
     */
    public SelectizeConfig<T> scrollDuration(int scrollDuration) {
        this.scrollDuration = scrollDuration;
        return this;
    }

    /**
     * The number of milliseconds to wait before requesting options from the server or null. If null, throttling is disabled. 
     * 
     * Useful when loading options dynamically while the user types a search / filter expression. Defaults to 300.
     * @param loadThrottle
     * @return This for chaining.
     */
    public SelectizeConfig<T> loadThrottle(int loadThrottle) {
        this.loadThrottle = loadThrottle;
        return this;
    }

    /**
     * The class name added to the wrapper element while awaiting the fulfillment of load requests. Defaults to 'loading'
     * @param loadingClass
     * @return This for chaining.
     */
    public SelectizeConfig<T> loadingClass(String loadingClass) {
        this.loadingClass = loadingClass;
        return this;
    }

    /**
     * The placeholder of the control (displayed when nothing is selected / typed). Defaults to input element's placeholder, unless this one is specified.
     * @param placeholder
     * @return This for chaining.
     */
    public SelectizeConfig<T> placeholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    /**
     * If true, the load function will be called upon control initialization (with an empty search). 
     * Alternatively it can be set to 'focus' to call the load function when control receives focus. Defaults to false.
     * @param preload
     * @return This for chaining.
     */
    public SelectizeConfig<T> preload(boolean preload) {
        this.preload = preload;
        return this;
    }

    /**
     * If true, the load function will be called when control receives focus.
     * @param preloadOnFocus
     * @return This for chaining.
     */
    public SelectizeConfig<T> preloadOnFocus(boolean preloadOnFocus) {
        this.preloadOnFocus = preloadOnFocus;
        return this;
    }

    /**
     * The element the dropdown menu is appended to. This should be 'body' or null. If null, the dropdown will be appended as a child of the Selectize control. 
     * @param dropdownParent
     * @return This for chaining.
     */
    public SelectizeConfig<T> dropdownParent(String dropdownParent) {
        this.dropdownParent = dropdownParent;
        return this;
    }

    /**
     * If true, the "Add..." option is the default selection in the dropdown. Defaults to false.
     * @param addPrecedence
     * @return This for chaining.
     */
    public SelectizeConfig<T> addPrecedence(boolean addPrecedence) {
        this.addPrecedence = addPrecedence;
        return this;
    }

    /**
     * If true, the tab key will choose the currently selected item. Defaults to false.
     * @param selectOnTab
     * @return This for chaining.
     */
    public SelectizeConfig<T> selectOnTab(boolean selectOnTab) {
        this.selectOnTab = selectOnTab;
        return this;
    }

    /**
     * Enable or disable international character support. Defaults to true.
     * @param diacritics
     * @return This for chaining.
     */
    public SelectizeConfig<T> diacritics(boolean diacritics) {
        this.diacritics = diacritics;
        return this;
    }

    // TODO add optgroup

    /**
     * 
     * @return This for chaining.
     */
    public SelectizeConfig<T> optgroupLabelGenerator(LabelGenerator<T> optgroupLabelGenerator) {
        this.optgroupLabelGenerator = optgroupLabelGenerator;
        return this;
    }


    /**
     * The <option> attribute from which to read JSON data about the option. Defaults to 'data-data' 
     * @param dataAttr
     * @return This for chaining.
     */
    public SelectizeConfig<T> dataAttr(String dataAttr) {
        this.dataAttr = dataAttr;
        return this;
    }

    /**
     * The name of the property to use as the value when an item is selected. Defaults to 'value'. 
     * @param valueField
     * @return This for chaining.
     */
    public SelectizeConfig<T> valueField(String valueField) {
        this.valueField = valueField;
        return this;
    }

    /**
     * @return The value field name. If null the library uses 'value' as default value.
     */
    public String getValueField() {
        return this.valueField;
    }

    /**
     * The name of the option group property that serves as its unique identifier. Defaults to 'value'.
     * @param optgroupValueField
     * @return This for chaining.
     */
    public SelectizeConfig<T> optgroupValueField(String optgroupValueField) {
        this.optgroupValueField = optgroupValueField;
        return this;
    }



    /**
     * The name of the property to render as an option / item label (not needed when custom rendering functions are defined). Defaults to 'text'. 
     * @param labelField
     * @return This for chaining.
     */
    public SelectizeConfig<T> labelField(String labelField) {
        this.labelField = labelField;
        return this;
    }

    /**
     * @return The label field name. If null the library uses 'text' as default value.
     */
    public String getLabelField() {
        return this.labelField;
    }

    /**
     * The name of the property to render as an option group label (not needed when custom rendering functions are defined). Defaults to 'label'. 
     * @param optgroupLabelField
     * @return This for chaining.
     */
    public SelectizeConfig<T> optgroupLabelField(String optgroupLabelField) {
        this.optgroupLabelField = optgroupLabelField;
        return this;
    }

    /**
     * The name of the property to group items by. Defaults to 'optgroup'. 
     * @param optgroupField
     * @return This for chaining.
     */
    public SelectizeConfig<T> optgroupField(String optgroupField) {
        this.optgroupField = optgroupField;
        return this;
    }

    /**
     * The order of the options in the drop down. Default direction is ascending.
     * @param sortField
     * @return This for chaining.
     */
    public SelectizeConfig<T> sortField(String sortField) {
        return sortField(sortField, true);
    }

    public SelectizeConfig<T> sortField(String sortField, boolean asc) {
        return sortField(sortField, asc, 0);
    }

    /**
     * The order of the options in the drop down.
     * @param sortField
     * @return This for chaining.
     */
    public SelectizeConfig<T> sortField(String sortField, boolean asc, int order) {
        if (this.sortFields == null) {
            this.sortFields = new ArrayList<>();
        }
        this.sortFields.add(new SelectizeSort(sortField, asc, order));
        return this;
    }

    public List<SelectizeSort> getSortFields() {
        if (this.sortFields != null) {
            Collections.sort(this.sortFields);
        }
        return this.sortFields;
    }

    /**
     * Property names to analyze when filtering options. Defaults to text.
     * @param searchFields
     * @return This for chaining.
     */
    public SelectizeConfig<T> searchFields(String... searchFields) {
        for (String searchField : searchFields) {
            if (searchField != null) {
                searchField(searchField);
            }
        }
        return this;
    }

    public Set<String> getSearchFields() {
        return this.searchFields;
    }

    /**
     * Add property name to analyze when filtering options. Defaults to text.
     * @param searchField
     * @return This for chaining.
     */
    public SelectizeConfig<T> searchField(String searchField) {
        if (this.searchFields == null) {
            this.searchFields = new HashSet<>();
        }
        this.searchFields.add(searchField);
        return this;
    }

    /**
     * When searching for multiple terms (separated by space), this is the operator used.
     * @param searchConjunction
     * @return This for chaining.
     */
    public SelectizeConfig<T> searchConjunction(SearchConjunction searchConjunction) {
        this.searchConjunction = searchConjunction;
        return this;
    }

    public SelectizeConfig<T> lockOptgroupOrder(boolean lockOptgroupOrder) {
        this.lockOptgroupOrder = lockOptgroupOrder;
        return this;
    }

    /**
     * Copy the original input classes to the dropdown element. Defaults to true.
     * @param copyClassesToDropdown
     * @return This for chaining.
     */
    public SelectizeConfig<T> copyClassesToDropdown(boolean copyClassesToDropdown) {
        this.copyClassesToDropdown = copyClassesToDropdown;
        return this;
    }

    public SelectizeConfig<T> plugins(Plugin... plugins) {
        if (this.plugins == null) {
            this.plugins = new HashSet<>();
        }
        for (Plugin p : plugins) {
            this.plugins.add(p.toString().toLowerCase());
        }
        return this;
    }

    /**
     * @return List of configured options.
     */
    public List<T> getOptions() {
        return this.options;
    }

    /**
     * Clear configured options.
     * @return This for chaining.
     */
    public SelectizeConfig<T> clearOptions() {
        if (this.options != null) {
            this.options.clear();
        }
        return this;
    }

    /**
     * @return The currently selected items.
     */
    public List<T> getItems() {
        return this.items;
    }

    /**
     * Clear configured items / selected options.
     * @return This for chaining.
     */
    public SelectizeConfig<T> clearItems() {
        if (this.items != null) {
            this.items.clear();
        }
        return this;
    }

    /**
     * @return True if there are any options.
     */
    public boolean hasOptions() {
        return (this.options != null && !this.options.isEmpty());
    }

    private void addNotNull(Set<String> set, String value) {
        if (value != null) {
            set.add(value);
        }
    }

    @SuppressWarnings("unchecked")
    public boolean resolveAnnotations() {
        if (this.optionsClass == null && this.options != null && !this.options.isEmpty()) {
            T obj = this.options.get(0);
            this.optionsClass = (Class<T>) obj.getClass();
        }

        if (this.optionsClass != null) {
            Class<?> i = this.optionsClass;
            while (i != null && i != Object.class) {
                for (Field field : i.getDeclaredFields()) {
                    if (this.valueField == null) {
                        SelectizeOptionValue optionValue = field.getAnnotation(SelectizeOptionValue.class);
                        if (optionValue != null) {
                            this.valueClass = field.getType();
                            valueField(field.getName());
                        }
                    }

                    if (this.labelField == null) {
                        SelectizeOptionLabel optionLabel = field.getAnnotation(SelectizeOptionLabel.class);
                        if (optionLabel != null) {
                            labelField(field.getName());
                        }
                    }

                    SelectizeOptionSearch optionSearch = field.getAnnotation(SelectizeOptionSearch.class);
                    if (optionSearch != null) {
                        searchField(field.getName());
                    }

                    SelectizeOptionSort optionSort = field.getAnnotation(SelectizeOptionSort.class);
                    if (optionSort != null) {
                        sortField(field.getName(), optionSort.asc(), optionSort.order());
                    }
                }
                i = i.getSuperclass();
            }

            return true;
        }
        return false;
    }

    public JsonArray getItemsJson() {
        JsonArray arr = Json.createArray();
        if (this.items != null) {
            String valueFieldName = this.valueField;
            if (valueFieldName == null) {
                valueFieldName = DEFAULT_VALUE_FIELD;
            }

            Field valueField = null;
            for (T o : this.items) {
                if (valueField == null) {
                    valueField = getFieldByName(o.getClass(), valueFieldName);
                }

                boolean accessible = valueField.isAccessible();
                if (!accessible) {
                    valueField.setAccessible(true);
                }
                try {
                    Object value = valueField.get(o);
                    if (value != null) {
                        if (value instanceof Number) {
                            Number n = (Number) value;
                            arr.set(arr.length(), n.doubleValue());
                        } else if (value instanceof String) {
                            arr.set(arr.length(), (String) value);
                        } else if (value instanceof Boolean) {
                            arr.set(arr.length(), (Boolean) value);
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        return arr;
    }

    public Field getFieldByName(String name) {
        Class<?> clazz = this.getOptionsClass();
        if (clazz == null && this.options != null) {
            for (T o : this.options) {
                clazz = o.getClass();
                break;
            }
        }
        return getFieldByName(clazz, name);
    }

    public Field getFieldByName(Class<?> clazz, String name) {
        Field field = null;
        Class<?> i = clazz;
        while (i != null && i != Object.class) {
            if (field != null) {
                break;
            }
            for (Field f : i.getDeclaredFields()) {
                if (name.equals(f.getName())) {
                    field = f;
                    break;
                }
            }
            i = i.getSuperclass();
        }
        return field;
    }

    public JsonArray getOptionsJson() {
        return getOptionsJson(this.options);
    }

    public JsonArray getOptionsJson(List<T> options) {
        JsonArray arr = Json.createArray();
        if (options != null) {
            List<Field> fields = new ArrayList<>();
            Set<String> constrainToFields = new HashSet<>();
            if (this.useOnlyConfiguredFields) {
                addNotNull(constrainToFields, this.labelField);
                addNotNull(constrainToFields, this.optgroupField);
                addNotNull(constrainToFields, this.optgroupLabelField);
                addNotNull(constrainToFields, this.optgroupValueField);
                addNotNull(constrainToFields, this.valueField);
                if (this.searchFields != null) {
                    for (String s : searchFields) {
                        addNotNull(constrainToFields, s);
                    }
                }
                if (this.sortFields != null) {
                    for (SelectizeSort s : this.sortFields) {
                        addNotNull(constrainToFields, s.getName());
                    }
                }
            }

            for (T o : options) {
                if (fields.isEmpty()) {
                    Class<?> i = o.getClass();
                    while (i != null && i != Object.class) {
                        for (Field field : i.getDeclaredFields()) {
                            if (!field.isSynthetic() && (constrainToFields.contains(field.getName()) || !this.useOnlyConfiguredFields)) {
                                fields.add(field);
                            }
                        }
                        i = i.getSuperclass();
                    }
                }

                JsonObject optionObj = Json.createObject();
                for (Field f : fields) {
                    boolean accessible = f.isAccessible();
                    if (!accessible) {
                        f.setAccessible(true);
                    }
                    try {
                        Object value = f.get(o);
                        if (value != null) {
                            if (value instanceof Number) {
                                Number n = (Number) value;
                                optionObj.put(f.getName(), n.doubleValue());
                            } else if (value instanceof String) {
                                optionObj.put(f.getName(), (String) value);
                            } else if (value instanceof Boolean) {
                                optionObj.put(f.getName(), (Boolean) value);
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                if (optionLabelGenerator != null) {
                    String label = optionLabelGenerator.getLabel(o);
                    String name = this.labelField;
                    if (name == null) {
                        name = DEFAULT_LABEL_FIELD;
                    }
                    if (generatedLabelField != null) {
                        name = generatedLabelField;
                    }
                    optionObj.put(name, label);
                }

                arr.set(arr.length(), optionObj);
            }
        }
        return arr;
    }

    public List<T> getOptionsByValues(List<?> valueList) {
        List<T> result = new ArrayList<>();
        if (valueList != null && !valueList.isEmpty()) {
            String valueFieldName = this.valueField;
            if (valueFieldName == null) {
                valueFieldName = DEFAULT_VALUE_FIELD;
            }

            Field valueField = getFieldByName(valueFieldName);
            for (T o : this.options) {
                try {
                    boolean accessible = valueField.isAccessible();
                    if (!accessible) {
                        valueField.setAccessible(true);
                    }
                    Object object = valueField.get(o);
                    for (Object value : valueList) {
                        if (Objects.equals(object, value)) {
                            result.add(o);
                            break;
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public JsonObject buildJson() {
        resolveAnnotations();
        JsonObject map = Json.createObject();
        JUtils.putNotNull(map, "delimiter", delimiter);
        JUtils.putNotNull(map, "createOnBlur", createOnBlur);
        JUtils.putNotNull(map, "createFilter", createFilter);
        JUtils.putNotNull(map, "highlight", highlight);
        JUtils.putNotNull(map, "persist", persist);
        JUtils.putNotNull(map, "openOnFocus", openOnFocus);
        JUtils.putNotNull(map, "maxOptions", maxOptions);
        if (infiniteItems) {
            map.put("maxItems", Json.createNull());
        }
        JUtils.putNotNull(map, "maxItems", maxItems);
        JUtils.putNotNull(map, "hideSelected", hideSelected);
        JUtils.putNotNull(map, "closeAfterSelect", closeAfterSelect);
        JUtils.putNotNull(map, "allowEmptyOption", allowEmptyOption);
        JUtils.putNotNull(map, "scrollDuration", scrollDuration);
        JUtils.putNotNull(map, "loadThrottle", loadThrottle);
        JUtils.putNotNull(map, "loadingClass", loadingClass);
        JUtils.putNotNull(map, "placeholder", placeholder);
        if (preload != null) {
            JUtils.putNotNull(map, "preload", preload);    
        } else if (preloadOnFocus != null && preloadOnFocus) {
            JUtils.putNotNull(map, "preload", "focus");
        }
        JUtils.putNotNull(map, "dropdownParent", dropdownParent);
        JUtils.putNotNull(map, "addPrecedence", addPrecedence);
        JUtils.putNotNull(map, "selectOnTab", selectOnTab);
        JUtils.putNotNull(map, "diacritics", diacritics);
        // Data / Searching
        JUtils.putNotNull(map, "dataAttr", dataAttr);
        JUtils.putNotNull(map, "valueField", valueField);
        JUtils.putNotNull(map, "optgroupValueField", optgroupValueField);
        JUtils.putNotNull(map, "labelField", labelField);
        JUtils.putNotNull(map, "optgroupLabelField", optgroupLabelField);
        JUtils.putNotNull(map, "optgroupField", optgroupField);
        JUtils.putNotNullBuilders(map, "sortField", getSortFields());
        JUtils.putNotNull(map, "searchField", searchFields);
        if (searchConjunction != null) {
            JUtils.putNotNull(map, "searchConjunction", searchConjunction.toString().toLowerCase());
        }
        JUtils.putNotNull(map, "lockOptgroupOrder", lockOptgroupOrder);
        JUtils.putNotNull(map, "copyClassesToDropdown", copyClassesToDropdown);
        // plugins
        JUtils.putNotNull(map, "plugins", plugins);
        // options
        JsonArray optionsArray = getOptionsJson();
        if (optionsArray != null) {
            map.put("options", optionsArray);
        }
        JsonArray itemsArray = getItemsJson();
        if (itemsArray != null) {
            map.put("items", itemsArray);
        }
        return map;
    }

    public Class<T> getOptionsClass() {
        return optionsClass;
    }

    public Class<?> getValueClass() {
        return valueClass;
    }

    public LabelGenerator<T> getOptionLabelGenerator() {
        return optionLabelGenerator;
    }

    public String getGeneratedLabelField() {
        return generatedLabelField;
    }

    public boolean isUseOnlyConfiguredFields() {
        return useOnlyConfiguredFields;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public Boolean getCreateOnBlur() {
        return createOnBlur;
    }

    public String getCreateFilter() {
        return createFilter;
    }

    public Boolean getHighlight() {
        return highlight;
    }

    public Boolean getPersist() {
        return persist;
    }

    public Boolean getOpenOnFocus() {
        return openOnFocus;
    }

    public Integer getMaxOptions() {
        return maxOptions;
    }

    public Integer getMaxItems() {
        return maxItems;
    }

    public boolean isInfiniteItems() {
        return infiniteItems;
    }

    public Boolean getHideSelected() {
        return hideSelected;
    }

    public Boolean getCloseAfterSelect() {
        return closeAfterSelect;
    }

    public Boolean getAllowEmptyOption() {
        return allowEmptyOption;
    }

    public Integer getScrollDuration() {
        return scrollDuration;
    }

    public Integer getLoadThrottle() {
        return loadThrottle;
    }

    public String getLoadingClass() {
        return loadingClass;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public Boolean getPreload() {
        return preload;
    }

    public Boolean getPreloadOnFocus() {
        return preloadOnFocus;
    }

    public String getDropdownParent() {
        return dropdownParent;
    }

    public Boolean getAddPrecedence() {
        return addPrecedence;
    }

    public Boolean getSelectOnTab() {
        return selectOnTab;
    }

    public Boolean getDiacritics() {
        return diacritics;
    }

    public List<T> getOptgroups() {
        return optgroups;
    }

    public LabelGenerator<T> getOptgroupLabelGenerator() {
        return optgroupLabelGenerator;
    }

    public String getDataAttr() {
        return dataAttr;
    }

    public String getOptgroupValueField() {
        return optgroupValueField;
    }

    public String getOptgroupLabelField() {
        return optgroupLabelField;
    }

    public String getOptgroupField() {
        return optgroupField;
    }

    public SearchConjunction getSearchConjunction() {
        return searchConjunction;
    }

    public Boolean getLockOptgroupOrder() {
        return lockOptgroupOrder;
    }

    public Boolean getCopyClassesToDropdown() {
        return copyClassesToDropdown;
    }

    public Set<String> getPlugins() {
        return plugins;
    }

    public T createOptionWithLabel(String input) {
        try {
            T newOption = getOptionsClass().newInstance();
            Field labelField = getFieldByName(getLabelField());
            if (labelField != null) {
                labelField.setAccessible(true);
                labelField.set(newOption, input);
            }
            return newOption;
        } catch (Exception e) {

        }
        return null;
    }

}

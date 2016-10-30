package com.byteowls.vaadin.selectize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;
import elemental.json.JsonBoolean;
import elemental.json.JsonNumber;
import elemental.json.JsonString;
import elemental.json.JsonValue;

@JavaScript({"vaadin://selectize/jquery.min.js", "vaadin://selectize/selectize.min.js", "vaadin://selectize/selectize-connector.js"})
public class Selectize<T> extends AbstractJavaScriptComponent {

    private static final long serialVersionUID = -4371120535603078616L;

    public interface ValueChangeListener<T> extends Serializable {
        void valueChanged(List<T> items);
    }

    public interface LazyLoadingListener<T> extends Serializable {
        List<T> loadOptions(String filter);
    }

    public interface OptionCreateListener<T> extends Serializable {
        void createOption(T newOption);
    }

    private List<ValueChangeListener<T>> valueChangeListeners = new ArrayList<>();
    private List<LazyLoadingListener<T>> lazyLoadingListeners = new ArrayList<>();
    private List<OptionCreateListener<T>> optionCreateListeners = new ArrayList<>();

    private SelectizeConfig<T> config;

    /**
     * Construct a Selectize.
     */
    public Selectize() {
        addJsFunctions();
    }

    /**
     * @return configure the selectize component.
     * @see #config(Class) if you want to use annotation on beans.
     */
    public SelectizeConfig<T> config() {
        if (this.config == null) {
            this.config = new SelectizeConfig<>();
        }
        return this.config;
    }

    /**
     * 
     * @param optionClass 
     * @return a config container to configure the component. If you want to use annotations on your option beans provide the class.
     */
    public SelectizeConfig<T> config(Class<T> optionClass) {
        if (this.config == null) {
            this.config = new SelectizeConfig<>();
            this.config.optionsClass(optionClass);
        }
        return this.config;
    }

    @Override
    public void attach() {
        if (config != null) {
            getState().hasLazyLoadingListeners = (this.lazyLoadingListeners != null && !this.lazyLoadingListeners.isEmpty());
            getState().hasValueChangeListeners = (this.valueChangeListeners != null && !this.valueChangeListeners.isEmpty());
            getState().hasOptionCreateListeners = (this.optionCreateListeners != null && !this.optionCreateListeners.isEmpty());
            getState().configurationJson = config.buildJson();
        }
        super.attach();
    }

    /**
     * @return True if the connector's logs defined messages to "console.log" else logging is disabled.
     */
    public boolean isJsLoggingEnabled() {
        return getState().loggingEnabled;
    }

    /**
     * Enable or disables the connector's logging to "console.log"
     * @param jsLoggingEnabled If true the connector script will log defined messages to "console.log". Defaults to false. 
     */
    public void setJsLoggingEnabled(boolean jsLoggingEnabled) {
        getState().loggingEnabled = jsLoggingEnabled;
    }

    /**
     * Sets the given options and replaces any already existing ones. 
     */
    public void replaceOptions(List<T> options) {
        replaceOptions(options, false);
    }

    /**
     * Sets the given options and replaces any already existing ones. 
     */
    public void replaceOptions(List<T> options, boolean triggerDropdown) {
        SelectizeConfig<T> config = config().options(options);
        if (isAttached()) {
            callFunction("replaceOptions", config.getOptionsJson(), triggerDropdown);
        }
    }


    /**
     * Adds the given options to already existing ones.
     * @param options
     */
    public void addOptions(List<T> options) {
        addOptions(options, false);
    }

    /**
     * Adds the given options to already existing ones. 
     * @param options
     * @param triggerDropdown If true the option drop down is opened.
     */
    public void addOptions(List<T> options, boolean triggerDropdown) {
        for (T o : options) {
            config().option(o);
        }
        if (isAttached()) {
            callFunction("addOptions", config().getOptionsJson(options), triggerDropdown);
        }
    }

    /**
     * Clears all existing options
     */
    public void clearOptions() {
        config().clearOptions();
        if (isAttached()) {
            callFunction("clearOptions");
        }
    }

    public void replaceItem(T item) {
        SelectizeConfig<T> config = config().clearItems().item(item);
        if (item != null) {
            List<T> options = config().getOptions();
            if (options == null || !options.contains(item)) {
                ArrayList<T> helper = new ArrayList<>();
                helper.add(item);
                addOptions(helper);
            }
        }
        if (isAttached()) {
            callFunction("replaceItems", config.getItemsJson());
        }
    }

    public void replaceItems(List<T> items) {
        SelectizeConfig<T> config = config().items(items);
        if (isAttached()) {
            callFunction("replaceItems", config.getItemsJson());
        }
    }

    public void clearItems() {
        config().clearItems();
        if (isAttached()) {
            callFunction("clearItems");
        }
    }

    public void addItems(List<T> items) {
        for (T i : items) {
            config().item(i);
        }
    }


    private void addJsFunctions() {
        addFunction("onSelectizeValueChange", new JavaScriptFunction() {
            private static final long serialVersionUID = -7865596041611535165L;

            @Override
            public void call(JsonArray args) {
                JsonValue jsonValue = args.get(0);
                Class<?> valueClass = config().getValueClass();
                List<Object> valueList = new ArrayList<>();
                if (jsonValue instanceof JsonArray) {
                    JsonArray arr = (JsonArray) jsonValue;
                    for (int i = 0; i < arr.length(); i++) {
                        String stringValue = arr.getString(i);
                        Object valueObj = convertString(valueClass, stringValue);
                        if (valueObj != null) {
                            valueList.add(valueObj);
                        }
                    }
                } else  if (jsonValue instanceof JsonNumber) {
                    JsonNumber v = (JsonNumber) jsonValue;
                    Object valueObj = convertString(valueClass, String.valueOf(v.getNumber()));
                    if (valueObj != null) {
                        valueList.add(valueObj);
                    }

                }  if (jsonValue instanceof JsonString) {
                    JsonString v = (JsonString) jsonValue;
                    Object valueObj = convertString(valueClass, v.getString());
                    if (valueObj != null) {
                        valueList.add(valueObj);
                    }
                }  if (jsonValue instanceof JsonBoolean) {
                    JsonBoolean v = (JsonBoolean) jsonValue;
                    Object valueObj = convertString(valueClass, String.valueOf(v.getBoolean()));
                    if (valueObj != null) {
                        valueList.add(valueObj);
                    }
                }

                List<T> selected = config().getOptionsByValues(valueList);
                for (ValueChangeListener<T> l : valueChangeListeners) {
                    l.valueChanged(selected);
                }

            }
        });

        addFunction("onSelectizeLazyLoading", new JavaScriptFunction() {
            private static final long serialVersionUID = 2548790968137988000L;
            @Override
            public void call(JsonArray args) {
                String filter = args.getString(0);
                List<T> allOptions = new ArrayList<>();
                for (LazyLoadingListener<T> l : lazyLoadingListeners) {
                    List<T> options = l.loadOptions(filter);
                    if (options != null) {
                        allOptions.addAll(options);
                    }
                }

                if (!allOptions.isEmpty()) {
                    addOptions(allOptions, true);
                }

            }
        });

        addFunction("onSelectizeOptionCreate", new JavaScriptFunction() {
            private static final long serialVersionUID = 6245727067365295299L;

            @Override
            public void call(JsonArray args) {
                String input = args.getString(0);
                T newOption = config().createOptionWithLabel(input);
                for (OptionCreateListener<T> l : optionCreateListeners) {
                    // fill the option
                    l.createOption(newOption);
                }
                // filled option should be send to the client side
                config().option(newOption);
                List<T> list = new ArrayList<>();
                list.add(newOption);
                addOptions(list);
                addItems(list);
            }
        });
    }

    private Object convertString(Class<?> valueClass, String stringValue) {
        Object valueObj = null;
        if (stringValue != null && !stringValue.isEmpty()) {
            if (valueClass == null || valueClass.isAssignableFrom(String.class)) {
                valueObj = stringValue;
            } else if (valueClass.isAssignableFrom(Integer.class)) {
                valueObj = Integer.valueOf(stringValue);
            } else if (valueClass.isAssignableFrom(Long.class)) {
                valueObj = Long.valueOf(stringValue);
            } else if (valueClass.isAssignableFrom(Double.class)) {
                valueObj = Double.valueOf(stringValue);
            }
        }
        return valueObj;
    }

    /**
     * Add a listener to handle changed values at the server side.
     */
    public void addValueChangeListener(ValueChangeListener<T> listener) {
        this.valueChangeListeners.add(listener);
    }

    /**
     * Add a listener to handle lazy server side loading
     */
    public void addLazyLoadingListener(LazyLoadingListener<T> listener) {
        this.lazyLoadingListeners.add(listener);
    }

    /**
     * Add a listener to handle adding new options
     */
    public void addOptionCreateListener(OptionCreateListener<T> listener) {
        this.optionCreateListeners.add(listener);
    }

    @Override
    protected SelectizeState getState() {
        return (SelectizeState) super.getState();
    }
}

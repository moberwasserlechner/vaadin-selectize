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

    public interface BlurListener<T> extends Serializable {
        void valueChange(List<T> items);
    }
    private List<BlurListener<T>> blurListeners = new ArrayList<>();

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
     * @param options
     */
    public void replaceOptions(List<T> options) {
        SelectizeConfig<T> config = config().options(options);
        if (isAttached()) {
            callFunction("replaceOptions", config.getOptionsJson());
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


    private void addJsFunctions() {
        addFunction("onBlurSelectize", new JavaScriptFunction() {
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
                for (BlurListener<T> l : blurListeners) {
                    l.valueChange(selected);
                }

            }
        });
    }

    private Object convertString(Class<?> valueClass, String stringValue) {
        Object valueObj = null;
        if (stringValue != null && !stringValue.isEmpty()) {
            if (valueClass == null || valueClass.isAssignableFrom(String.class)) {
                valueObj = stringValue;
            } if (valueClass.isAssignableFrom(Integer.class)) {
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
     * Add a listener to handle the changed value from the editor.
     * @param listener a simple blurlistener retrieving just the value
     */
    public void addBlurListener(BlurListener<T> listener) {
        this.blurListeners.add(listener);
    }

    @Override
    protected SelectizeState getState() {
        return (SelectizeState) super.getState();
    }
}

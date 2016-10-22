package com.byteowls.vaadin.selectize;

import java.util.List;

import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({"vaadin://selectize/jquery.min.js", "vaadin://selectize/selectize.min.js", "vaadin://selectize/selectize-connector.js"})
public class Selectize<T> extends AbstractJavaScriptComponent {

    private static final long serialVersionUID = -4371120535603078616L;

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
        // this function can be called in connector.js e.g. self.onDataPointClick(datasetIndex, dataIndex)
        //        addFunction("TODO add function", new JavaScriptFunction() {
        //
        //            private static final long serialVersionUID = -7865596041611535165L;
        //
        //            @Override
        //            public void call(JsonArray arguments) {
        //            }
        //        });
    }

    @Override
    protected SelectizeState getState() {
        return (SelectizeState) super.getState();
    }
}

package com.byteowls.vaadin.selectize;

import com.byteowls.vaadin.selectize.config.SelectizeConfig;
import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;

@JavaScript({"vaadin://selectize/selectize.min.js", "vaadin://selectize/selectize-connector.js"})
public class Selectize extends AbstractJavaScriptComponent {

    private static final long serialVersionUID = -4371120535603078616L;

    private SelectizeConfig config;

    /**
     * Construct a Selectize. Be aware that you have to set a {@link ChartConfig} as well. Use {@link #configure(ChartConfig)} to do so.
     */
    public Selectize() {
        addJsFunctions();
    }
    
    public void setMultiple(boolean multiple) {
        getState().multiple = multiple;
    }
    
    /**
     * @return configure the selectize component.
     */
    public SelectizeConfig config() {
        if (this.config == null) {
            this.config = new SelectizeConfig();
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

    private void addJsFunctions() {
        // this function can be called in chartjs-connector e.g. self.onDataPointClick(datasetIndex, dataIndex)
        addFunction("TODO add function", new JavaScriptFunction() {

            private static final long serialVersionUID = -7865596041611535165L;

            @Override
            public void call(JsonArray arguments) {
            }
        });
    }

    @Override
    protected SelectizeState getState() {
        return (SelectizeState) super.getState();
    }
}

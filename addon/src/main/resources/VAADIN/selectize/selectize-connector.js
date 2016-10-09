window.com_byteowls_vaadin_selectize_Selectize = function() {
	var THEME_ID = "selectize-theme-id";
	var themeLink;
	// see the javadoc of com.vaadin.ui.
	// for all functions on this.
	var e = this.getElement();
	// Please note that in JavaScript, this is not necessarily defined inside callback functions and it might therefore be necessary to assign the reference to a separate variable
	var self = this;
	var loggingEnabled = false;
	var selectize;
	var stateChangedCnt = 0;

	// called every time the state is changed
	this.onStateChange = function() {
		stateChangedCnt++;
		var state = this.getState();
		loggingEnabled = state.loggingEnabled;
		if (loggingEnabled) {
			console.log("selectize: accessing onStateChange the "+stateChangedCnt+". time");
		}
		
		// theme
		if (typeof themeLink === 'undefined') {
			// check if a theme css is already loaded
			themeLink = document.getElementById(THEME_ID);
			if (themeLink == null) {
				themeLink = document.createElement("link");
				themeLink.setAttribute("rel", "stylesheet");
				themeLink.setAttribute("type", "text/css");
				themeLink.setAttribute("id", THEME_ID);
				document.getElementsByTagName("head")[0].appendChild(themeLink);
			}
		}
		var url = this.translateVaadinUri("vaadin://selectize/css/selectize."+state.theme.toLowerCase()+".css");
		themeLink.setAttribute("href", url);

		// detect jquery 1.7+
		if (typeof jQuery == 'undefined') {
			var jqueryUrl = this.translateVaadinUri("vaadin://selectize/jquery.min.js");
			this.loadScript(jqueryUrl, this.initComponent(state));
		} else {
			this.initComponent(state);
		}
	};
	
	/**
	 * 
	 */
	this.initComponent = function(state) {
		loggingEnabled = state.loggingEnabled;
		
		if (loggingEnabled) {
			console.log("selectize: configuration is\n", JSON.stringify(state.configurationJson, null, 2));
		}
		
		if (typeof selectize === 'undefined' && state.configurationJson !== 'undefined') {
			selectize = $("<select>").appendTo(e).selectize(state.configurationJson);
		}
	}

	/**
	 * 
	 */
	this.loadScript = function(url, callback) {
        var script = document.createElement("script")
        script.type = "text/javascript";

        if (script.readyState) { //IE
            script.onreadystatechange = function () {
                if (script.readyState == "loaded" || script.readyState == "complete") {
                    script.onreadystatechange = null;
                    callback();
                }
            };
        } else { //Others
            script.onload = function () {
                callback();
            };
        }

        script.src = url;
        
        var headElement = document.getElementsByTagName("head")[0];
        headElement.insertBefore(script, headElement.firstChild);
    };


};

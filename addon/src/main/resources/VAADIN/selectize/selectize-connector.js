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
			this.loadScript(jqueryUrl, (function() {
				self.initComponent(state);
			}));
		} else {
			self.initComponent(state);
		}
	};
	
	/**
	 * 
	 */
	this.initComponent = function(state) {
		var selectizeUrl = this.translateVaadinUri("vaadin://selectize/selectize.min.js");
		this.loadScript(selectizeUrl, (function() {
			loggingEnabled = state.loggingEnabled;
			if (loggingEnabled) {
				console.log("selectize: configuration is\n", JSON.stringify(state.configurationJson, null, 2));
			}
			
			if (typeof selectize === 'undefined' && state.configurationJson !== 'undefined') {
				selectize = $("<select>").appendTo(e).selectize(state.configurationJson);
			}
		}));
	}
	
	//this function will work cross-browser for loading scripts asynchronously
	this.loadScript = function(src, callback) {
		var r = false;
		var s = document.createElement('script');
		s.type = 'text/javascript';
		s.src = src;
		s.onload = s.onreadystatechange = function() {
			//console.log( this.readyState ); //uncomment this line to see which ready states are called.
			if (!r && (!this.readyState || this.readyState == 'complete')) {
				if (loggingEnabled) {
					console.log("selectize: loading script: " + src);
				}
				r = true;
				callback();
			}
		};
		
		var t = document.querySelector('script[src="'+this.translateVaadinUri("vaadin://selectize/selectize-connector.js")+'"]');
		t.parentNode.insertBefore(s, t);
	}

};

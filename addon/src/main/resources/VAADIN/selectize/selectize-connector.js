window.com_byteowls_vaadin_selectize_Selectize = function() {
	var THEME_ID = "selectize-theme-id";
	var themeLink;
	// see the javadoc of com.vaadin.ui.
	// for all functions on this.
	var e = this.getElement();
	// Please note that in JavaScript, this is not necessarily defined inside callback functions and it might therefore be necessary to assign the reference to a separate variable
	var self = this;
	var loggingEnabled = false;
	var selectElement;
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

		if (typeof selectElement === 'undefined' && state.configurationJson !== 'undefined') {
			if (loggingEnabled) {
				console.log("selectize: configuration is\n", JSON.stringify(state.configurationJson, null, 2));
			}

			var selectizeConfig = state.configurationJson;
			if (selectizeConfig.maxItems === null || selectizeConfig.maxItems > 1) {
				selectizeConfig.onBlur = function() {
					self.onBlurSelectize($(selectElement).val());
				};
			} else {
				selectizeConfig.onChange = function(value) {
					self.onBlurSelectize(value);
				};
			}
			selectElement = $("<select>").appendTo(e).selectize(state.configurationJson);
		}

		if (selectElement != null) {
			if (state.readOnly) {
				selectElement[0].selectize.lock();
			} else {
				selectElement[0].selectize.unlock();
			}
		}
	};

	this.replaceOptions = function(newOptions) {
		if (selectElement != null) {
			this.clearOptions();
			newOptions.forEach(function(o) {
				selectElement[0].selectize.addOption(o);
			});
			selectElement[0].selectize.refreshOptions(false);
		}
	}

	this.clearOptions = function() {
		if (selectElement != null) {
			selectElement[0].selectize.clearOptions();
		}
	}

	this.replaceItems = function(newItems) {
		if (selectElement != null) {
			this.clearItems();
			newItems.forEach(function(o) {
				selectElement[0].selectize.addItem(o, true);
			});
		}
	}

	this.clearItems = function() {
		if (selectElement != null) {
			selectElement[0].selectize.clear(true);
		}
	}
};

Buffalo.View = Class.create();

Buffalo.View.LAST_VIEWNAME = null;
Buffalo.View.CURRENT_VIEW = null;
Buffalo.View.HOME_VIEW = null;
Buffalo.View.HISTORY_IFRAME_ID = "buffalo-view-history-iframe";

Buffalo.View.iframeLoaded = function(loc) {
	var url = loc.href;
	
	var idx = url.indexOf("?");
	var viewName = "";
	if (idx > -1) {
		viewName = url.substring(idx+1);
	}
	
	if (viewName == "") {
		viewName = Buffalo.View.HOME_VIEW;
	}

	if (Buffalo.View.CURRENT_VIEW != null) {
		Buffalo.View.CURRENT_VIEW.doSwitchPart(viewName);
	}
}

Buffalo.View.prototype = {

	initialize:function(buffaloObj) {
		this.buffalo = buffaloObj;
	},

	switchPart: function(partId, viewName, addToHistory) {
		this.partId = partId;
		this.viewName = viewName;
		if (typeof(addToHistory) == "undefined" || addToHistory == true) {
			this.addToHistory = true;
		} else {
			this.addToHistory = false;
		}
		
		if (Buffalo.View.LAST_VIEWNAME == null) {
			/* the first visit view is home view */
			Buffalo.View.HOME_VIEW = viewName;
			/* The first view, don't add to history */
			this.doSwitchPart(viewName);
			Buffalo.View.LAST_VIEWNAME = viewName;
			return;
		}

		Buffalo.View.CURRENT_VIEW = this;

		if (this.addToHistory) {
			if ($(Buffalo.View.HISTORY_IFRAME_ID)) {
				var iframesrc=$(Buffalo.View.HISTORY_IFRAME_ID).src;
				var newUrl = iframesrc;
				var idx = iframesrc.indexOf("?");
				if (idx > -1) {
					newUrl = iframesrc.substr(0,idx);
				}
				newUrl += "?" + viewName;
				$(Buffalo.View.HISTORY_IFRAME_ID).src = newUrl;
			} else {
				var msg = "It seems that you havent add the buffalo-blank.html as an Iframe for browser history.";
				msg += "\nSo this view cannot add to browser history.";
				msg += "\n\nTo prevent this dialog, use buffalo.switchPart(partId, viewName, false) or ";
				msg += "add the buffalo-blank.html to your main page with id 'buffalo-view-history-iframe'.";

				alert(msg);
			}
		} 

		this.doSwitchPart(viewName);
		
		Buffalo.View.LAST_VIEWNAME = viewName;
		
	},
	
	doSwitchPart: function(viewName) {

		if (Buffalo.View.LAST_VIEWNAME == viewName) {
			return ;
		}

		this.transport = XmlHttp.create();
		var nonCachedViewName = viewName;
		try {
			/*Fix for the IE cache*/
			if (/MSIE/.test(navigator.userAgent)) {
				var bfViewHackKey = "_bfviewhackkey_=" + (new Date()).getTime();
				if (viewName.indexOf('?') > -1)	{
					nonCachedViewName += "&" + bfViewHackKey;
				} else {
					nonCachedViewName += "?" + bfViewHackKey;
				}
			}
			this.transport.open("GET", nonCachedViewName, this.buffalo.async);/*use get for static page*/
		} catch (e) {
			var msg = "Buffalo View Error: \n\n Cannot find view with name: " + "[" + viewName + "]";
			alert(msg);	
		}
		
		this.transport.send(null);
		if (this.buffalo.async) {
			this.transport.onreadystatechange = this._viewHandle.bind(this);
			this.buffalo.events["onLoading"](true);
		} else { 
			this._processView();
		}

		Buffalo.View.LAST_VIEWNAME = viewName;

	},

	_viewHandle : function(){
		this._processView();
	},

	_processView : function() {
		this.buffalo.events["onLoading"](false);
		if (this.transport.readyState == 4) {
			if (this.transport.status == '200') {
				var data = this.transport.responseText;
				this._showView(this.partId, this.viewName, data);
			} else {
				this.buffalo.events["onError"](this.transport);
			}
		}
	},

	_showView: function(partId, viewPath, viewData) {
		
		var regexp1 = /<script(.|\n)*?>(.|\n|\r\n)*?<\/script>/ig;
		var regexp2 = /<script(.|\n)*?>((.|\n|\r\n)*)?<\/script>/im;
		
		/* draw the html first */
		$(partId).innerHTML = viewData.replace(regexp1, "");
		
		var result = viewData.match(regexp1);
		if (result) {
			for (var i = 0; i < result.length; i++) {
				var realScript = result[i].match(regexp2);
				this._executeScript(realScript[2], partId);
				/* Note: do not try to write more than one <script> in your view.*/
				/* break;  process only one script element */
			}
		}
		
	},
	
	_executeScript : function(scriptFrag, partId) {
		var scriptContainerId = partId + "_SCRIPT_CONTAINER";
		var obj = $(scriptContainerId);
		var ss = document.getElementsByTagName("SCRIPT");
		if (obj != null) {
			document.body.removeChild(obj);
		}
		var scriptContainer = document.createElement('SCRIPT');
		scriptContainer.setAttribute("id", scriptContainerId);
		scriptContainer.text = scriptFrag;
		document.body.appendChild(scriptContainer);
	} 

}

Object.extend(Buffalo.prototype, {

	switchView: function(viewName) {
		this.switchPart("body", viewName, true);
	},
	
	switchPart : function(partId, viewName, addToHistory) {		
		new Buffalo.View(this).switchPart(partId, viewName, addToHistory);
	}
});

var Buffalo = Class.create();
Buffalo.BOCLASS="_BUFFALO_OBJECT_CLASS_";
Buffalo.VERSION="@@BUFFALO_VERSION@@";

Buffalo.prototype = {
	initialize: function(gateway, async, events, options) {
		this.gateway = gateway;
		this.transport = null;
		if (typeof(async) == 'undefined') {
			this.async = true;
		} else {
			this.async = async;
		}
		this.currentCallback = new Function();
		this.setEvents(events);
		this.queue = [];
		this.requesting = false;
		this.options = {timeout:30000};
		Object.extend(this.options, options || {});
	},
	
	getGateway : function() { return this.gateway;},

	setEvents: function(events) {
		this.events = {
			onLoading: Buffalo.Default.showLoading,
			onFinish: new Function(),
			onException: null,
			onError: Buffalo.Default.showError,
			onTimeout: new Function()
		};
		Object.extend(this.events, events || {});
	},
	
	_remoteCall: function(url, buffaloCall, callback) {
		this.requesting = true;
		this.transport = XmlHttp.create();
		try {
			this.transport.open("POST", url, this.async);
			this.transport.setRequestHeader("X-Buffalo-Version", Buffalo.VERSION);
			this.transport.setRequestHeader("Content-Type", "text/xml;charset=utf-8");
			this.transport.send(buffaloCall.xml());
		} catch (e) {
			this.events.onError(this.transport);
			this.events["onLoading"](false);
			return;
		}
		this.requestTime = new Date();
		this.timeoutHandle = new PeriodicalExecuter(this._timeoutChecker.bind(this), 0.5);
		this.currentCallback = callback;
		if (this.async) {
			this.transport.onreadystatechange = this.onStateChange.bind(this);
			this.events["onLoading"](true);
		} else { 
			this.response();
		}
	},
	
	_timeoutChecker: function() {
		if ((new Date() - this.requestTime) > this.options.timeout)	{
			this.events["onTimeout"]();
			this.timeoutHandle.stop();
		}
	},

	nextRemoteCall : function() {
		if (this.queue.length <= 0) return ;
		
		var command = this.queue.shift();
		this._remoteCall(command.url, command.call, command.callback);
	},

	remoteCall: function(service, params, callback) {
		var serviceMethodPair = this._splitServiceMethod(service);
		var newUrl = this.gateway+"/buffalo/"+serviceMethodPair[0];
		var call = new Buffalo.Call(serviceMethodPair[1]);
		for (var i = 0; i < params.length; i++) {
			call.addParameter(params[i]);
		}

		this.queue.push({url:newUrl, call: call, callback: callback});
		
		if (!this.requesting) {
			this.nextRemoteCall();
		}
	},
	
	_splitServiceMethod: function(service) {
		var idx = service.lastIndexOf(".");
		
		var serviceId = service.substring(0,idx);
		var method = service.substring(idx+1,service.length);

		return [serviceId, method];
	},
	
	onStateChange: function(){
		if (this.transport.readyState == 4) {
			this.response();
		}
	},
	
	response : function() {
		this.timeoutHandle.stop();
		this.events["onLoading"](false);
		if (this.transport.responseText && this.transport.status == '200') {
			var reply = new Buffalo.Reply(this.transport);
			if (reply.isFault()) {
			  if (this.events["onException"]) {
			    this.events["onException"](reply.getResult());
			  } else {
			    Buffalo.Default.showException(reply.getResult());
			    this.currentCallback(reply);  
			  }
			} else {
			  this.currentCallback(reply);  
			}
			this.events["onFinish"](reply);
			this.requesting = false;
			this.nextRemoteCall();
		} else {
			this.events["onError"](this.transport);
			this.requesting = false;
		}
	}
}

Buffalo.Default = {
	loadingPane: null,
	errorPane: null,
	exceptionPane: null,
	
	showLoading : function(state) {
		this.loadingPane = $("buffalo_loading");
		if (this.loadingPane == null) {
			var el = document.createElement('DIV');
			el.setAttribute("id","buffalo_loading");
			el.style.cssText="display:none;font-family:Verdana;font-size:11px;border:1px solid #00CC00;background-color:#A4FFA4;padding:1px;position:absolute; right:1px; top:1px; width:110px; height:14px; z-index:10000";
			el.innerHTML="buffalo loading... ";
			document.body.appendChild(el);
			this.loadingPane = el;
		}
		if (state) {
			this.loadingPane.style.display="block";
			this.loadingPane.style.top = document.body.scrollTop+1;
		} else {
			this.loadingPane.style.display="none";
		}
	},
	
	showError: function(transport) {
		this.errorPane = $("buffalo_error");
		if (this.errorPane == null) {
			var el = document.createElement('DIV');
			el.setAttribute("id","buffalo_error");
			el.style.cssText="font-size:11px;border:4px solid #FF3333;background-color:#fff;padding:4px;position:absolute;overflow:auto; right:10px; top:10px; width:500px; height:300px; z-index:1";
			el.innerHTML="<h2>Error: " + transport.status+" - "+transport.statusText+"</h2>";
			el.innerHTML+="<textarea style='width:96%;height:80%'>"+transport.responseText.stripTags()+"</textarea>";
      		el.onclick=function(){ el.style.display="none"; }
			document.body.appendChild(el);
			this.errorPane = el;
		} else {
			this.errorPane.style.display = "block";
		}
	},
	
	showException: function(faultObj) {
		this.exceptionPane = $("buffalo_exception");
		if (this.exceptionPane == null) {
			var el = document.createElement('DIV');
			el.setAttribute("id","buffalo_exception");
			el.style.cssText="font-size:11px;border:4px solid #FFFF33;background-color:#fff;padding:4px;position:absolute;overflow:auto; right:10px; top:10px; width:300px; height:100px; z-index:1";
			el.innerHTML ="<h2>Exception: " + faultObj.code+"</h2>";
			el.innerHTML += "Code: "+faultObj.code+"<br/>";
			el.innerHTML += "Message: "+faultObj.message+"<br/>";
			el.innerHTML += "Detail: " + faultObj.detail;
      		el.onclick=function(){ el.style.display="none"; }
			document.body.appendChild(el);
			this.exceptionPane = el;
		} else {
			this.exceptionPane.style.display = "block";
		}
	},
	
	showTimeout: function() {
		alert("timeout!");
	}
}

function getDomDocumentPrefix() {
	if (getDomDocumentPrefix.prefix)
		return getDomDocumentPrefix.prefix;
	
	var prefixes = ["MSXML2", "Microsoft", "MSXML", "MSXML3"];
	var o;
	for (var i = 0; i < prefixes.length; i++) {
		try {
			// try to create the objects
			o = new ActiveXObject(prefixes[i] + ".DomDocument");
			return getDomDocumentPrefix.prefix = prefixes[i];
		}
		catch (ex) {};
	}
	
	throw new Error("Could not find an installed XML parser");
}

function getXmlHttpPrefix() {
	if (getXmlHttpPrefix.prefix)
		return getXmlHttpPrefix.prefix;
	
	var prefixes = ["MSXML2", "Microsoft", "MSXML", "MSXML3"];
	var o;
	for (var i = 0; i < prefixes.length; i++) {
		try {
			o = new ActiveXObject(prefixes[i] + ".XmlHttp");
			return getXmlHttpPrefix.prefix = prefixes[i];
		}
		catch (ex) {};
	}
	
	throw new Error("Could not find an installed XMLHttp object");
}

function XmlHttp() {}

XmlHttp.create = function () {
	try {
		if (window.XMLHttpRequest) {
			var req = new XMLHttpRequest();
			if (req.readyState == null) {
				req.readyState = 1;
				req.addEventListener("load", function () {
					req.readyState = 4;
					if (typeof req.onreadystatechange == "function")
						req.onreadystatechange();
				}, false);
			}
			
			return req;
		}
		if (window.ActiveXObject) {
			return new ActiveXObject(getXmlHttpPrefix() + ".XmlHttp");
		}
	}
	catch (ex) {}
	throw new Error("Your browser does not support XmlHttp objects");
};

function XmlDocument() {}
XmlDocument.create = function () {
	try {
		if (document.implementation && document.implementation.createDocument) {
			var doc = document.implementation.createDocument("", "", null);
			if (doc.readyState == null) {
				doc.readyState = 1;
				doc.addEventListener("load", function () {
					doc.readyState = 4;
					if (typeof doc.onreadystatechange == "function")
						doc.onreadystatechange();
				}, false);
			}
			
			return doc;
		}
		if (window.ActiveXObject)
			return new ActiveXObject(getDomDocumentPrefix() + ".DomDocument");
	}
	catch (ex) {}
	throw new Error("Your browser does not support XmlDocument objects");
};

if (window.DOMParser &&
	window.XMLSerializer &&
	window.Node && Node.prototype && Node.prototype.__defineGetter__) {

	Document.prototype.loadXML = function (s) {
		
		var doc2 = (new DOMParser()).parseFromString(s, "text/xml");
		
		while (this.hasChildNodes())
			this.removeChild(this.lastChild);
		for (var i = 0; i < doc2.childNodes.length; i++) {
			this.appendChild(this.importNode(doc2.childNodes[i], true));
		}
	};
	
	Document.prototype.__defineGetter__("xml", function () {
		return (new XMLSerializer()).serializeToString(this);
	});
}

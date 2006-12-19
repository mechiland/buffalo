Buffalo.Reply = Class.create();
Buffalo.Reply.prototype = {
	initialize: function(xhr) {		
		this._isFault = false;
		this._type = "null";
		this._objects = [];
		this._objectNodes = [];
    this._source = xhr.responseText;

    var root = xhr.responseXML ? xhr.responseXML.documentElement :
              this.constructNodeFromXmlStringInIEOrFF(this._source);
    
		this.dataNode = root.firstChild;
		this._type = this._getType(this.dataNode);
	},
	
	constructNodeFromXmlStringInIEOrFF: function(xmlString) {
		var xmldoc = XmlDocument.create();
		xmldoc.async=false;
		xmldoc.loadXML(xmlString);
		return xmldoc.documentElement;
	}, 

	getType: function() { return this._type; },
	
	getResult : function() { return this.deserialize(this.dataNode); },
	
	isFault : function() { return (this._type == "fault"); },
	
	isNull: function() { return (this._type == "null"); },
	
	getSource : function() { return this._source; },
	
	deserialize: function(dataNode) {
		var ret;
		var type = this._getType(dataNode);
		switch (type) {
			case "boolean": ret = this.doBoolean(dataNode); break;
			case "date": ret = this.doDate(dataNode); break;
			case "double": ret = this.doDouble(dataNode); break;
			case "int": 
			case "long": 
				ret = this.doInt(dataNode);
				break;
			case "list": ret = this.doList(dataNode); break;
			case "map": ret = this.doMap(dataNode); break;
			case "null": ret = this.doNull(dataNode); break;
			case "ref": ret = this.doRef(dataNode); break;
			case "string": ret = this.doString(dataNode);break;
			case "xml": ret = this.doXML(dataNode); break;
			case "fault": ret = this.doFault(dataNode); break;
			default: ;
		}

		return ret;
	},
	
	_getType : function(dataNode) {
		return dataNode.tagName.toLowerCase();
	},
	
	getNodeText :function(dataNode) {
		if (dataNode && dataNode.hasChildNodes()) {
			var s = "";
			for (var i = 0; i < dataNode.childNodes.length; i++) {
				s += new String(dataNode.childNodes.item(i).nodeValue);
			}
			return s;
		} else {
			return null;
		}
	},

	doBoolean : function (dataNode) {
		var value = this.getNodeText(dataNode);
		return (value == "1");
	},
	
	doDate : function (dataNode) {

		var dateStr = this.getNodeText(dataNode);
		var year = parseInt(dateStr.substring(0,4),"10");
		var month = parseInt(dateStr.substring(4,6),"10") - 1;
		var day = parseInt(dateStr.substring(6,8),"10");
		var hour = parseInt(dateStr.substring(9,11),"10");
		var minute = parseInt(dateStr.substring(11,13),"10");
		var second = parseInt(dateStr.substring(13,15),"10");
		
		var d = new Date(year, month, day, hour, minute, second);
		return d;
	},
	
	doDouble : function (dataNode) {
		var value = this.getNodeText(dataNode);
		return parseFloat(value);
	},
	
	doInt: function (dataNode) {
		var value = this.getNodeText(dataNode);
		return parseInt(value);
	},
	
	doList: function (dataNode) {
		var arr = new Array();
		this._objects[this._objects.length] = arr;
    
    var children = dataNode.childNodes;
    arr[Buffalo.BOCLASS] = this.getNodeText(children[0]);
		for (var i=2; i < children.length; i++) {
			arr[arr.length] = this.deserialize(children[i]);
		}

		return arr;
	},

	doMap: function (dataNode) {
	
		var obj = new Object();
		this._objects[this._objects.length] = obj;

		var attrs = dataNode.childNodes;
		obj[Buffalo.BOCLASS] = this.getNodeText(attrs[0]);
		for (var i = 1; i < attrs.length; i+=2) {
			if (attrs[i+1].hasChildNodes() ) {
				obj[this.getNodeText(attrs[i])] = this.deserialize(attrs[i+1]);
			} else {
				obj[this.getNodeText(attrs[i])] = attrs[i+1].text;
			}
		}
		
		return obj;
	},
	
	doNull: function (dataNode) { return null;	},
	
	doRef: function (dataNode) {
		var value = this.getNodeText(dataNode);
		var idx = parseInt(value);
		
		return this._objects[idx];
	},
	
	doString: function (dataNode) {
		var value = this.getNodeText(dataNode);
		if (value == null) {
			return "";
		}
		return (value);
	},
	
	doXML : function (dataNode) {
		var value = this.getNodeText(dataNode);
		return unescape(value);
	},
	
	doFault : function (dataNode) {
		var code = this.getNodeText(dataNode.childNodes[1]);
		var msg = this.getNodeText(dataNode.childNodes[3]);
		var detail = this.deserialize(dataNode.childNodes[5]);
		return new Buffalo.Fault(code, msg, detail);
	}
}

Buffalo.Fault = Class.create();
Buffalo.Fault.prototype = {
	initialize: function(code, message, detail) {
		this.code = code;
		this.message = message;
		this.detail = detail;
	},
	toString: function() {
		return "Buffalo.Fault:[code=" + this.code + ", message=" + this.message + ", detail=" + this.detail+"]";
	}
}

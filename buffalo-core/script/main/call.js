Buffalo.Call = Class.create();
Buffalo.Call.prototype = {
	initialize: function(methodname){
		this.method = methodname;
		this.params = [];
        this._objects = [];
	},

	addParameter: function(data){
		if (typeof(data) == 'undefined') return;
		this.params[this.params.length] = data;
	},

	xml: function(){
        this._objects = [];
		var xmlstr = "<buffalo-call>\n";
		xmlstr += "<method>" + this.method+ "</method>\n";
		for (var i = 0; i < this.params.length; i++) {
		  var data = this.params[i];
		  xmlstr += this.getParamXML(this.dataTypeOf(data),data) + "\n";
		}
		xmlstr += "</buffalo-call>";
		return xmlstr; 
	},
	
	/* Guess the type of an javascript object */
	dataTypeOf: function (o){
		if (o == null) {
			return "null";
		}
		var type = typeof(o);
		type = type.toLowerCase();
		switch(type){
		  case "number":
			if (Math.round(o) == o) type = "int";
			else type = "double";
			break;
		  case "object":
			var con = o.constructor;
			if (con == Date) type = "date";
			else if (con == Array) type = "list";
			else type = "map";
			break;
		}
		return type;
	},
	
	doValueXML: function(type,data){
		var xml, str = data;
		if (typeof(data) == "string") {
			str = str.replace(/&/g,"&amp;");
			str = str.replace(/</g,"&lt;");
			str = str.replace(/>/g,"&gt;");
			xml = "<" + type + ">" + str + "</" + type + ">";
		} else {
			xml = "<" + type + ">" + data + "</" + type + ">";
		}
				
		return xml;
	},

	doBooleanXML:function(data){
		var value = (data==true)?1:0;
		var xml = "<boolean>" + value + "</boolean>";
		return xml;
	},
	
	doDateXML: function(data){
		var xml = "<date>";
		xml += dateToISO8609(data);
		xml += "</date>";
		return xml;
	},
	
	doArrayXML : function(data){
		var ref = this._checkRef(data);
        if (ref != -1) return "<ref>" + ref + "</ref>";
        this._objects[this._objects.length] = data;
        
        var xml = "<list>\n";
		var boClass = data[Buffalo.BOCLASS];
		var boType = boClass ? boClass : this.arrayType(data);
		xml += "<type>" +boType+ "</type>\n";
		xml += "<length>" +data.length+ "</length>\n";
		for (var i = 0; i < data.length; i++){
			xml += this.getParamXML(this.dataTypeOf(data[i]),data[i]) + "\n";
		}
		xml += "</list>\n";
		return xml;
	},
	
	arrayType: function(arr) {
		if (arr.length == 0) return "";
		var type = "";
		var obj = arr;
		while(this.isArray(obj)) {
			var canBeArray = true;
			for(var i = 0; i < obj.length; i++) {
				if (typeof(obj[i]) != typeof(obj[0])) {
					canBeArray = false;
					break;
				} else {
					if (typeof(obj[i]) == 'object') {
						if (obj[0][Buffalo.BOCLASS] != obj[i][Buffalo.BOCLASS]) {
							canBeArray = false;
							break;
						}
					} 
				}
			}
			if (canBeArray) {
				type+="[";	
				obj = obj[0];
			} else {
				break;
			}
		}
		if (type.indexOf("[") == -1) return "";
		var componentType = obj[Buffalo.BOCLASS] || typeof(obj);
		if (componentType == 'object') return "";
		return type+componentType;
	},
	
	isArray: function(obj) {
		return typeof(obj) == 'object' && obj.constructor == Array; 
	},
    
    _checkRef: function(obj) {
        var ref = -1;
        for (var i = 0; i < this._objects.length; i++) {
            if (obj === this._objects[i]) {
                ref = i; break;   
            }
        }
        return ref;
    },
	
	doStructXML : function(data){
        var ref = this._checkRef(data);
        if (ref != -1) return "<ref>" + ref + "</ref>";
        this._objects[this._objects.length] = data;
        
		var boType = data[Buffalo.BOCLASS] || "java.util.HashMap";
		var xml = "<map>\n";
		xml += "<type>" +boType+ "</type>\n";

		for (var i in data){
			if (data[i] != boType) { 
				if (typeof(data[i]) == "function") continue; /* the function shouldn't transfered. */
				xml += this.getParamXML(this.dataTypeOf(i),i)+"\n";
				xml += this.getParamXML(this.dataTypeOf(data[i]),data[i]) + "\n";
			}
		}
		xml += "</map>\n";
		return xml;
	},
	
	doNullXML : function() {
		return "<null></null>";
	},

	getParamXML: function(type,data){
		var xml;
		switch (type){
			case "date": xml = this.doDateXML(data); break;
			case "list": xml = this.doArrayXML(data); break;
			case "map": xml = this.doStructXML(data); break;
			case "boolean": xml = this.doBooleanXML(data); break;
			case "null": xml = this.doNullXML(); break;
			default: xml = this.doValueXML(type,data); break;
		}
		return xml;
	}
}

function dateToISO8609(date){
	var year = date.getYear();
	/* Fix for Y2K */
	if (year < 2000) {
		year += 1900;
	}
	var month = leadingZero(new String(date.getMonth()+1));
	var day = leadingZero(new String(date.getDate()));
	var time = leadingZero(new String(date.getHours())) + leadingZero(new String(date.getMinutes())) + leadingZero(new String(date.getSeconds()));

	var converted = year+month+day+"T"+time+"Z";
	return converted;
} 

function leadingZero(n){
	if (n.length==1) n = "0" + n;
	return n;
}

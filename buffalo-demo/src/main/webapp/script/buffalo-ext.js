// For Buffalo-EXT integration

//

Buffalo.Ext = {};

//Usage: new Buffalo.Ext.DataProxy("myService.method", [param1, param2])
Buffalo.Ext.DataProxy = function(endPoint, service, params, async, events, options) {
    Buffalo.Ext.DataProxy.superclass.constructor.call(this);
    this.buffalo = new Buffalo(endPoint, async, events, options);
    this.service =  service;
    this.params = params;
}

Ext.extend(Buffalo.Ext.DataProxy, Ext.data.DataProxy, {
   
    load : function(params, reader, callback, scope, arg){
        var self = this;
        if (this.fireEvent("beforeload", this, params) != false) {
            this.buffalo.remoteCall(this.service, this.params, function(reply) {
                if (reply.isFault()) {
                    // This should be processed in buffalo events...
                    alert(reply.getSource());
                    return;
                }
                reader.read(reply.getResult()); // let the reader to read the returned object.
                self.fireEvent("load", this, null, arg);
            });
        } else {
            callback.call(scope || this, null, arg, false);
        }
    } 
});


Buffalo.Ext.ObjectArrayReader = function(meta, recordType) {
    Buffalo.Ext.ObjectArrayReader.superclass.constructor.call(this, meta, recordType);
}

Ext.extend(Buffalo.Ext.ObjectArrayReader, Ext.data.JsonReader, {
    read : function(o) {
        if(o.metaData){
            delete this.ef;
            this.meta = o.metaData;
            this.recordType = Ext.data.Record.create(o.metaData.fields);
            this.onMetaChange(this.meta, this.recordType, o);
        }
        return this.readRecords(o);
    },
    
    readRecords : function(o){
        /**
         * After any data loads, the raw JSON data is available for further custom processing.
         * @type Object
         */
        this.jsonData = o;
        var s = this.meta, Record = this.recordType,
            f = Record.prototype.fields, fi = f.items, fl = f.length;

//      Generate extraction functions for the totalProperty, the root, the id, and for each field
        if (!this.ef) {
            if(s.totalProperty) {
	            this.getTotal = this.getJsonAccessor(s.totalProperty);
	        }
	        if(s.successProperty) {
	            this.getSuccess = this.getJsonAccessor(s.successProperty);
	        }
	        this.getRoot = s.root ? this.getJsonAccessor(s.root) : function(p){return p;};
	        if (s.id) {
	        	var g = this.getJsonAccessor(s.id);
	        	this.getId = function(rec) {
	        		var r = g(rec);
		        	return (r === undefined || r === "") ? null : r;
	        	};
	        } else {
	        	this.getId = function(){return null;};
	        }
            this.ef = [];
            for(var i = 0; i < fl; i++){
                f = fi[i];
                var map = (f.mapping !== undefined && f.mapping !== null) ? f.mapping : f.name;
                this.ef[i] = this.getJsonAccessor(map);
            }
        }

    	var root = this.getRoot(o), c = root.length, totalRecords = c, success = true;
    	if(s.totalProperty){
            var v = parseInt(this.getTotal(o), 10);
            if(!isNaN(v)){
                totalRecords = v;
            }
        }
        if(s.successProperty){
            var v = this.getSuccess(o);
            if(v === false || v === 'false'){
                success = false;
            }
        }
        var records = [];
	    for(var i = 0; i < c; i++){
		    var n = root[i];
	        var values = {};
	        var id = this.getId(n);
	        for(var j = 0; j < fl; j++){
	            f = fi[j];
                var v = this.ef[j](n);
                values[f.name] = f.convert((v !== undefined) ? v : f.defaultValue);
	        }
	        var record = new Record(values, id);
	        record.json = n;
	        records[i] = record;
	    }
        
	    return {
	        success : success,
	        records : records,
	        totalRecords : totalRecords
	    };
    }
});


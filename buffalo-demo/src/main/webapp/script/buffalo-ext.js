// For Buffalo-EXT integration

//

Buffalo.Ext = {};

//Usage: new Buffalo.Ext.DataProxy(buffalo, "myService.method", [param1, param2])
Buffalo.Ext.DataProxy = function(buffalo, service, params) {
    Buffalo.Ext.DataProxy.superclass.constructor.call(this);
    this.buffalo = buffalo;
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
                    // alert(reply.getSource());
                    return;
                }
                // let the reader to read the returned object.
                var result = reader.readRecords(reply.getResult()); 
                self.fireEvent("load", this, null, arg);
                callback.call(scope, result, arg, true);
            });
        } else {
            callback.call(scope || this, "", arg, false);
        }
    } 
});



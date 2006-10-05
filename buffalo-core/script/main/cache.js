
var Buffalo.Cache = {
	
}

Buffalo.CacheElement = Class.create();

Buffalo.CacheElement.prototype = {

	initialize:function(name) {
		this.name = name;
		this.createTime = new Date();
		this.lastAccess = new Date();
	}
	
	idleTime : function() {
		return (new Date()) - this.lastAccess;
	}
	
	
}
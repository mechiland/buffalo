<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Buffalo integrates with EXT</title>
<style>
body,input,select,textarea {font-size:12px;font-family: Arial, Helvetica, sans-serif;margin:3px}
</style>
<script language="JavaScript" src="script/prototype.js"></script>
<script language="JavaScript" src="script/buffalo.js"></script>
<script language="JavaScript" src="script/3rdparty/ext1.1/ext-base.js"></script>
<script language="JavaScript" src="script/3rdparty/ext1.1/ext-all.js"></script>
<script language="JavaScript" src="script/buffalo-ext.js"></script>
<link type="text/css" rel="stylesheet" href="script/3rdparty/ext1.1/resources/css/ext-all.css"></link>
</head>

<body>
<script language="javascript">
var endPoint="<%=request.getContextPath()%>/bfapp";
var buffalo = new Buffalo(endPoint);

var Example = {
    init : function(){
        
        var ds = new Ext.data.Store({
		        proxy: new Buffalo.Ext.DataProxy(endPoint, "simpleService.allLocales", []),
		        reader: new Buffalo.Ext.ObjectArrayReader ({
		        		
		        	}, [
                       {name: 'language'},
                       {name: 'country'},
                       {name: 'variant'},
                       {name: 'hashcode'},
                  ])
        });
        ds.load();

        var colModel = new Ext.grid.ColumnModel([
			{header: "Language", width: 160, sortable: true, dataIndex: 'language'},
			{header: "Country", width: 75, sortable: true, dataIndex: 'country'},
			{header: "Variant", width: 75, sortable: true, dataIndex: 'variant'},
			{header: "Hashcode", width: 75, sortable: true, dataIndex: 'hashcode'},
		]);

        // create the Grid
        var grid = new Ext.grid.Grid('grid-example', {
            ds: ds,
            cm: colModel,
        });
        
        var layout = Ext.BorderLayout.create({
            center: {
                margins:{left:3,top:3,right:3,bottom:3},
                panels: [new Ext.GridPanel(grid)]
            }
        }, 'grid-panel');

        grid.render();
        

        grid.getSelectionModel().selectFirstRow();
    }
};

Ext.onReady(Example.init, Example);

</script>

<h1>Buffalo-EXT Integration</h1>

<h2>Array Reader</h2>

<div id="grid-panel" style="width:600px;height:300px;">
<div id="grid-example"></div>
</div>

</body>
</html>
<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Buffalo Demo</title>
<style>
body,input,select,textarea {font-size:12px;font-family: Arial, Helvetica, sans-serif;margin:3px}
</style>
<script language="JavaScript" src="script/prototype.js"></script>
<script language="JavaScript" src="script/buffalo.js"></script>
<script language="JavaScript" src="script/template.js"></script>
</head>

<body>
<script language="javascript">
var endPoint="<%=request.getContextPath()%>/bfapp";
var buffalo = new Buffalo(endPoint);

function cmdDivide() {
	var d3Handle = $("double3");
	
	for (var i = 0; i < 10; i++) {
		buffalo.remoteCall("simpleService.divide",[i+0.8,i+1.1], function(reply) {
			$("result").value = reply.getSource();
			d3Handle.value = reply.getResult();
		})
	}
	
}

function error() {
	var newbuffalo = new Buffalo(endPoint);
	newbuffalo.remoteCall("simpleService.xxxx", [], function(reply){})
}

</script>

<h1>Basic</h1>
<hr/>
<h4>1 Division calculator</h4>
<p>
  <input name="double3" type="text" class="input_text" id="double3">
<input type="button" onclick="cmdDivide()" value="calculate">
</p>

<h4>Callback</h4>

<textarea id="result" rows="6" cols="60"></textarea>

<p>&nbsp;</p>
<input type="button" onclick="error()" value="error">
</body>
</html>
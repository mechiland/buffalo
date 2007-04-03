<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Buffalo Demo</title>
<script language="JavaScript" src="script/prototype.js"></script>
<script language="JavaScript" src="script/buffalo.js"></script>
<script language="JavaScript" src="script/template.js"></script>
</head>

<body>
<script language="javascript">
var endPoint="<%=request.getContextPath()%>/bfapp";
var buffalo = new Buffalo(endPoint);

function testArrayParameter() {
	var jj = new Array();
	
	buffalo.remoteCall("simpleService.testArrayParameter",[jj,'222'],function(reply) {
		alert(reply.getResult());
	});
}

</script>

<h1>Array parameter test</h1>
<hr/>
<button onclick="testArrayParameter()">Test</button>
</body>
</html>
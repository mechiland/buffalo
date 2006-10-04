<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Buffalo Demo</title>
<style>
body,input,select,textarea {font-size:12px;font-family: Arial, Helvetica, sans-serif;margin:3px}
#top #logo{ float:left; border-right:2px solid #fff}
#top #banner{float:inherit; background-color:#C2FFA6;height:54px;}
#menu {
	margin-top:2px;
	padding: 4px 0px 0px 0px;
	background: #333333;
	color: #ffffff;
	height:20px;
}

#menu ul {
	clear: left;
	margin: 0px;
	padding:0px;
	border: 0px;
	list-style-type: none;
	text-align: center;
	display:inline;
}

#menu li {
	float: left;
	display: block;
	margin: 0px;
	padding: 0px;
	width:120px;
	text-align: center
}

#menu li a {
	display: block;
	padding:0px 3px 2px 3px;
	width: 100%;
	color: #ffffff;
	text-decoration: underline;
}

#menu li a:hover {
	/*background-color: #c61c18;
	color: #fff; */
} 

#content{ margin-top:2px;}

#content #submenu {
	float:left;
	padding:10px;    
	background: #339900;   
	color: #fff;    
	width:150px;    
	border-right:3px solid #fff;
	height:360px;
}

#content #submenu strong{
	/*border:1px solid #fff;*/
	width:120px;display:block;
}

#content #submenu ul {
	margin: 0px;
	margin-top:2px;
	padding: 0px;
	border: medium none; 
	line-height: normal;
	list-style-type: none;
}

#content #submenu  li a {
padding:2px 0px 2px 8px;
display: block;
width: 100%;
color: #fff;
text-decoration: none;
}

#content #submenu  li a:hover { text-decoration:underline }

#content #body {
	margin-left:170px;
	background-color:#f0f0f0;
	padding:10px;
	padding-left:15px;
}

.style2 {
	font-size: 24px;
	color: #FF0000;
	font-weight: bold;
}

.mouseOut {
background: #7A8AFF;
color: #FFFAFA;
}

.mouseOver {
background: #FFFAFA;
color: #000000;
}
</style>
<script language="JavaScript" src="script/prototype.js"></script>
<script language="JavaScript" src="script/buffalo.js"></script>
<script language="JavaScript" src="script/template.js"></script>

<script language="JavaScript">
var endPoint="<%=request.getContextPath()%>/bfapp";

var pageBuffalo = new Buffalo(endPoint);

function switchPage(page) {	
	pageBuffalo.switchView("<%=request.getContextPath()%>/pages/"+page);
}


</script>
</head>

<body>
<script language="javascript">
var buffalo = new Buffalo(endPoint);
function openObj(obj) {
	var tmp = obj+"{";
	for (var x in obj) {
			tmp += x + "=" + obj[x]+";";
	}
	tmp += "}";
	return tmp;
}

function cmdDivide() {
	var double1 = parseFloat(Buffalo.getElementById("double1").value);
	var double2 = parseFloat(Buffalo.getElementById("double2").value);
	var d3Handle = Buffalo.getElementById("double3");
	
	buffalo.remoteCall("simpleService.divide",[double1,double2], function(reply) {
		$("result").value = reply.getSource();
		d3Handle.value = reply.getResult();
	})
}

function cmdServerTime() {
	var dtObj = Buffalo.getElementById("datetime");
	buffalo.remoteCall("simpleService.now",[], function(reply){
		dtObj.value = reply.getResult();
	})
}
function cmdException() {
	buffalo.remoteCall("simpleService.fault",[], function(reply) {
		var f = reply.getResult();
		alert(reply.getSource());
		//reportFault(f);
	})
}

function reportFault(f) {
	var str = openObj(f);
	alert(str);
}

function cmdRandomUser() {
	buffalo.remoteCall("simpleService.randomUser",[], function(reply) {
		var obj = reply.getResult();
		Buffalo.getElementById("user_id").value=obj.id;
		Buffalo.getElementById("user_name").value=obj.name;
		Buffalo.getElementById("user_age").value=obj.age;
		Buffalo.getElementById("user_sex").value=obj.sex;
		Buffalo.getElementById("user_memo").value=obj.memo;
	});
}

function cmdRandomComplexUser() {
	
	buffalo.remoteCall("simpleService.randomComplexUser",[], function(reply) {
		var obj = reply.getResult();
		
		Buffalo.getElementById("cuser_name").value=obj.name;
		Buffalo.getElementById("cuser_fullname").value=obj.name.firstName+" " + obj.name.middleName+" " +obj.name.familyName;
		Buffalo.getElementById("cuser_friends").value=friendsToStr(obj.friends);

	});
}

function friendsToStr(arr) {
	var str = "";
	for (var i = 0; i < arr.length; i++) {
		var obj = arr[i];
		str += obj.name.firstName+" " + obj.name.middleName+" " +obj.name.familyName + "\n";
	}
	return str;
}

function overloadTest(value) {
	buffalo.remoteCall("simpleService.overloadMethod", [value], function(reply) {
		alert(reply.getResult());
	});
}


</script>

<h1>Basic</h1>
<hr/>
<h4>1 Division calculator</h4>
<p>
  <input name="double1" type="text" class="input_text" id="double1" size="12"> 
  /
  <input name="double2" type="text" class="input_text" id="double2" size="12"> 
  <input type="button" name="Submit" value=" = " onclick="cmdDivide()">
  <input name="double3" type="text" class="input_text" id="double3">
</p>
<h4>2 What's Time?</h4>
<p>
  <input name="Submit2" type="button" onClick="cmdServerTime()" value="Server Time">
  <input name="datetime" type="text" id="datetime" size="50">
</p>
<h4>3 Throw Exception(fault)</h4>
<p>
  <input name="Submit3" type="button" onClick="cmdException()" value="Throw Exception">
</p>
<h4>4 Return Object</h4>
<p>
  <input name="Submit5" type="button" onClick="cmdRandomUser()" value="Random User">
</p>
<p>Object Consist of Name,Age,Sex,Memo</p>
<table border="1" bordercolor="#006600">
  <tr>
    <td>Number(1,2,3May use)</td>
    <td><input name="user_id" type="text" id="user_id">    </td>
  </tr>
  <tr>
    <td>Name</td>
    <td><input name="user_name" type="text" id="user_name"></td>
  </tr>
  <tr>
    <td>Age</td>
    <td><input name="user_age" type="text" id="user_age"></td>
  </tr>
  <tr>
    <td>Sex</td>
    <td><input name="user_sex" type="text" id="user_sex"></td>
  </tr>
  <tr>
    <td>Memo</td>
    <td><textarea name="user_memo" rows="4" wrap="VIRTUAL" id="user_memo"></textarea></td>
  </tr>
</table>

<h4>5 Advancde Object</h4>
<p>
  <input name="Submit5" type="button" onClick="cmdRandomComplexUser()" value="Random Advancde object">
</p>
<p>Advancde object name{First Name,middle name,family name},Friend[Advancde Object Array]<br>
  This complex example has mainly demonstrated the multistage data simple use, as well as between them quotation relations.<br>
  User[0] : {{&quot;John&quot;,&quot;M&quot;,&quot;Smith&quot;}}, friends=[User[1],User[2]]<br>
User[1] : {{&quot;Friend&quot;,&quot;H&quot;,&quot;Johnson&quot;}}, friends=[User[0],User[2]]<br>
User[2] : {{&quot;Michael&quot;,&quot;J&quot;,&quot;Jordon&quot;}}, friends=[User[1],User[0]]</p>
<table border="1" bordercolor="#006600">
  <tr>
    <td>Name(Object)</td>
    <td><input name="cuser_name" type="text" id="cuser_name"></td>
  </tr>
  <tr>
    <td>Full Name</td>
    <td><input name="cuser_fullname" type="text" id="cuser_fullname"></td>
  </tr>
  <tr>
    <td>Friend</td>
    <td><textarea name="cuser_friends" rows="10" cols="80" wrap="VIRTUAL" id="cuser_friends"></textarea></td>
  </tr>
</table>

<h4>6 overload teest</h4>

<input type="button" value="click Integer" onClick="overloadTest(123)" >
<input type="button" value="click String" onClick="overloadTest('hello')">

<h4>Callback</h4>

<textarea id="result" rows="6" cols="60"></textarea>

<p>&nbsp;</p>

<SCRIPT LANGUAGE="JavaScript">
<!--
cmdServerTime();
cmdDivide();
cmdRandomUser();
cmdRandomComplexUser();
cmdException();
//-->
</SCRIPT>
</body>
</html>
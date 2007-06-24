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
<script language="JavaScript" src="script/3rdparty/template.js"></script>

<script language="JavaScript">
var endPoint="<%=request.getContextPath()%>/bfapp";

var pageBuffalo = new Buffalo(endPoint);

function switchPage(page) {	
	pageBuffalo.switchView("<%=request.getContextPath()%>/pages/"+page);
}


</script>
</head>

<body onload="switchPage('home.html')">
<div id="top">
<div id="logo"><img src="images/buffalo.gif" width="160" height="54"></div>
<div id="banner"></div>
</div>
<div id="menu">
<ul>
	<li><a href="javascript:switchPage('home.html')">Home</a></li>
	<li><a href="javascript:switchPage('help.html')">Get help</a></li>
	<li>version: <script>document.write(Buffalo.VERSION);</script></li>
</ul>
</div>
<div id="content">
<div id="submenu"><strong>Demo market</strong>
<ul>
	<li page="simple">Basic</li>
	<li page="num">Number guess</li>
	<li page="cust-status">Customize status</li>
	<li page="object">Client object</li>
	<li page="ajax-autocomplete">Auto-completion</li>
	<li page="ajax-autocomplete-withbind">Auto-completion(bind)</li>
	<li page="classloader">Java API auto-completion</li>
</ul>
<br>

<strong>1.2 new features</strong>
<ul>
	<li page="simple-spring-2">Spring integration</li>
	<li page="broswer-back-forward">Browser back/fwd</li>
	<li page="form">Form demos</li>
</ul>
<br>

<strong>Buffalo-bind</strong>
<ul>
	<li page="bind">Binding</li>
	<li page="with-jst">JST Integration</li>
</ul>

</div>
<div id="body">
<p>&nbsp;</p>
<h1>Please wait, loading...</h1>
<p>&nbsp;</p>
</div>

<div id="poweredby">
<img src="images/buffalo-poweredby.gif" />
</div>
</div>
<script language="javascript">
$$("#submenu li").each(function(item) {
  Event.observe(item, "click", function(){
    switchPage(item.getAttribute("page")+".html");
  })
  Event.observe(item, "mouseover", function(){
    item.style.textDecoration="underline";
    item.style.cursor="pointer"
  });
  Event.observe(item, "mouseout", function(){
    item.style.textDecoration="none";
  });
})
</script>
<iframe src="buffalo-blank.html" id="buffalo-view-history-iframe" width="0" height="0" style="display:none;"></iframe>
</body>
</html>

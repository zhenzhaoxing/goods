<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="">

<title>body</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="content-type" content="text/html;charset=utf-8">


<link rel="stylesheet" type="text/css"
	href="<c:url value='/jsps/css/sezi.css'/>" />

<script type="text/javascript"
	src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/jsps/js/user/body.js'/>"></script>
<style type="text/css">
a {
	text-decoration: none;
}

h1 {
	text-align: center;
	font-family: "微软雅黑"
}

#sess {
	width: 900px;
	height: 405px;
	border: solid 1px #15B69A;
	position: relative;
	margin: 20px auto;
}

#sess img {
	width: 900px;
	position: absolute;
	top: 0;
	left: 0;
	display: none;
}

#sess ul {
	position: absolute;
	bottom: 12px;
	left: 350px;
}

#sess ul li {
	list-style: none;
	width: 19px;
	height: 19px;
	float: left;
	background: black;
	border-radius: 50%;
	margin-left: 5px;
}

#sess .icon-xiangzuo {
	position: absolute;
	left: 20px;
	top: 50%;
	transform: translateY(-50%);
	font-size: 40px;
	color: #666;
}

#sess .icon-you {
	position: absolute;
	right: 20px;
	top: 50%;
	transform: translateY(-50%);
	font-size: 45px;
	color: #666;
}

#sess .icon-xiangzuo:hover {
	color: red;
}

#sess .icon-you:hover {
	color: red;
	run
	();
}
</style>
</head>

<body>
	<h1>欢迎进入乐购网上书城系统</h1>
	<div id="sess">

		<img src="<c:url value='/images/lunbo2.jpg'/>" style="display: block;" />
		<img src="<c:url value='/images/lunbo3.jpg'/>" /> <img
			src="<c:url value='/images/lunbo4.jpg'/>" /> <img
			src="<c:url value='/images/luobotu.jpg'/>" />
		<ul>
			<li class="dian" style="background: #A10000;"></li>
			<li></li>
			<li></li>
			<li></li>
		</ul>

		<i class="iconfont icon-xiangzuo">&#xe699;</i> <i
			class="iconfont icon-you">&#xe698;</i>
	</div>



</body>
</html>

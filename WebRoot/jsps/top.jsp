<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>top</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	body {
	background:#15B69A;
		margin: 0px;
		color: #ffffff;
		font-family: "黑体"
	}
	a {
		text-transform:none;
		text-decoration:none;
		color: #ffffff;
		font-weight: 900;
		font-size: 18px;
	} 
	a:hover {
		
		color:blue;
	}
	.im1{
	position: relative;
	}
	.im1 img{
	position: absolute;
	top :-43px;
	left: 0px;
	}
	.im1 .nav{
	position: absolute;
	left:230px;
	}
	#nav2{
	position: absolute;
	left :300px;
	}
</style>
  </head>
  
  <body>
<h1 style="text-align: center;">乐购网上书城系统</h1>
<div  class="im1" style="font-size: 10pt; line-height: 10px;">

<c:choose>

<c:when test="${empty sessionScope.sessionUser }"> <!--  sessionScope= session.getArt.. -->
<div class="logo">
<%-- <img class="img"  src="<c:url value='/images/logo.png.png'/>"/> --%>
</div>
<div class="nav">
<a href="<c:url value='/jsps/user/login.jsp'/>" target="_parent">乐购会员登录</a> |&nbsp; 
		  <a href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent">注册乐购会员</a>
		  </div>
</c:when>
<c:otherwise>
<!-- 目标资源在body里面显示 在这里面设置在body显示 那么只要和这个有关的就都在 body里显示
像 我的购物车 提交到了 服务器 ，而服务器响应的内容也在body里面显示 下同
 -->
 <div class="logo1">
<%-- <img class="img"  src="<c:url value='/images/logo.png.png'/>"/> --%>
</div>
 <div id="nav2">
                                乐购会员：${sessionScope.sessionUser.loginname }&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/CartItemServlet?method=myCart'/>" target="body">我的购物车</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/OrderServlet?method=myOrders'/>" target="body">我的订单</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		
		  <a href="<c:url value='/jsps/user/pwd.jsp'/>" target="body">修改密码</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/UserServlet?method=quit'/>" target="_parent">退出</a>	
</div>
</c:otherwise>



</c:choose>

		  


		   

</div>
  </body>
</html>

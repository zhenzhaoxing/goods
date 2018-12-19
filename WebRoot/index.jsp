<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
	.box{
	margin: 10px 38%;
	display: block;
	width: 370px;
	height: 100px;
	background: green;
	 border-radius:15px;
	-webkit-transform: rotateX( 35deg ); 

	-moz-border-radius: 78px; 
-webkit-border-radius: 78px; 
box-shadow: inset 0px -4px 5px rgba(255,255,255,0.2), 
inset 0px 1px 5px rgba(255,255,255,0.2), 
	}
	span{
	
	color:white;
	}
	</style>
  </head>
  
  
  
  
  
  <body>
  <h1 align="center">注意本系统只是学习系统，请勿购买!</h1>
 <h1 align="center">注意本系统只是学习系统，请勿购买!</h1>
  <h1 align="center">注意本系统只是学习系统，请勿购买!</h1>
    
  <button class="box"><h2 align="center"><a href="jsps/main.jsp" style="text-decoration: none;font-size: 40px"><span>点击这里进入主页</span></a></h2></button>
  </body>
</html>

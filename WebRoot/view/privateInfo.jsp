<%@ page language="java"
	 pageEncoding="UTF-8"%>
	 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort

()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>所有图书信息</title>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
        text-align:center;
        margin-left:auto;
        margin-right:auto;
        width:50%;
      }
      
      
      </style>
</head>

<body>
	<h1>查询结果</h1>
	<table border=1 width=50%  class="table  table-condensed table-bordered">
		<tr class="success">
			<td>用户名</td>
			<td>密码</td>
			<td>管理权限</td>
			<td>性别</td>
			<td>学号</td>
			
		</tr>
		<c:forEach var="user" items="${users}">


			<tr>
				<td>${user.username}</td>
				<td>${user.password}</td>
				<td>${user.authority}</td>
				<td>${user.gender}</td>
				<td>${user.number}</td> 
				<td><a href="updatePrivateInfo?keyWord=${user.username}">修改</a></td>
		  </tr>
		</c:forEach>
	</table>


	<br>
	<br>
	<a href="queryBook?keyWord=${keyWord}&showPage=1">第一页</a>
	<a href="queryBook?keyWord=${keyWord}&showPage=${pageInfo.showPage-1}">上一页</a>
	<a href="queryBook?keyWord=${keyWord}&showPage=${pageInfo.showPage+1}">下一页</a>
	<a href="queryBook?keyWord=${keyWord}&showPage=${pageInfo.pageNum}">最后一页</a>
	<br>
	
	<form action="queryBook" method="post">
		<input type="hidden" name="keyWord" value="${keyWord}">
		<input type="text" name="showPage">
	 
		<input type="submit" value="确定">
	</form>
	<br>
	<a href="adminIndex">返回主界面</a>
	<br>


















</body>
</html>

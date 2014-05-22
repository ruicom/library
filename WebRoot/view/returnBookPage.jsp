<%@ page language="java"
	 pageEncoding="UTF-8"%>
	 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
			<td>索取号</td>
			<td>书名</td>
			<td>作者</td>
			<td>出版社</td>
			<td>出版时间</td>
			<td>借书时间</td>
			<td>最晚应还时间</td>
		</tr>
		<c:forEach var="borrowBook" items="${borrowBooks}">


			<tr>
				<td>${borrowBook.book.id}</td>
				<td>${borrowBook.book.bookname}</td>
				<td>${borrowBook.book.authod}</td>
				<td>${borrowBook.book.publishCompany}</td>
				<td>${borrowBook.book.publishTime}</td>
				<td>${borrowBook.borrowTime}</td>
				<td>${borrowBook.returnTime}</td>
				<td><a href="returnBook?bookid=${borrowBook.book.id}">还书</a></td> 
		  </tr>
		</c:forEach>
	</table>


	<br>
	<br>
	<a href="returnBookPage?showPage=1">第一页</a>
	<a href="returnBookPage?showPage=${pageInfo.showPage-1}">上一页</a>
	<a href="returnBookPage?showPage=${pageInfo.showPage+1}">下一页</a>
	<a href="returnBookPage?showPage=${pageInfo.pageNum}">最后一页</a>
	<br>
	
	<form action="returnBookPage">
		
		<input type="text" name="showPage">
	 
		<input type="submit" value="确定">
	</form>
	<br>
	<a href="userIndex">返回主界面</a>
	<br>


















</body>
</html>

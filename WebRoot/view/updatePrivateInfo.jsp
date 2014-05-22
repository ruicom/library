<%@ page language="java" import="java.util.*,javax.imageio.*"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>




<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Sign in &middot; Twitter Bootstrap</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

    </style>
    <link href="//cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap-responsive.min.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="//cdnjs.bootcss.com/ajax/libs/html5shiv/3.6.2/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
                                   <link rel="shortcut icon" href="../assets/ico/favicon.png">
  </head>

  <body>

    <div class="container">

      <form class="form-signin" action="updatePrivateInfo" method="post">
        <h2 class="form-signin-heading">用户修改</h2>
        
        
        用户名:<input type="text" class="input-block-level" name="username" value="${user.username} ">
        密码:<input type="password" class="input-block-level" name="password" value="${user.password}">
   重新输入密码 :<input type="password" class="input-block-level" name="repassword" value="${user.password}">
 
   	性别:<label class="checkbox inline"  for="mail">
  			<input type="radio" name="gender" value="男" id="mail">男
		</label>
		<label class="checkbox inline"  for="femail">
  			<input type="radio" name="gender" value="女" id="femail">女
		</label><br>
   		学号:<input type="text" class="input-block-level" name="number" value="${user.number}">	
        <label class="checkbox">
         
        </label>
        <input class="btn btn-large btn-primary" type="submit" value="修改"><br><br>
      	 <a href="adminIndex" class="btn btn-large btn-primary">返回主界面</a>
        <br><br>
       	${fail} 
		
      </form>
	
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="//cdnjs.bootcss.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="//cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>

  </body>
</html>


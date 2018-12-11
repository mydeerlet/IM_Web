<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link
	href="${pageContext.request.contextPath }/style/css/haiersoft.css"
	rel="stylesheet" type="text/css" media="screen,print" />
<link href="${pageContext.request.contextPath }/style/css/print.css"
	rel="stylesheet" type="text/css" media="print" />

<title>Insert title here</title>
</head>

<body>

	<!-- MainForm -->
	<div id="MainForm">
		<div class="form_boxA">

			<h2>结果查询</h2>
			<!-- 过滤条件 -->
			<br />
			<div id="QueryArea">
			
				<form action="" method="post">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text" name="tableName" title="" >
					&nbsp;&nbsp;<input type="submit" value="搜索"> 
				</form>
			</div>

			<br />
			
			<table cellpadding="0" cellspacing="0">
				<tr>
					<th>序号</th>
					<th>用户id</th>
					<th>结果</th>
					<th>插入时间</th>
				</tr>

				<c:forEach items="${ listDT}" var="item" varStatus = "r" >
					<tr>
						<td>${r.count }</td>
						<td>${item.userId }</td>
						<td>${item.result }</td>
						<td><fmt:formatDate value="${item.crateDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>

				</c:forEach>

			</table>

			<p class="msg">共找到47条会员记录，当前显示从第1条至第10条</p>
		</div>
	</div>
	<!-- /MainForm -->

</body>
</html>




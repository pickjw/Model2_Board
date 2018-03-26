<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<%-- <script type="text/javascript" src="http://code.jquery.com/jquery-3.2.1.js"></script> --%>
	<script type="text/javascript" src="../include/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">
	
	</script>
</head>
<body>
	<table class="table table-striped" style="border: 2px solid #dddddd">
		<!-- 회색빛 -->
	<c:forEach var="row" items="${list}">
		<tr>
			<td>
				${row.writer} 
				( <fmt:formatDate value="${row.reg_date}" pattern="yyyy-MM-dd hh:mm:ss"/> )<br>
				${row.content} 
			</td>
		</tr>
	</c:forEach>
	</table>

</body>
</html>
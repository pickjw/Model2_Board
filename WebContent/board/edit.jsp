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
	$(function(){
		$("#btnUpdate").click(function(){
			document.form1.action="${path}/board_servlet/update.do";
			document.form1.submit();
		});
		$("#btnDelete").click(function(){
			if(confirm("삭제하시겠습니까?")) {
				document.form1.action="${path}/board_servlet/delete.do";
				document.form1.submit();
			}
		});
	});
	</script>
</head>
<body>
	<hr>
	<h2>수정/삭제</h2>  <!--  enctype="multipart/form-data" 이거 사용시 request.getParameter("");사용이 안됨 -->
	<hr>
	<form  method="post" name="form1" enctype="multipart/form-data" >
		<table class="table table-striped" style="border: 2px solid #dddddd">
		<!-- 회색빛 -->
			<tr>
				<td>이름</td>
				<td><input name="writer" id="writer" value="${bdto.writer}"></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input name="subject" id="subject" size="60" value="${bdto.subject}"></td>
			</tr>
			<tr>
				<td>본문</td>
				<td><textarea rows="5" cols="60" name="content" >${bdto.content}</textarea></td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td>
					<c:if test="${bdto.filesize > 0 }">
						${bdto.filename} ( ${bdto.filesize  / 1024}  KB )
						<input type="checkbox" name="fileDel">첨부파일 삭제<br>
					</c:if>
					<input type="file" name="file1">
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="passwd" id="passwd"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<!-- 수정,삭제를 위한 글번호 -->
					<input type="hidden" name="num" value="${bdto.num }" >
					<input type="button" value="수정" id="btnUpdate">
					<input type="button" value="삭제" id="btnDelete">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
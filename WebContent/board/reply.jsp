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
		$("#btnSave").click(function(){
			var passwd = $("#passwd");
			if(passwd.val() == "" ) {
				alert("비밀번호를 입력하세요");
				gb_content.focus();
				return;
			}   
			document.form1.submit();
		});
	});
	</script>
</head>
<body>
	<hr>
	<h2>답변 쓰기</h2>
	<hr>
	<form action="${path}/board_servlet/insertReply.do" method="post" name="form1" >
		<table class="table table-striped" style="border: 2px solid #dddddd">
			<tr>
				<td>이름</td>
				<td><input name="writer" id="writer"></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input name="subject" id="subject" value="Re: ${bdto.subject}" size="60"></td>
			</tr>
			<tr>
				<td>본문</td>
				<td><textarea rows="5" cols="60" name="content">${bdto.content}</textarea></td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td><input type="file" name="file1"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="passwd" id="passwd"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<!-- 게시물 번호 -->
					<input type="hidden" name="num" value="${bdto.num}" >
					<input type="button" value="확인" id="btnSave">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
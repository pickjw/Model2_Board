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
			var form1 = $("#form1");
			var writer = $("#writer");
			var subject = $("#subject");
			var content = $("#content");
			var passwd = $("#passwd");
			if(writer.val() == "" ) {
				alert("이름을 입력하세요");
				gb_name.focus();
				return;
			}
			if(subject.val() == "" ) {
				alert("제목을 입력하세요");
				gb_email.focus();
				return;
			}
			if(content.val() == "" ) {
				alert("내용을 입력하세요");
				gb_passwd.focus();
				return;
			}
			if(passwd.val() == "" ) {
				alert("비밀번호를 입력하세요");
				gb_content.focus();
				return;
			}   
			document.form1.submit();
		});
		
		$("#btnList").click(function(){
			location.href="${path}/board_servlet/list.do";
		});
	});
	</script>
</head>
<body>
	<hr>
	<h2>글쓰기</h2>
	<hr>
	<form action="${path}/board_servlet/insert.do" method="post" name="form1" enctype="multipart/form-data">
		<table class="table table-striped" style="border: 2px solid #dddddd">
			<tr>
				<td>이름</td>
				<td><input name="writer" id="writer"></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input name="subject" id="subject" size="60"></td>
			</tr>
			<tr>
				<td>본문</td>
				<td><textarea rows="5" cols="60" name="content"></textarea></td>
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
					<input type="button" value="확인" id="btnSave">
					<input type="button" value="목록" id="btnList">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
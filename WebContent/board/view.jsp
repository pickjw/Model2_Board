<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<%-- <script type="text/javascript" src="http://code.jquery.com/jquery-3.2.1.js"></script> --%>
	<script type="text/javascript" src="../include/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">
	$(function (){
		comment_list();
		$("#btnSave").click(function(){
			comment_add();
		});
		$("#btnList").click(function(){
			location.href="${path}/board_servlet/list.do";
		});
		$("#btnReply").click(function(){
			document.form1.action="${path}/board_servlet/reply.do";
			document.form1.submit();
		});
		$("#btnEdit").click(function(){
			document.form1.action="${path}/board_servlet/pass_check.do";
			document.form1.submit();
		});btnEdit
	});
	function comment_list() {
		//백그라운드로 서버 호출
		$.ajax({
			type: "post",
			url: "${path}/board_servlet/commentList.do",
		    cache : false,
		    data: "num=${bdto.num}",
			success: function(result) {
				//responseText 서버의 응답 텍스트
				console.log(result);
				$("#commentList").html(result);
			}
		});
	}
	function comment_add() {
		var param = "board2_num=${bdto.num}&writer="+$("#writer").val()
				+"&content="+$("#content").val();
			$.ajax({
				type: "post",
				url: "${path}/board_servlet/comment_add.do",
			    cache : false,
			    data: param,
				success: function() {
					//responseText 서버의 응답 텍스트
					$("#writer").val("");
					$("#content").val("");
					comment_list();
				}
			});
	}
	</script>
</head>
<body>
	<hr>
	<h2>상세 화면</h2>
	<hr>
 	<form action="" method="post" name="form1">
	 	<table class="table table-striped" style="border: 2px solid #dddddd">
	 	
	 		<tr>
	 			<td>날짜</td>
	 			<td>${bdto.reg_date}</td>
	 			<td>조회수</td>
	 			<td>${bdto.readcount}</td>
	 		</tr>
	 		<tr>
	 			<td>이름</td>
	 			<td colspan="3">${bdto.writer}</td>
	 		</tr>
	 		<tr>
	 			<td>제목</td>
	 			<td colspan="3">${bdto.subject}</td>
	 		</tr>
	 		<tr>
	 			<td>본문</td>
	 			<td colspan="3">${bdto.content}</td>
	 		</tr>
	 		<tr>
	 			<td>비밀번호</td>
	 			<td colspan="3">
	 				<input type="password" name="passwd" id="passwd">
	 				<c:if test="${param.message == 'error' }">
	 					<span style="color:red;">
	 						비밀번호가 일치 하지 않습니다.
	 					</span>
	 				</c:if>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td>첨부파일</td>
	 			<td colspan="3">
	 				<c:if test="${bdto.filesize > 0 }">
		 				${bdto.filename} ( ${bdto.filesize} bytes) 
		 				<a href="${path}/board_servlet/download.do?num=${bdto.num}">[다운로드]</a>
	 				</c:if>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td colspan="4" align="center">
	 				<input type="hidden" name="num" value="${bdto.num}">
	 				<input type="button" value="수정/삭제" id="btnEdit">
	 				<input type="button" value="답변" id="btnReply">
	 				<input type="button" value="목록" id="btnList">
	 			</td>
	 		</tr>
	 		
	 	</table>
	 </form>
	<!--  댓글쓰기 폼 -->
	<table class="table table-striped" style="border: 2px solid #dddddd">
		<tr>
			<td><input id="writer" placeholder="이름"></td>
			<td rowspan="2">
				<button id="btnSave" type="button">확인</button>
			</td>
		</tr>
		<tr>
			<td>
				<textarea rows="5" cols="80" placeholder="내용을 입력하세요" id="content"></textarea>
			</td>
		</tr>
	</table>
	 
	<!--  댓글 목록을 출력할 영역 -->
	 <div id="commentList"></div>
	 
	 
	 
	 
</body>
</html>
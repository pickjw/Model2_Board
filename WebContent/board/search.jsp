<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script type="text/javascript" src="../include/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#btnWrite").click(function(){
			location.href="${path}/board/write.jsp";
		});
	});
	</script>
</head>
<body>
	<hr>
	<h2>게시판</h2>
	<hr>
	<form action="${path}/board_servlet/search.do" method="post" name="form1">
		<select name="search_option">
		<%-- 	if문의 경우 3번이나 검사 하기에 느릴수 있다. --%>
				<c:choose>
		    <c:when test="${search_option == 'writer' }">
				<option value="writer" selected="selected">이름</option>
				<option value="subject">제목</option>
				<option value="content">내용</option>
				<option value="all">이름+제목+내용</option>
		    </c:when>
		    <c:when test="${search_option == 'subject' }">
				<option value="writer" >이름</option>
				<option value="subject" selected="selected">제목</option>
				<option value="content">내용</option>
				<option value="all">이름+제목+내용</option>
		    </c:when>
		    <c:when test="${search_option == 'content' }">
				<option value="writer" >이름</option>
				<option value="subject">제목</option>
				<option value="content" selected="selected">내용</option>
				<option value="all">이름+제목+내용</option>
		    </c:when>
		     <c:when test="${search_option == 'all' }">
				<option value="writer" >이름</option>
				<option value="subject">제목</option>
				<option value="content">내용</option>
				<option value="all" selected="selected">이름+제목+내용</option>
		    </c:when>
		 </c:choose> 
		</select>
		<input name="keyword" value="${keyword}">
		<button id="btnSearch">검색</button>
	</form>
	<button id="btnWrite" class="btn btn-danger">글쓰기</button>
	<table class="table table-striped" style="border: 2px solid #dddddd">
		<tr>
			<th>번호</th>
			<th>이름</th>
			<th>제목</th>
			<th>날짜</th>
			<th>조회수</th>
			<th>첨부파일</th>
			<th>다운로드</th>
		</tr>
	<c:forEach var="bdto" items="${list}">
		<c:choose>
			<c:when test="${bdto.show == 'y' }" >
			<tr>
				<td>${bdto.num}</td>
				<td>${bdto.writer}</td>
				<td>
					<!-- 답글들여쓰기 -->
					<c:forEach var="i" begin="1" end="${bdto.re_level}">
						&nbsp;&nbsp;
					</c:forEach>
					<a href="${path}/board_servlet/view.do?num=${bdto.num}">${bdto.subject}</a>
					<!-- 댓글갯수 표시 -->
					<c:if test="${bdto.comment_count > 0}">
						<span style="color: red;">(${bdto.comment_count})</span>
					</c:if>
				</td>
				<td>${bdto.reg_date}</td>
				<td>${bdto.readcount}</td>
				<td align="center">
					<c:if test="${bdto.filesize > 0 }">
						<a href="${path}/board_servlet/download.do?num=${bdto.num}">
							<img src="../images/file.gif">
						</a>
					</c:if>
				</td>
				<td>${bdto.down}</td>
			</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td>${bdto.num}</td>
					<td colspan="6" align="center">삭제된 게시물입니다.</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	</table>
</body>
</html>
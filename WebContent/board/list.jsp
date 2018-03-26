<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width , initial-scale=1">
	<title>Insert title here</title>
	<script type="text/javascript" src="../include/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#btnWrite").click(function(){
			location.href="${path}/board/write.jsp";
		});
	});
	//클릭한 페이지로 이동
	function list(pager) {
		location.href="${path}/board_servlet/list.do?curPage="+pager;
	}
	</script>
</head>
<body>
	<hr>
	<h2>게시판</h2>
	<hr>
	<form action="${path}/board_servlet/search.do" method="post" name="form1">
		<select name="search_option">
			<option value="writer">이름</option>
			<option value="subject">제목</option>
			<option value="content">내용</option>
			<option value="all">이름+제목+내용</option>
		</select>
		<input name="keyword">
		<button id="btnSearch">검색</button>
	</form>
	<button id="btnWrite" class="btn btn-danger" >글쓰기</button>
	<table class="table table-striped" style="text-align: center; border: 2px solid #dddddd">
		<!-- 회색빛 -->
				<thead>
					<!-- 회색빛 -->
					<tr>
						<th style="background-color: #eeeeee; text-align: center;">번호</th>
						<th style="background-color: #eeeeee; text-align: center;">이름</th>
						<th style="background-color: #eeeeee; text-align: center;">제목</th>
						<th style="background-color: #eeeeee; text-align: center;">날짜</th>
						<th style="background-color: #eeeeee; text-align: center;">조회수</th>
						<th style="background-color: #eeeeee; text-align: center;">첨부파일</th>
						<th style="background-color: #eeeeee; text-align: center;">다운로드</th>
					</tr>
					<!-- 회색빛 -->
				</thead>
				<tbody>
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
			<!--  페이지 네비 게이션 출력 -->
			<tr>
				<td colspan="7" align="center">
					<c:if test="${pager.curBlock > 1}">
						<a href="#" onclick="list('1')">[처음]</a>
					</c:if>
					<c:if test="${pager.curBlock > 1}">
						<a href="#" onclick="list('${pager.prevPage}')">[이전]</a>
					</c:if>
					<c:forEach var="num" begin="${pager.blockStart}" end="${pager.blockEnd}">
						<c:choose>
							<c:when test="${num == pager.curPage }">
								<span style="color: red;">${num}</span>
							</c:when>
							<c:otherwise>
								<a href="#" onclick="list('${num}')">${num}</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${pager.curBlock < pager.totBlock}">
						<a href="#" onclick="list('${pager.nextPage}')">[다음]</a>
					</c:if>
					<c:if test="${pager.curPage < pager.totPage}">
						<a href="#" onclick="list('${pager.totPage}')">[끝]</a>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
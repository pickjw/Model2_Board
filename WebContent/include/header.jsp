<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 
JSTL(Jsp Standard Tag Library, JSP 표준 태그 라이브러리) 
taglib prefix="태그 접두어" uri="태그라이브러리의 식별자"
Core tag (핵심태그, 제일 자주 사용되는 태그들) => c
jsp 내부의 복잡한 자바 코드를 대체하기 위한 태그 
-->    
<%@ taglib prefix="c" 
uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 
uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- set var="변수명" value="값" -->
<c:set var="path" 
value="${pageContext.request.contextPath}" />
<link rel="stylesheet" 
href="${path}/include/css/bootstrap.css">
<link rel="stylesheet" 
href="${path}/include/css/custom.css">
<style type="text/css">
	a, a:hover {
		color: #000000;
		text-decoration: none;
	}

</style>

<script src="${path}/include/js/bootstrap.js"></script>






































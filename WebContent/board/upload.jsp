<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<!-- 
	method="post" enctype="multipart/form-data"  
	속성 2가지가 없으면 파일 업로드 안됨!  enctype="multipart/form-data"  =>파일을 끊어서 보냄
	-->
	<h2>파일 업로드</h2>
	<form name="form1" method="post" enctype="multipart/form-data"  action="upload_result.jsp">
	이름 : <input name="name"><br>
	제목 : <input name="subject"><br>
	파일1 : <input type="file" name="file1"><br>
	파일2 : <input type="file" name="file2"><br>
	<input type="submit" value="업로드">
	</form>

</body>
</html>
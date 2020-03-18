<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<h1>몽고디비 데이터 조회</h1>
		<hr/>
		<h3><a href="/mongoTest/score/list.do">mongodb데이터조회</a></h3>
		<!-- 첫번째 보여질 페이지 넘기기 -->
		<h3><a href="/mongoTest/score/pagingList.do?pageNo=0">mongodb데이터조회(페이징 처리)</a></h3>
		<h3><a href="/mongoTest/score/insert.do">mongodb document 삽입하기</a></h3>
		<h3><a href="/mongoTest/score/multiInsert.do">여러개 document 삽입하기</a></h3>
	    <h3><a href="/mongoTest/score/search.do">검색</a></h3>
	     <h3><a href="/mongoTest/score/search.do">검색</a></h3>
	      
	</div>
</body>
</html>


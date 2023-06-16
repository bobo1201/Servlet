<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%-- EL에서 기본으로 제공하는 param이라는 객체를 이용해 result 값 확인 --%>
<c:if test="${param.result == 'error'}">
    <h1>로그인 에러</h1>
</c:if>

<form action="/login" method="post">
  <input type="text" name="mid">
  <input type="text" name="mpw">
<%-- uuid를 위해 추가 --%>
  <input type="checkbox" name="auto">
  <button type="submit">LOGIN</button>
</form>
</body>
</html>

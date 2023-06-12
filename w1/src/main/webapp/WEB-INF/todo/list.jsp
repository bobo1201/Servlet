<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <h1>List Page</h1>

<%--  var : EL에서 사용될 변수 이름--%>
<%--  items : List, Set, Map, Enumeration, Iterator 등의 컬렉션--%>
<%--  begin/end : 반복의 시작/끝 값--%>

  <ul>
      <c:forEach var="dto" items="${list}">
          <li>${dto}</li>
      </c:forEach>
  </ul>

  <%-- 반복문 --%>
  <ul>
      <c:forEach var="num" begin="1" end="10">
          <li>${num}</li>
      </c:forEach>
  </ul>

<%-- 제어문 --%>
<c:if test="${list.size() % 2 == 0}">
    짝수
</c:if>
<c:if test="${list.size() % 2 != 0}">
  홀수
</c:if>

<c:choose>
    <c:when test="${list.size() % 2 == 0}">
        짝수
    </c:when>
    <c:otherwise>
        홀수
    </c:otherwise>
</c:choose>

<%-- set을 이용해서 새로운 변수 생성, 변수명과 값 지정 --%>
<c:set var="target" value="5"></c:set>

<%-- 5알때 해당 값 출력 --%>
  <ul>
      <c:forEach var="num" begin="1" end="10">
          <c:if test="${num == target}">
              num is target
          </c:if>
      </c:forEach>
  </ul>

</body>
</html>
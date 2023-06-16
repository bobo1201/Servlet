<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Todo Read</title>
</head>
<body>
<div>
  <input type="text" name="tno" value="${dto.tno}" readonly>
</div>
<div>
  <input type="text" name="title" value="${dto.title}" readonly>
</div>
<div>
  <input type="date" name="dueDate" value="${dto.dueDate}" readonly>
</div>
<div>
  <%-- checkbox는 readonly가 아니라 disabled가 되어야함 --%>
  <input type="checkbox" name="finished" ${dto.finished ? "checked" : ""} disabled>
</div>
<div>
  <a href="/todo/modify?tno=${dto.tno}">Modify/Remove</a>
  <a href="/todo/list">List</a>
</div>
</body>
</html>

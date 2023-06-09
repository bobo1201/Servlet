<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>NUM1 ${param.num1}</h1>
    <h1>NUM2 ${param.num2}</h1>

    <%-- 자동 형변환이 일어나기 때문에 + 연산이 수행됨, 실제로는 Integer.parseInt로 형변환 필요    --%>
<%--    <h1>SUM ${param.num1 + param.num2}</h1>--%>
    <h1>SUM ${Integer.parseInt(param.num1) + Integer.parseInt(param.num2)}</h1>

</body>
</html>
<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 검증된 결과 확인하기 위해 태그 라이브러리 추가 --%>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Bootstrap demo</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<div class="container-fluid">
  <div class="row">
    <!--    <h1>Header</h1>-->
    <div class="row">
      <div class="col">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
          <div class="container-fluid">
            <a class="navbar-brand" href="#">Navbar</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <div class="navbar-nav">
                <a class="nav-link active" aria-current="page" href="#">Home</a>
                <a class="nav-link" href="#">Features</a>
                <a class="nav-link" href="#">Pricing</a>
                <a class="nav-link" href="#">Disabled</a>
              </div>
            </div>
          </div>
        </nav>
      </div>
    </div>
    <!-- header end -->
    <!-- 기존의 <h1>Header</h1> 끝 -->

    <div class="row content">
      <div class="col">
        <div class="card">
          <div class="card-header">
            Featured
          </div>
          <div class="card-body">
            <form action="/todo/modify" method="post">

              <%-- form에서 내용 전송시 페이지 관련 정보 같이 추가해서 전달 --%>
              <%-- 필요 없는 내용이라 삭제 --%>
              <%--              <input type="hidden" name="page" value="${pageRequestDTO.page}">--%>
<%--              <input type="hidden" name="size" value="${pageRequestDTO.size}">--%>

              <div class="input-group mb-3">
                <span class="input-group-text">TNO</span>
                <input class="form-control" type="text" name="tno"
                 value=<c:out value="${dto.tno}"></c:out> readonly>
              </div>

              <div class="input-group mb-3">
                <span class="input-group-text">Title</span>
                <input class="form-control" type="text" name="title"
                      value=<c:out value="${dto.title}"></c:out>>
              </div>

              <div class="input-group mb-3">
                <span class="input-group-text">DueDate</span>
                <input class="form-control" type="date" name="dueDate"
                       value=<c:out value="${dto.dueDate}"></c:out>>
              </div>

              <div class="input-group mb-3">
                <span class="input-group-text">Writer</span>
                <input class="form-control" type="text" name="writer"
                       value=<c:out value="${dto.writer}"></c:out> readonly>
              </div>

              <div class="form-check">
                <label class="form-check-label">
                  Finished &nbsp;
                </label>
                <input class="form-check-input" type="checkbox" name="finished"
                       ${dto.finished?"checked":""}>
              </div>

              <div class="my-4">
                <div class="float-end">
                  <button type="button" class="btn btn-danger">Remove</button>
                  <button type="button" class="btn btn-primary">Modify</button>
                  <button type="button" class="btn btn-secondary">List</button>
                </div>
              </div>
            </form>
          </div>
          <script>
            // 검증된 정보를 처리하는 코드를 추가, @Valid 문제가 발생했다면
            // 자바스크립트 객체로 필요할 때 사용할 수 있도록함
            <%--const serverValidResult = {}--%>
            <%--<c:forEach items="${errors}" var="error">--%>
            <%--serverValidResult['${error.getField()}'] = '${error.defaultMessage}'--%>
            <%--</c:forEach>--%>

            <%--console.log(serverValidResult)--%>
          </script>

          <script>
            const formObj = document.querySelector("form")

            document.querySelector(".btn-danger").addEventListener("click", function (e){
              // 이벤트 객체 'e'의 기본 동작을 취소
              e.preventDefault()
              // 이벤트 객체 'e'의 전파를 중단
              e.stopPropagation()
              // form의 action 속성을 "/todo/remove"로 경로 설정
              // 페이징으로 경로 수정
              formObj.action = `/todo/remove?${pageRequestDTO.link}`
              // form의 method 속성을 "post"로 설정
              formObj.method = "post"

              // form을 서버로 제출합니다.
              formObj.submit()
            }, false);

            document.querySelector(".btn-primary").addEventListener("click", function (e){
              e.preventDefault()
              e.stopPropagation()

              formObj.action = "/todo/modify"
              formObj.method = "post"

              formObj.submit()
            }, false);

            document.querySelector(".btn-secondary").addEventListener("click", function (e){
              e.preventDefault()
              e.stopPropagation()
              // 경로 수정(현재 페이지 정보 유지)
              self.location = `/todo/list?${pageRequestDTO.link}`
            }, false);

          </script>
        </div>
      </div>
    </div>
  </div>
  <div class="row footer">
    <!--    <h1>Footer</h1>-->
    <div class="row fixed-bottom" style="z-index: -100">
      <footer class="py-1 my-1">
        <p class="text-center text-muted">Footer</p>
      </footer>
    </div>
  </div>
</div>

<script>

  const serverValidResult = {}
  <c:forEach items = "${errors}" var="error">
  serverValidResult['${error.getField()}'] = '${error.defaultMessage}'
  </c:forEach>

  console.log(serverValidResult)

</script>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>
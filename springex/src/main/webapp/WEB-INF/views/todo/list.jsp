<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 웹페이지에서 한글 깨질시 위의 코드 실행 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
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
            <h5 class="card-title">Special title treatment</h5>
            <table class="table">
              <thead>
              <tr>
                <th scope="col">Tno</th>
                <th scope="col">Title</th>
                <th scope="col">Writer</th>
                <th scope="col">DueDate</th>
                <th scope="col">Finished</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach items="${responseDTO.dtoList}" var="dto">
                <tr>
                  <th scope="row"><c:out value="${dto.tno}" /></th>
                  <td><a href="/todo/read?tno=${dto.tno}" class="text-decoration-none">
                    <c:out value="${dto.title}" /></a> </td>
                  <td><c:out value="${dto.writer}" /> </td>
                  <td><c:out value="${dto.dueDate}" /> </td>
                  <td><c:out value="${dto.finished}" /> </td>
                </tr>
              </c:forEach>
              </tbody>
            </table>

            <%-- 페이지네이션(부트스트랩) 이용 --%>
            <div class="float-end">
              <ul class="pagination flex-wrap">
                <c:if test="${responseDTO.prev}">
                  <li class="page-item">
                      <%-- ul태그에 이벤트 처리할 것, data- 속성을 이용해 필요한 속성 추가
                       data-num이라는 속성 추가해 페이지 번호 보관 --%>
                    <a class="page-link" data-num="${responseDTO.start-1}">Previous</a>
                  </li>
                </c:if>

                <%-- 첫번째 페이지일때 Previous를 출력안함 --%>
                <c:forEach begin="${responseDTO.start}" end="${responseDTO.end}" var="num">
                  <li class="page-item" ${responseDTO.page == num? "active":""}><a class="page-link" href="#">${num}</a></li>
                </c:forEach>

                <c:if test="${responseDTO.next}">
                  <li class="page-item">
                    <a class="page-link" data-num="${responseDTO.end + 1}">Next</a>
                  </li>
                </c:if>
              </ul>
            </div>

            <script>
              document.querySelector(".pagination").addEventListener("click", function (e){
                e.preventDefault()
                e.stopPropagation()

                const target = e.target

                if(target.tagName !== 'A'){
                  return
                }

                const num = target.getAttribute("data-num")

                self.location = `/todo/list?page=\${num}` // 백틱을 이용해 템플릿 처리
              }, false);
            </script>
          </div>
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


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: hamid
  Date: 6/29/21
  Time: 6:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="component/navbar.jsp"  %>
<div class="container">
<form action="/user-register" method="post">
    <div class="mb-3">
        <label for="exampleFormControlInput1" class="form-label">Email address</label>
        <input type="email" class="form-control" name="username" id="exampleFormControlInput1" placeholder="name@example.com">
    </div>
    <div class="mb-3">
        <label for="exampleFormControlInput2" class="form-label">Password</label>
        <input type="password" class="form-control" name="password" id="exampleFormControlInput2">
    </div>
    <div class="mb-3">
        <label for="exampleFormControlInput3" class="form-label">Name</label>
        <input type="text" class="form-control" name="name" id="exampleFormControlInput3">
    </div>
    <div class="mb-3">
        <label for="exampleFormControlInput4" class="form-label">Surname</label>
        <input type="text" class="form-control" name="surname" id="exampleFormControlInput4">
    </div>
    <label class="form-label">Gender</label>
    <select class="form-select" aria-label="Default select example" name="gender">
        <c:forEach items="${genders}" var="gender">
            <c:choose>
                <c:when test="${gender.getValue() == 2}">
                    <option selected value="${gender.getValue()}">${gender.getName()}</option>
                </c:when>
                <c:otherwise>
                    <option value="${gender.getValue()}">${gender.getName()}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </select>
    <label class="form-label">Type</label>
    <select class="form-select" aria-label="Default select example" name="userType">
        <c:forEach items="${userTypes}" var="userType">
            <c:choose>
                 <c:when test="${userType.getValue() == 2}">
                    <option selected value="${userType.getValue()}">${userType.getName()}</option>
                 </c:when>
                 <c:otherwise>
                    <option value="${userType.getValue()}">${userType.getName()}</option>
                 </c:otherwise>
            </c:choose>
        </c:forEach>
    </select>
    <button type="submit">OK</button>
</form>
</div>

</body>
</html>

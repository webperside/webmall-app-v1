<%--
  Created by IntelliJ IDEA.
  User: hamid
  Date: 7/10/21
  Time: 11:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Webmall - Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>

<%@ include file="component/navbar.jsp"  %>
<div class="container">

    <c:choose>
        <c:when test="${code!= null && code == -1}">
            <div class="alert alert-danger" role="alert">
                    ${msg}
            </div>
        </c:when>
        <c:otherwise>
            <%
                String msg =(String) request.getAttribute("msg");
                if(msg != null){
            %>
                    <div class="alert alert-success" role="alert">
                            ${msg}
                    </div>
            <%
                }
            %>
        </c:otherwise>
    </c:choose>

    <form action="/login" method="post">
        <div class="mb-3">
            <label for="exampleFormControlInput1" class="form-label">Email address</label>
            <input type="email" class="form-control" name="username" id="exampleFormControlInput1" placeholder="name@example.com">
        </div>
        <div class="mb-3">
            <label for="exampleFormControlInput2" class="form-label">Password</label>
            <input type="password" class="form-control" name="password" id="exampleFormControlInput2">
        </div>
        <button type="submit">OK</button>
    </form>
</div>
</body>
</html>

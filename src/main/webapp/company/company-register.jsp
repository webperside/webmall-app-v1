<%--
  Created by IntelliJ IDEA.
  User: hamid
  Date: 7/16/21
  Time: 7:39 PM
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
<%@ include file="../component/navbar.jsp"  %>
<div class="container">
    <form action="/company-register" method="post">
        <div class="mb-3">
            <label for="exampleFormControlInput1" class="form-label">Name your company</label>
            <input type="text" class="form-control" name="name" id="exampleFormControlInput1">
        </div>
        <div class="mb-3">
            <label for="exampleFormControlInput2" class="form-label">Description about your company</label>
            <input type="text" class="form-control" name="desc" id="exampleFormControlInput2">
        </div>
        <input hidden type="text" class="form-control" name="userId" value="<%=request.getAttribute("userId")%>">
        <button type="submit">OK</button>
    </form>
</div>

</body>
</html>

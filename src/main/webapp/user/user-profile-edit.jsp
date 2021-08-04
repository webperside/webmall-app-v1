<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="../component/navbar.jsp" %>
<div class="container">
    <%
        Integer userProfileId = (Integer) request.getAttribute("userProfileId");
    %>
    <form action="/user-profile-edit" method="post">
        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" class="form-control" name="name" id="name">
        </div>
        <div class="mb-3">
            <label for="surname" class="form-label">Surname</label>
            <input type="text" class="form-control" name="surname" id="surname">
        </div>
        <div class="mb-3">
            <label for="birthdate" class="form-label">Birthdate</label>
            <input type="datetime-local" class="form-control" name="birthdate" id="birthdate">
        </div>
        <div class="mb-3">
            <label for="avatar" class="form-label">Avatar</label>
            <input type="image" class="form-control" name="avatar" id="avatar">
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
        <input hidden type="text" class="form-control" name="userProfileId" value="<%=request.getAttribute("userProfileId")%>">
        <button type="submit">Edit</button>
    </form>
</div>

</body>
</html>
<%@ page import="com.webperside.webmallappv1.dto.user.UserProfileDto" %>
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
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"
        integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w=="
        crossorigin="anonymous"/>
</head>
<body>
<%@ include file="../component/navbar.jsp" %>
<div class="container">

  <%
    UserProfileDto userProfile = (UserProfileDto) request.getAttribute("userProfile");
  %>

  <c:choose>
    <c:when test="${code != null && (code == -1 || code == 0)}">
      <div class="alert alert-danger" role="alert">
          ${msg}
      </div>
    </c:when>
    <c:otherwise>
      <%
        String msg = (String) request.getAttribute("msg");
        if (msg != null) {
      %>
      <div class="alert alert-success" role="alert">
          ${msg}
      </div>
      <%
        }
      %>
    </c:otherwise>
  </c:choose>
  <div class="d-flex justify-content-center">
    <div class="card" style="width: 18rem;">
      <img src="https://picsum.photos/200" class="card-img-top" alt="...">
      <div class="card-body">
        <h5 class="card-title"><%=userProfile.getFullName()%></h5>
        <p class="card-text"><%=userProfile.getGender()%> | <%=userProfile.getBirthDate() != null ? userProfile.getBirthDate() : "N/A"%></p>

        <c:forEach items="${userProfile.getContacts()}" var="userContact">
          <c:if test="${userContact.getContact() != null}" >

            <p><i class="${userContact.getContactType().getIcon()}"></i>${userContact.getContact()}</p>
          </c:if>
        </c:forEach>

        <%
          if(userProfile.isMe()){
        %>
        <a href="/user-profile-edit" class="btn btn-primary">Edit profile</a>
        <%
          }
        %>
      </div>
    </div>

  </div>


</div>

</body>
</html>

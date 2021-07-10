<%@ page import="com.webperside.webmallappv1.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<%
    Object obj =request.getSession().getAttribute("loggedUser");
    User u = null;
    if(obj instanceof User) {
        u = (User) obj;
%>
        <p>Hello <%=u.getUsername()%></p>
<%
    } else {
%>
        <p>Hello, Guest></p>
<%
    }
%>

</body>
</html>
<%@ page import="com.webperside.webmallappv1.model.User" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Link</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Dropdown
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">Action</a></li>
                        <li><a class="dropdown-item" href="#">Another action</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#">Something else here</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                </li>
            </ul>
<%--            <form class="d-flex">--%>
<%--                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">--%>
<%--                <button class="btn btn-outline-success" type="submit">Search</button>--%>
<%--            </form>--%>
            <%
                Object obj =request.getSession().getAttribute("loggedUser");
                if(obj != null){
                    User user = (User) obj;
            %>
            <div class="btn-group">
                <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                    (<%=user.getUsername()%>)
                </button>
                <ul class="dropdown-menu dropdown-menu-start">
                    <li><button class="dropdown-item" type="button">Action</button></li>
                    <li><button class="dropdown-item" type="button">Another action</button></li>
                    <li>
                        <a class="dropdown-item btn btn-outline-success" href="/logout" role="button">Logout</a>
                    </li>
                </ul>
            </div>
            <%
                } else {
            %>
            <a class="btn btn-outline-success" href="/login" role="button">Login</a>
            <a class="btn btn-outline-success" href="/user-register" role="button">Register</a>
            <%
                }
            %>
        </div>
    </div>
</nav>
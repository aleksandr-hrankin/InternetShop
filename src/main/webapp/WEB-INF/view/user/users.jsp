<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <title>All users</title>
</head>
<body>
<div class="wrapper">
    <%@include file="../header.jsp"%>

    <div class="content">
        <h2>Users</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">id</th>
                <th scope="col">name</th>
                <th scope="col">login</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <th scope="row">${user.id}</th>
                    <td>${user.name}</td>
                    <td>${user.login}</td>
                    <td><a href="${pageContext.request.contextPath}/users/delete?userId=${user.id}">delete</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <%@include file="../footer.jsp"%>
</div>
</body>
</html>

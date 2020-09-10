<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
<h2>Users</h2>
<a href="${pageContext.request.contextPath}/">Main page</a>
<table border="1">
    <tr>
        <th>id</th>
        <th>name</th>
        <th></th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.name}"/></td>
            <td><a href="${pageContext.request.contextPath}/users/delete?userId=${user.id}">delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

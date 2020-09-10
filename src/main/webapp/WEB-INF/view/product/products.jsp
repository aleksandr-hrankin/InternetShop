<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<h2>Products</h2>
<a href="${pageContext.request.contextPath}/">Main page</a>
<table border="1">
    <tr>
        <th>id</th>
        <th>name</th>
        <th>price</th>
        <th></th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.price}</td>
            <td>
                <a href="${pageContext.request.contextPath}/shopping-carts/products/add?productId=${product.id}">buy</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

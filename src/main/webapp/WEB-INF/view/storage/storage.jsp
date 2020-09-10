<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Storage</title>
</head>
<body>
<h2>Storage</h2>
<a href="${pageContext.request.contextPath}/">Main page</a>
<p>Add new product:</p>
<form action="${pageContext.request.contextPath}/storage/products/add" method="post">
    <label>
        name: <input type="text" name="productName">
    </label>
    <label>
        price: <input type="text" name="productPrice">
    </label>
    <button type="submit">add</button>
</form>
<hr>
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
                <a href="${pageContext.request.contextPath}/storage/products/delete?productId=${product.id}">delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

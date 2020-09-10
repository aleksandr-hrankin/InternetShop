<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
<h2>Your Shopping Cart</h2>
<a href="${pageContext.request.contextPath}/">Main page</a>
<table border="1">
    <tr>
        <td>id</td>
        <td>name</td>
        <td>price</td>
        <td></td>
    </tr>
    <c:forEach var="product" items="${shoppingCart.products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.price}</td>
            <td>
                <a href="${pageContext.request.contextPath}/shopping-cart/products/delete?productId=${product.id}">delete</a>
            </td>
        </tr>
    </c:forEach>
    </table>
<form action="${pageContext.request.contextPath}/shopping-cart/complete-order?cartId=${shoppingCart.id}" method="post">
    <input type="submit" value="checkout">
</form>
</body>
</html>

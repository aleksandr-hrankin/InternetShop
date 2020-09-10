<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Internet Shop</title>
</head>
<body>
    <h2>Main page</h2>
    <p>admin panel</p>
    <a href="${pageContext.request.contextPath}/inject-data">Inject test data into the DB</a>
    <br>
    <a href="${pageContext.request.contextPath}/users/all">All users</a>
    <br>
    <a href="${pageContext.request.contextPath}/storage/products">Storage</a>
    <br>
    <a href="${pageContext.request.contextPath}/orders/all">Order panel</a>
    <hr>
    <p>user panel</p>
    <a href="${pageContext.request.contextPath}/registration">Registration</a>
    <br>
    <a href="${pageContext.request.contextPath}/products/all">Product catalog</a>
    <br>
    <a href="${pageContext.request.contextPath}/shopping-carts/products">Shopping Cart</a>
    <br>
    <a href="${pageContext.request.contextPath}/user/orders">Orders</a>
</body>
</html>

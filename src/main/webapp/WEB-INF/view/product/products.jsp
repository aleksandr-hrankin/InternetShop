<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file="../../../resources/css/product-style.css" %>

</style>
<html>
<head>
    <title>Products</title>
</head>
<body>
<%@include file="../header.jsp" %>
<div class="container">
    <h1 class="text-center">Products</h1>
    <div class="row">
        <c:forEach var="product" items="${products}">
            <div class="col-md-4 product-gird">
                <div class="image">
                    <a href="#">
                        <img class="w-100" src="${pageContext.request.contextPath}/resources/img/empty-box.png"
                             alt="product">
                        <div class="overlay">
                            <div class="detail">View Details</div>
                        </div>
                    </a>
                </div>
                <h5 class="text-center">${product.name}</h5>
                <h5 class="text-center">Price: $${product.price}</h5>
                <a href="${pageContext.request.contextPath}/shopping-carts/products/add?productId=${product.id}" class="btn btn-outline-dark buy">BUY</a>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>

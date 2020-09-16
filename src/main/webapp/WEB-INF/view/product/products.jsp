<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/products-style.css">
    <title>Products</title>
</head>
<body>
<div class="wrapper">
    <%@include file="../header.jsp"%>
    <div class="content">
        <div class="container">
            <h1 class="text-center">Products ${not empty products ? "" : "is empty"}</h1>
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
                        <a href="${pageContext.request.contextPath}/shopping-carts/products/add?productId=${product.id}" class="btn btn-outline-dark button">BUY</a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <%@include file="../footer.jsp"%>
</div>
</body>
</html>

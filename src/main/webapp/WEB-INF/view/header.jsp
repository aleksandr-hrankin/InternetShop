<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style><%@include file="../../resources/css/style.css"%></style>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <title></title>
</head>
<body>
<nav class="navbar navbar-expand-lg bh-white">
    <a class="navbar-brand pl-5" href="${pageContext.request.contextPath}/"><i class="fas fa-box"></i> Shop</a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
                <a class="nav-link pr-5" href="${pageContext.request.contextPath}/">
                    <i class="fas fa-home"></i> Home
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link pr-5" href="${pageContext.request.contextPath}/products/all">
                    <i class="fas fa-carrot"></i> Products
                </a>
            </li>
            <li class="nav-item active">
                <a class="nav-link pr-5" href="${pageContext.request.contextPath}/shopping-carts/products">
                    <i class="fas fa-shopping-cart"></i> Cart
                </a>
            </li>
            <li class="nav-item active">
                <a class="nav-link pr-5" href="${pageContext.request.contextPath}/user/orders">
                    <i class="fas fa-box"></i> Orders
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link pr-5" href="${pageContext.request.contextPath}/login">
                    <i class="fas fa-sign-in-alt"></i> Sign in
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link pr-5" href="${pageContext.request.contextPath}/registration">
                    <i class="fas fa-user-plus"></i> Sign up
                </a>
            </li>
        </ul>
    </div>
</nav>
<hr>
<script src="https://kit.fontawesome.com/2be77622bb.js" crossorigin="anonymous"></script>
</body>
</html>

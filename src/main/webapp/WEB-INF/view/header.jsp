<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header-style.css">
    <title></title>
</head>
<body>
<header class="page-header">
    <div class="container">
        <nav class="navbar navbar-expand-xl navbar-light bg-white">
            <a class="navbar-brand pl-5" href="${pageContext.request.contextPath}/inject-data">
                <i class="fas fa-box"> Shop</i>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link pr-4" href="${pageContext.request.contextPath}/">
                            <i class="fas fa-home"> Home</i>
                        </a>
                    </li>
                    <c:if test="${not empty sessionScope.user_id}">
                        <c:forEach var="role" items="${sessionScope.user_roles}">
                            <c:choose>
                                <c:when test="${role.roleName eq 'ADMIN'}">
                                    <li class="nav-item dropdown">
                                        <a class="nav-link pr-4" href="#" id="navbarDropdown" role="button"
                                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="fas fa-user-shield"> Admin Panel <i class="fas fa-caret-down"></i></i>
                                        </a>
                                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                            <a class="dropdown-item"
                                               href="${pageContext.request.contextPath}/admin/products">Products</a>
                                            <a class="dropdown-item"
                                               href="${pageContext.request.contextPath}/users/all">Users</a>
                                            <a class="dropdown-item"
                                               href="${pageContext.request.contextPath}/orders/all">Orders</a>
                                            <div class="dropdown-divider"></div>
                                            <a class="dropdown-item"
                                               href="${pageContext.request.contextPath}/inject-data">Inject</a>
                                        </div>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="nav-item">
                                        <a class="nav-link pr-4" href="${pageContext.request.contextPath}/shopping-carts/products">
                                            <i class="fas fa-shopping-cart"> Cart</i>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link pr-4" href="${pageContext.request.contextPath}/user/orders">
                                            <i class="fas fa-box"> Orders</i>
                                        </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link pr-4" href="${pageContext.request.contextPath}/products/all">
                            <i class="fas fa-carrot"> Products</i>
                        </a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link pr-4" href="#" id="navbarDropdownInfo" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-ellipsis-v"> More <i class="fas fa-caret-down"></i></i>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdownInfo">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/users/all">Contact</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/orders/all">About Us</a>
                        </div>
                    </li>
                    <c:choose>
                        <c:when test="${empty sessionScope.user_id}">
                            <li class="nav-item">
                                <a class="nav-link pr-4" href="${pageContext.request.contextPath}/login">
                                    <i class="fas fa-sign-in-alt"> Sign in</i>
                                </a>
                            </li>
                            <li class="nav-item pr-4">
                                <a class="nav-link" href="${pageContext.request.contextPath}/registration">
                                    <i class="fas fa-user-plus"> Sign up</i>
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item pr-4">
                                <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                                    <i class="fas fa-sign-out-alt"> Logout</i>
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </nav>
    </div>
</header>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/2be77622bb.js" crossorigin="anonymous"></script>
</body>
</html>

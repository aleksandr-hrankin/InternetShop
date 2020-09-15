<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style><%@include file="../../resources/css/style.css"%></style>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <title>Internet Shop</title>
</head>
<body>

    <%@include file="header.jsp"%>

    <p>admin panel</p>
    <a href="${pageContext.request.contextPath}/inject-data">Inject test data into the DB</a>
    <br>
    <a href="${pageContext.request.contextPath}/users/all">All users</a>
    <br>
    <a href="${pageContext.request.contextPath}/admin/products">Storage</a>
    <br>
    <a href="${pageContext.request.contextPath}/orders/all">Order panel</a>
    <hr>

   <script src="https://kit.fontawesome.com/2be77622bb.js" crossorigin="anonymous"></script>
</body>
</html>

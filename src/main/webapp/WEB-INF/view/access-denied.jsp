<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <title>Access Denied</title>
</head>
<body>
<div class="container">
    <div class="text-center pt-lg-5">
        <img width="300px" src="${pageContext.request.contextPath}/resources/img/lock-box.png" alt="lock-box">
        <p>Access to this page is denied! <a href="${pageContext.request.contextPath}/">Go Home</a></p>
    </div>
</div>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <title>Authorization</title>
</head>
<body>
<div style="padding: 30px 10%">
    <div class="container">
        <div class="row">
            <div class="col-md-4 offset-md-4">
                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/">
                        <h2><i class="fas fa-box"></i></h2>
                    </a>
                </div>
                <div class="text-center">
                    <h1>Sign in</h1>
                </div>
                <c:if test="${not empty errorMessage}">
                    <div>
                        <div class="alert alert-danger" role="alert">${errorMessage}</div>
                    </div>
                </c:if>

                <div class="panel panel-default">
                    <div class="panel-body">
                        <form action="${pageContext.request.contextPath}/login" method="post">
                            <div class="form-group">
                                <label for="exampleInputLogin1">Login</label>
                                <input type="text" class="form-control" id="exampleInputLogin1" name="login" minlength="1">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword1">Password</label>
                                <input type="password" class="form-control" id="exampleInputPassword1" name="password"minlength="1">
                            </div>
                            <button type="submit" class="btn btn-success btn-lg btn-block">Sign in</button>
                        </form>
                    </div>
                </div>

                <div class="well well-sm">
                    <div class="text-center">
                        <a href="${pageContext.request.contextPath}/registration">Sign up</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://kit.fontawesome.com/2be77622bb.js" crossorigin="anonymous"></script>
</body>
</html>

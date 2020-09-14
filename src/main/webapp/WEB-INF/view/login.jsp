<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorization</title>
</head>
<body>
<h2>Authorization</h2>
<br>
<a href="${pageContext.request.contextPath}/registration">Registration</a>
<p>${errorMessage}</p>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label>
        login: <input type="text" name="login">
    </label>
    <label>
        password: <input type="text" name="password">
    </label>
    <button type="submit">Ok</button>
</form>
</body>
</html>

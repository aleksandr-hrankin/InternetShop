<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h2>Registration</h2>
<a href="${pageContext.request.contextPath}/">Main page</a>
<h4>${errorMessage}</h4>
<form action="${pageContext.request.contextPath}/registration" method="post">
     <label>
         name:<input type="text" name="userName">
     </label>
    <br>
    <label>
        login:<input type="text" name="login">
    </label>
    <br>
    <label>
        password:<input type="password" name="password">
    </label>
    <br>
    <label>
        repeat password<input type="password" name="repeat-password">
    </label>
    <br>
    <button type="submit">Register</button>
</form>
</body>
</html>

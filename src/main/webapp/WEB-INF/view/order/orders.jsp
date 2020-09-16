<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order panel</title>
</head>
<body>
<div class="wrapper">
    <%@include file="../header.jsp" %>

    <div class="content">
        <h2>Order panel</h2>
        <c:forEach var="order" items="${orders}">
            <p>id = ${order.id} userId = ${order.userId}</p>
            <table border="1">
                <tr>
                    <th>id</th>
                    <th>name</th>
                    <th>price</th>
                </tr>
                <c:forEach var="product" items="${order.products}">
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.price}</td>
                    </tr>
                </c:forEach>
                <a href="${pageContext.request.contextPath}/orders/delete?orderId=${order.id}">delete</a>
            </table>
        </c:forEach>
    </div>

    <%@include file="../footer.jsp" %>
</div>
</body>
</html>

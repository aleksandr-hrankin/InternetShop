<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Storage</title>
</head>
<body>
<div class="wrapper">
    <%@include file="../header.jsp"%>

    <div class="content">
        <h2>Storage</h2>
        <p>Add new product:</p>
        <form action="${pageContext.request.contextPath}/admin/products/add" method="post">
            <label>
                name: <input type="text" name="productName">
            </label>
            <label>
                price: <input type="text" name="productPrice">
            </label>
            <button type="submit">add</button>
        </form>
        <hr>
        <table border="1">
            <tr>
                <th>id</th>
                <th>name</th>
                <th>price</th>
                <th></th>
            </tr>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/products/delete?productId=${product.id}">delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <%@include file="../footer.jsp"%>
</div>
</body>
</html>

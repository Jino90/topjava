<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<jsp:useBean id="mealTos" scope="request" type="java.util.List"/>
<style>
    .green {
        color: green;
    }

    .red    {
        color: red;
    }
</style>
<table>
<%--    <colgroup>
        <col span="2" style="background:Khaki"><!-- С помощью этой конструкции задаем цвет фона для первых двух столбцов таблицы-->
        <col style="background-color:#ff564f">
        <col style="background-color:#33d1ff">
        <col style="background-color:#2cff23">
        <col style="background-color:#d94cff">
    </colgroup>--%>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
<c:forEach items="${mealTos}" var="meal">
    <c:choose>
        <c:when test="${meal.excess != true}">
    <tr>
        <td class="green">${meal.dateTime}</td>
        <td class="green">${meal.description}</td>
        <td class="green">${meal.calories}</td>
    </tr>>
        </c:when>
        <c:otherwise>
        <tr>
            <td class="red">${meal.dateTime}</td>
            <td class="red">${meal.description}</td>
            <td class="red">${meal.calories}</td>
        </tr>>
        </c:otherwise>
    </c:choose>
</c:forEach>
</table>
</body>
</html>
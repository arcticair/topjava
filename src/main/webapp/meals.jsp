<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <tr>

        <th>Meal</th>
    </tr>
<%
    List<MealTo> m  = (List<MealTo>) request.getAttribute("meallist");
    for (MealTo mt : m) {
%>
<tr>
    <td><%= mt.toString() %></td>
</tr>
<%
    }
%>
</table>
</body>
</html>
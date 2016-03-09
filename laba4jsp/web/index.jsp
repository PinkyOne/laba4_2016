<%@ page import="database.controller.DatabaseController" %>
<%@ page import="java.util.List" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%

//    coffeeJoinCountry.
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Database</title>
</head>
<body>
<table>
    <c:forEach items="${coffeeJoinCountry}" var="item">
        <tr>
            <c:forEach items="${item}" var="tmp">
                <td><c:out value="${tmp}"/></td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
</body>
</html>
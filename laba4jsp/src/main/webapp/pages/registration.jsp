<%@ page import="web.controllers.User" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    application.setAttribute("isLoggedIn", User.getInstance().getId() > 0);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" id="reg_html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=Windows-1251"/>
    <title> Юридические онлайн-консультации </title>
    <link rel="stylesheet" href="/ilona/assets/css/style.css" type="text/css" media="all">

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>

    <script src="/ilona/assets/js/check_c.js"></script>
</head>
<body>
<div id="wrapper">
    <div id="header">
        <p class="logotext"><strong> Юридические онлайн-консультации </strong><br>
            <span class="logotext2">Качественная юридическая помощь в любое время</span></p>
        <div id="nav">
            <ul>
                <li><a href="/ilona/pages/contacts.html">Контакты</a></li>
                <li><a href="/ilona/pages/comments.jsp">Отзывы</a></li>
                <li><a href="/ilona/pages/clients.html">Клиенты</a></li>
                <li><a href="/ilona/pages/uinfo.html">Информация</a></li>
                <li><a href="/ilona/pages/price.html" class="on">Цены</a></li>
                <li><a href="/ilona/pages/judicial.html">Юр. лицам</a></li>
                <li><a href="/ilona/pages/physical.html">Физ. лицам</a></li>
                <li><a href="/ilona/pages/index.html">Главная</a></li>
            </ul>
        </div>
    </div>
    <div id="content">
        <div id="left">
            <p>Полезные ссылки</p>
            <ul>
                <li><a href="http://www.consultant.ru/">Консультант плюс</a></li>
                <li><a href="http://law.edu.ru/">Федеральный правовой портал</a></li>
                <li><a href="http://sudact.ru/">Судебные и нормативные акты РФ</a></li>
                <li><a href="http://pravo.gov.ru/">Официальный интернет-портал правовой информации</a></li>
            </ul>

            <ul>
                <table id="calendars">
                    <thead>
                    <tr>
                        <td>‹
                        <td colspan="5">
                        <td>›
                    <tr>
                        <td>Пн
                        <td>Вт
                        <td>Ср
                        <td>Чт
                        <td>Пт
                        <td>Сб
                        <td>Вс
                    <tbody>
                </table>
                <script src="/ilona/assets/js/table.js"></script>
            </ul>

        </div>

        <div id="right">
            <h2>Регистрация</h2>

            <c:if test="${isLoggedIn==false}">

                <div class="registr">
                    <b>Введите</b>
                    <form action="" name="myForm" method="post" id="registrationForm">
                        <p>Имя:</p>
                        <p><input class="registration" type="text" name="name" size="20" value=""/>
                        <p>Фамилию:</p>
                        <p><input class="registration" type="text" name="surname" size="20"/>
                        <p>Электронную почту:</p>
                        <p><input class="registration" type="text" name="email" size="50">
                        <p>Логин:</p>
                        <p><input class="registration" type="text" name="login" size="20"/>
                        <p>Пароль:</p>
                        <p><input class="registration" type="password" name="password" size="20"/>

                        <p>
                            <button class="registration" name="action" value="reg">Зарегистрироваться</button>
                            <button name="action" value="clean">Очистить</button>
                    </form>
                </div>
            </c:if>
            <c:if test="${isLoggedIn==true}">
                <div class="registr">
                    <b>Вы уже залогинены</b>
                </div>
            </c:if>
            <br class="clear">
            <div class="bottom">
                <p>Designed by Ilona Kharkovskaya. All content copyright &copy; 2015, all rights reserved. Layout
                    created at <a href="http://cssweblayouts.com" target="_blank">CSSWebLayouts.</a><br/>

            </div>
        </div>
        <div id="footer"><img src="/ilona/assets/images/footer.gif"></div>
    </div>
</div>
</body>


</html>
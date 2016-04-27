<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    //application.setAttribute("isLoggedIn", User.getInstance().isLoggedIn());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" id="com_html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=Windows-1251"/>
    <title> Юридические онлайн-консультации </title>
    <link rel="stylesheet" href="/assets/css/style.css" type="text/css" media="all">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.5/handlebars.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
    <script src="/assets/js/check_c.js"></script>
</head>
<body>
<div id="wrapper">
    <div id="header">
        <p class="logotext"><strong> Юридические онлайн-консультации </strong><br>
            <span class="logotext2">Качественная юридическая помощь в любое время</span></p>
        <div id="nav">
            <ul>
                <li><a href="/pages/contacts.html">Контакты</a></li>
                <li><a href="/pages/comments.jsp">Отзывы</a></li>
                <li><a href="/pages/clients.html">Клиенты</a></li>
                <li><a href="/pages/uinfo.html">Информация</a></li>
                <li><a href="/pages/price.html" class="on">Цены</a></li>
                <li><a href="/pages/judicial.html">Юр. лицам</a></li>
                <li><a href="/pages/physical.html">Физ. лицам</a></li>
                <li><a href="/pages/index.html">Главная</a></li>
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
                <li>
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
                </li>
                <script src="/assets/js/table.js"></script>
            </ul>

        </div>

        <div id="right">
            <h2>Отзывы</h2>
            <hr/>
            <div id="posts">

            </div>
            <form action="" name="myForm" method="post" id="commentPost">
                <textarea rows="10" cols="50" class="comment" name="text">Текст комментария</textarea>
                <br/>
                <button class="comment" name="action" value="comment">Отправить</button>
            </form>
        </div>

        <div class="welcome" id="login_form">
            <c:if test="${isLoggedIn==false||isLoggedIn==null}">

                <p>Войдите чтобы оставить комментарий</p>
                <hr/>
                <strong>Заполните поля:</strong>
                <form action="" name="myForm" method="post" id="loginForm">

                    <input class="login" type="text" name="username" value="Ваше имя"/>
                    <input class="login" type="password" name="password" value="password"/>
                    <br/>
                    <button class="login" name="action" value="login">Войти</button>
                    <br/>
                    <small>
                        <a href="">Забыли пароль?</a> | <a href="/pages/registration.jsp">Зарегистрироваться</a>
                    </small>
                </form>
            </c:if>
            <c:if test="${isLoggedIn==true}">
                <hr/>
                <strong>Вы уже залогинены</strong>
                <form action="/org" name="myForm" method="post" id="logoutForm">
                    <button name="action" value="logout">Выйти</button>
                </form>
            </c:if>
        </div>

        <br class="clear">
        <div class="bottom">
            <p>Designed by Ilona Kharkovskaya. All content copyright &copy; 2015, all rights reserved. Layout created at
                <a href="http://cssweblayouts.com" target="_blank">CSSWebLayouts.</a><br/>

        </div>
    </div>
    <div id="footer"><img src="/assets/images/footer.gif"></div>
</div>
</body>


<script id="posts-template" type="text/x-handlebars-template">
    {{#each data}}
    <span class="postinfo"> Posted by {{name}} {{surname}} on {{date}} <br/>
        <p>{{text}}</p>
         {{~#if test}}
            <p><a href="{{deleteLink}}">Удалить комментарий</a></p>
            {{~^~}}
         {{~/if~}}
        <hr/>
    </span>
    {{/each}}
</script>

</html>
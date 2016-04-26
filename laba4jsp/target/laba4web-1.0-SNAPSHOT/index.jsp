<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String coffeeName = "name";
    String countryName = "name";
    int tax = 0;

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.5/handlebars.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
<script src="scripts/main.js" charset="utf-8"></script>


<style>
    ul, li {
        display: inline-block;
    }
</style>


<head>
    <title>The Coffee Shop</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" charset="utf-8"/>
    <link href="default.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="wrapper">
    <div id="menu">
        <ul>
            <li><a href="#" accesskey="1">Home</a></li>
            <li class="active"><a href="#" accesskey="2">Photos</a></li>
            <li><a href="#" accesskey="3">Links</a></li>
            <li><a href="#" accesskey="4">About</a></li>
            <li><a href="#" accesskey="5">Contact</a></li>
        </ul>
    </div>
    <!-- end #menu -->
    <div id="header">
        <h1>The Coffee Shop</h1>
    </div>
    <!-- end #header -->
    <div id="content">
        <div id="posts">
            <div class="post">

                <ul>
                    <li>
                        <h2 class="title">Welcome to The Coffee Shop!</h2>
                    </li>
                    <li>
                        <table border="3" id="coffeeJoinCountryTable">
                            <thead>
                            <tr>
                                <th>Наименование</th>
                                <th>Страна производства</th>
                                <th>Купаж</th>
                                <th>Арабика</th>
                                <th>Робуста</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${coffeeJoinCountry}" var="item">
                                <tr class="coffee-table-content">
                                    <td>${item.get(1)}</td>
                                    <td>${item.get(0)}</td>
                                    <td>${item.get(4)}</td>
                                    <td>${item.get(2)}</td>
                                    <td>${item.get(3)}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </li>
                    <li>
                        <form action="" method="post" id="addCoffeeForm">
                            <div>
                                <p>
                                    <input class="addCoffeeAction" type="text" name="coffeeName"
                                           value="<%= coffeeName %>"/>
                                </p>
                                <p>
                                    <select class="addCoffeeAction" name="country_name" id="countryNameSelect">
                                        <c:forEach items="${country}" var="item">
                                            <option><c:out value="${item.get(0)}"/></option>
                                        </c:forEach>
                                    </select>
                                </p>
                                <p>
                                    <select class="addCoffeeAction" name="coupage_name" id="coupageNameSelect">
                                        <c:forEach items="${coupage}" var="item">
                                            <option><c:out value="${item.get(0)}"/></option>
                                        </c:forEach>
                                    </select>
                                </p>
                                <p>
                                    <button class="addCoffeeAction" name="action" value="addToCoffee">
                                        Добавить запись
                                    </button>
                                </p>
                            </div>
                        </form>
                    </li>
                    <li>
                        <form action="" method="post" id="deleteCoffeeForm">
                            <div>
                                <p>
                                    <select class="deleteCoffeeAction" name="coffee_name" id="deleteCoffeeSelect">
                                        <c:forEach items="${coffee}" var="item">
                                            <option><c:out value="${item.get(1)}"/></option>
                                        </c:forEach>
                                    </select>
                                </p>
                                <p>
                                    <button class="deleteCoffeeAction" name="action" value="deleteCoffee">
                                        Удалить запись
                                    </button>
                                </p>
                            </div>
                        </form>
                    </li>
                    <li>
                        <form action="" method="post" id="sort">
                            <div>
                                <p>
                                    <select class="updateSort" name="sortType" id="sortSelect">
                                        <option>A-Z name</option>
                                        <option>Z-A name</option>
                                        <option>A-Z country</option>
                                        <option>Z-A country</option>
                                        <option>arabica increase</option>
                                        <option>arabica decrease</option>
                                    </select>
                                </p>
                                <p>
                                    <button class="updateSort" name="action" value="updateSort">
                                        Применить
                                    </button>
                                </p>
                            </div>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
        <!-- end #posts -->
        <div id="links">
            <ul>
                <li>
                    <h2>Archives</h2>
                    <ul>
                        <li><a href="#">February 2007</a> <i>(22)</i></li>
                        <li><a href="#">January 2007</a> <i>(31)</i></li>
                        <li><a href="#">December 2006</a> <i>(31)</i></li>
                        <li><a href="#">November 2006</a> <i>(30)</i></li>
                        <li><a href="#">October 2006</a> <i>(31)</i></li>
                    </ul>
                </li>
                <li>
                    <h2>Categories</h2>
                    <ul>
                        <li><a href="#">Donec Dictum Metus</a></li>
                        <li><a href="#">Etiam Rhoncus Volutpat</a></li>
                        <li><a href="#">Integer Gravida Nibh</a></li>
                        <li><a href="#">Maecenas Luctus Lectus</a></li>
                        <li><a href="#">Mauris Vulputate Dolor Nibh</a></li>
                        <li><a href="#">Nulla Luctus Eleifend</a></li>
                        <li><a href="#">Posuere Augue Sit Nisl</a></li>
                    </ul>
                </li>
                <li>
                    <h2>Blog Roll</h2>
                    <ul>
                        <li><a href="#">Donec Dictum Metus</a></li>
                        <li><a href="#">Etiam Rhoncus Volutpat</a></li>
                        <li><a href="#">Integer Gravida Nibh</a></li>
                        <li><a href="#">Maecenas Luctus Lectus</a></li>
                        <li><a href="#">Mauris Vulputate Dolor Nibh</a></li>
                        <li><a href="#">Nulla Luctus Eleifend</a></li>
                        <li><a href="#">Posuere Augue Sit Nisl</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- end #links -->
        <div style="clear: both;">&nbsp;</div>
    </div>
</div>
<!-- end #content -->
<div id="footer">
    <p id="legal">Copyright &copy; Elistratov 2016</p>
    <p id="brand">The Coffee Shop</p>
</div>
<!-- end #footer -->

<script id="coffee-table-template" type="text/x-handlebars-template">
    {{#each data}}
    <tr>
        <td>{{name}}</td>
        <td>{{country}}</td>
        <td>{{coupage}}</td>
        <td>{{arabica}}</td>
        <td>{{robusta}}</td>
    </tr>
    {{/each}}
</script>
<script id="select-template" type="text/x-handlebars-template">
    {{#each data}}
    <option>{{name}}</option>
    {{/each}}
</script>
</body>
</html>

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
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('#addCountryForm').submit(function () {
            var ajax_data = {};
            $('.countryAddInput').each(function () {
                ajax_data[$(this).attr('name')] = $(this).val();
            });
            $.ajax({
                type: "post",
                url: "/db", //this is my servlet
                data: ajax_data,
                success: function (response) {
                    $.ajax({
                        type: "get",
                        url: "/country", //this is my servlet
                        success: function (data) {
                            refreshCountry(data)
                        }
                    })
                    $.ajax({
                        type: "get",
                        url: "/country", //this is my servlet
                        success: function (data) {
                            refresh_CountryDeleteSelect(data)
                        }
                    })
                    $.ajax({
                        type: "get",
                        url: "/country", //this is my servlet
                        success: function (data) {
                            refresh_CountryNameSelect(data)
                        }
                    })
                }
            });
            return false;
        });
    });
    function refreshCountry(data) {
        var tbl_body = "";
        $.each(data, function () {
            var tbl_row = "";
            var id = "";
            var tax = "";
            var name_c = "";
            $.each(this, function (k, v) {
                switch (k) {
                    case "name":
                    {
                        name_c = "<td>" + v + "</td>";
                    }
                        break;
                    case "id":
                    {
                        id = "<td>" + v + "</td>";
                    }
                        break;
                    case "tax":
                    {
                        tax = "<td>" + v + "</td>";
                    }
                        break;
                }

            })
            tbl_row = id + name_c + tax;
            tbl_body += "<tr>" + tbl_row + "</tr>";
        });
        $('table#countryTable tbody')
        // Append the new rows to the body
                .html(tbl_body)
                // Call the refresh method
                .closest("table#countryTable")
                .table("refresh")
                // Trigger if the new injected markup contain links or buttons that need to be enhanced
                .trigger("create")
    }
    function refresh_CountryNameSelect(data) {
        var delete_s_country = "";
        $.each(data, function () {
            var delete_s_option = "";
            $.each(this, function (k, v) {
                switch (k) {
                    case "name":
                    {
                        delete_s_option = "<option>" + v + "</option>";
                    }
                        break;
                }

            })
            delete_s_country += delete_s_option;
        });
        $('select#countryNameSelect')
        // Append the new rows to the body
                .html(delete_s_country)
                // Call the refresh method
                .closest("select#countryNameSelect")
                .selectmenu("refresh")
                .trigger("create")
    }
    function refresh_CountryDeleteSelect(data) {
        var delete_s_country = "";
        $.each(data, function () {
            var delete_s_option = "";
            $.each(this, function (k, v) {
                switch (k) {
                    case "id":
                    {
                        delete_s_option = "<option>" + v + "</option>";
                    }
                        break;
                }

            })
            delete_s_country += delete_s_option;
        })
        alert(delete_s_country);
        $('select#deleteCountrySelect')
        // Append the new rows to the body
                .html(delete_s_country)
                // Call the refresh method
                .closest("select#deleteCountrySelect")
                .selectmenu("refresh")
                .trigger("create")
    }
    $(document).ready(function () {
        $('#deleteCountryForm').submit(function () {
            var ajax_data = {};
            $('.countryDeleteInput').each(function () {
                ajax_data[$(this).attr('name')] = $(this).val();
            });
            $.ajax({
                type: "post",
                url: "/db", //this is my servlet
                data: ajax_data,
                success: function (response) {
                    $.ajax({
                        type: "get",
                        url: "/country", //this is my servlet
                        success: function (data) {
                            refreshCountry(data)
                        }
                    })
                    $.ajax({
                        type: "get",
                        url: "/country", //this is my servlet
                        success: function (data) {
                            refresh_CountryDeleteSelect(data)
                        }
                    })
                    $.ajax({
                        type: "get",
                        url: "/country", //this is my servlet
                        success: function (data) {
                            refresh_CountryNameSelect(data)
                        }
                    })
                    $.ajax({
                        type: "get",
                        url: "/coffee", //this is my servlet
                        success: function (data) {
                            refreshCoffeeTable(data);
                        }
                    })
                    $.ajax({
                        type: "get",
                        url: "/coffeeJoin", //this is my servlet
                        success: function (data) {
                            refreshCoffeeJoinTable(data);
                        }
                    })
                }
            });
            return false;
        });
    });
</script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#addCoffeeForm').submit(function () {
            var ajax_data = {};
            $('.addCoffeeAction').each(function () {
                ajax_data[$(this).attr('name')] = $(this).val();
            });
            $.ajax({
                type: "post",
                url: "/db", //this is my servlet
                data: ajax_data,
                success: function (response) {
                    $.ajax({
                        type: "get",
                        url: "/coffee", //this is my servlet
                        success: function (data) {
                            refreshCoffeeTable(data);
                        }
                    })
                    $.ajax({
                        type: "get",
                        url: "/coffee", //this is my servlet
                        success: function (data) {
                            refresh_deleteCoffeeSelect(data);
                        }
                    })

                    $.ajax({
                        type: "get",
                        url: "/coffeeJoin", //this is my servlet
                        success: function (data) {
                            refreshCoffeeJoinTable(data);
                        }
                    })
                }
            });
            return false;
        });
    });
    function refreshCoffeeTable(data) {
        var tbl_body = "";
        var select_coffee = "";
        $.each(data, function () {
            var tbl_row = "";
            var id = "";
            var country_id = "";
            var name_c = "";
            var select_opt = "";
            $.each(this, function (k, v) {
                switch (k) {
                    case "name":
                    {
                        name_c = "<td>" + v + "</td>";
                    }
                        break;
                    case "id":
                    {
                        select_opt = "<option>" + v + "</option>";
                        id = "<td>" + v + "</td>";
                    }
                        break;
                    case "country_id":
                    {
                        country_id = "<td>" + v + "</td>";
                    }
                        break;
                }

            })
            tbl_row = id + name_c + country_id;
            tbl_body += "<tr>" + tbl_row + "</tr>";
        });
        $('table#coffeeTable tbody')
        // Append the new rows to the body
                .html(tbl_body)
                // Call the refresh method
                .closest("table#coffeeTable")
                .table("refresh")
                // Trigger if the new injected markup contain links or buttons that need to be enhanced
                .trigger("create");
    }
    function refresh_deleteCoffeeSelect(data) {
        var select_coffee = "";
        $.each(data, function () {
            var select_opt = "";
            $.each(this, function (k, v) {
                switch (k) {
                    case "id":
                    {
                        select_opt = "<option>" + v + "</option>";
                    }
                        break;
                }
            })
            select_coffee += select_opt;
        });
        $('select#deleteCoffeeSelect')
        // Append the new rows to the body
                .html(select_coffee)
                // Call the refresh method
                .closest("select#deleteCoffeeSelect")
                .selectmenu("refresh")
                .trigger("create");
    }
    function refreshCoffeeJoinTable(data) {
        var tbl_body = "";
        $.each(data, function () {
            var tbl_row = "";
            var name = "";
            var tax = "";
            var country = "";
            $.each(this, function (k, v) {
                switch (k) {
                    case "name":
                    {
                        name = "<td>" + v + "</td>";
                    }
                        break;
                    case "tax":
                    {
                        tax = "<td>" + v + "</td>";
                    }
                        break;
                    case "country":
                    {
                        country = "<td>" + v + "</td>";
                    }
                        break;
                }

            })
            tbl_row = name + country + tax;
            tbl_body += "<tr>" + tbl_row + "</tr>";
        });
        $('table#coffeeJoinCountryTable tbody')
        // Append the new rows to the body
                .html(tbl_body)
                // Call the refresh method
                .closest("table#coffeeJoinCountryTable")
                .table("refresh")
                // Trigger if the new injected markup contain links or buttons that need to be enhanced
                .trigger("create");
    }
    $(document).ready(function () {
        $('#deleteCoffeeForm').submit(function () {
            var ajax_data = {};
            $('.deleteCoffeeAction').each(function () {
                ajax_data[$(this).attr('name')] = $(this).val();
            });
            $.ajax({
                type: "post",
                url: "/db", //this is my servlet
                data: ajax_data,
                success: function (response) {
                    $.ajax({
                        type: "get",
                        url: "/coffee", //this is my servlet
                        success: function (data) {
                            refreshCoffeeTable(data);
                        }
                    })
                    $.ajax({
                        type: "get",
                        url: "/coffee", //this is my servlet
                        success: function (data) {
                            refresh_deleteCoffeeSelect(data);
                        }
                    })
                    $.ajax({
                        type: "get",
                        url: "/coffeeJoin", //this is my servlet
                        success: function (data) {
                            refreshCoffeeJoinTable(data);
                        }
                    })
                }
            });
            return false;
        });
    });
</script>
<style>
    ul, li {
        display: inline-block;
    }
</style>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Database</title>
</head>
<body>
<ul id="menu">
    <li>
        <table border="3" id="coffeeJoinCountryTable">
            <thead>
            <tr>
                <th>Coffee</th>
                <th>Country</th>
                <th>Tax</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${coffeeJoinCountry}" var="item">
                <tr>
                    <td>${item.get(1)}</td>
                    <td>${item.get(0)}</td>
                    <td>${item.get(2)}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </li>
    <li>
        <table border="2" id="coffeeTable">
            <thead>
            <tr>
                <th>id</th>
                <th>Name</th>
                <th>country_id</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${coffee}" var="item">
                <tr>
                    <td>${item.get(1)}</td>
                    <td>${item.get(0)}</td>
                    <td>${item.get(2)}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </li>
    <li>
        <table border="2" id="countryTable">
            <thead>
            <tr>
                <th>id</th>
                <th>Name</th>
                <th>Tax</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${country}" var="item">
                <tr>
                    <td>${item.get(2)}</td>
                    <td>${item.get(0)}</td>
                    <td>${item.get(1)}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </li>
</ul>
<form action="" method="post" id="addCoffeeForm">
    <div>
        <input class="addCoffeeAction" type="text" name="coffeeName" value="<%= coffeeName %>"/>
        <br/>
        <select class="addCoffeeAction" name="country_name" id="countryNameSelect">
            <c:forEach items="${country}" var="item">
                <option><c:out value="${item.get(0)}"/></option>
            </c:forEach>
        </select>
        <br/>
        <button class="addCoffeeAction" name="action" value="addToCoffee">Добавить запись</button>
    </div>
</form>
<form action="" method="post" id="deleteCoffeeForm">
    <div>
        <br/>
        <br/>
        <select class="deleteCoffeeAction" name="coffee_id" id="deleteCoffeeSelect">
            <c:forEach items="${coffee}" var="item">
                <option><c:out value="${item.get(1)}"/></option>
            </c:forEach>
        </select>
        <br/>
        <button class="deleteCoffeeAction" name="action" value="deleteCoffee">Удалить запись</button>
    </div>
</form>
<form action="" id="addCountryForm">
    <div>
        <input class="countryAddInput" type="text" name="countryName" value="<%= countryName %>"/>
        <br/>
        <input class="countryAddInput" type="text" name="tax" value="<%= tax %>"/>
        <br/>
        <button class="countryAddInput" name="action" value="addToCountry">Добавить запись</button>
        <br/>
    </div>
</form>
<form action="" id="deleteCountryForm">
    <br/>
    <select class="countryDeleteInput" name="country_id" id="deleteCountrySelect">
        <c:forEach items="${country}" var="item">
            <option><c:out value="${item.get(2)}"/></option>
        </c:forEach>
    </select>
    <br/>
    <button class="countryDeleteInput" name="action" value="deleteCountry">Удалить запись</button>
</form>
</body>
</html>
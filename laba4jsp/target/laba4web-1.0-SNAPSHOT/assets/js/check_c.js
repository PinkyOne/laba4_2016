$(document).ready(function () {
    function check() {
        var x = document.forms["myForm"]["username"].value;
        var p = document.forms["myForm"]["password"].value;
        var y = document.forms["myForm"]["names"].value;
        var z = document.forms["myForm"]["surname"].value;
        var email = document.forms["myForm"]["email"].value;
        if (x == null || x == "" || p == null || p == "" || y == null || y == "" || z == null || z == "") {
            alert("Ошибка! Все поля обязательны для заполнения!");
            return false;
        }

        if (p.length < 6) {
            alert("Допустимый пароль от 6 символов!");
            return false;
        }
        /*if(p.contains){alert("Пароль должен быть составлен не только из букв!");
         return false;}*/
        email = email.replace(/^\s+|\s+$/g, '');
        if ((/^([a-z0-9_\-]+\.)*[a-z0-9_\-]+@([a-z0-9][a-z0-9\-]*[a-z0-9]\.)+[a-z]{2,4}$/i).test(email) == false) {
            alert("Не корректный адрес e-mail");
            return false;
        }
    }

    function createRequest(options) {
        $.ajax(options);
    }

    $('#registrationForm').submit(function () {
        check();
        var ajax_data = {};
        $('.registration').each(function () {
            ajax_data[$(this).attr('name')] = $(this).val();
        });

        // Refresh data

        var options = {
            type: "post",
            url: "/org", //this is my servlet
            data: ajax_data,
            success: function (response) {
                $('#reg_html').html(response);
            }
        };

        createRequest(options);

        return false;
    });

    $('#loginForm').submit(function () {
        var ajax_data = {};
        $('.login').each(function () {
            ajax_data[$(this).attr('name')] = $(this).val();
        });

        // Refresh data

        var options = {
            type: "post",
            url: "/org", //this is my servlet
            data: ajax_data,
            success: function (response) {
                $('#com_html').html(response)
            }
        };

        createRequest(options);

        return false;
    });
    function refreshComments(data) {
        var source = $('#posts-template').html();
        var template = Handlebars.compile(source);

        var html = template(data);
        $('#posts').html(html);
    }

    function getComments() {
        var options = {
            type: "get",
            url: "/comments", //this is my servlet
            success: function (response) {
                refreshComments(response)
            }
        };

        createRequest(options);
    }

    $('#commentPost').submit(function () {
        var ajax_data = {};
        $('.comment').each(function () {
            ajax_data[$(this).attr('name')] = $(this).val();
        });

        // Refresh data

        var options = {
            type: "post",
            url: "/org", //this is my servlet
            data: ajax_data,
            success: function (response) {
                getComments();
            }
        };

        createRequest(options);

        return false;
    });
});
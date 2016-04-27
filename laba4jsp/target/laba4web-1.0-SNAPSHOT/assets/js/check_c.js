$(document).ready(function () {
    function check() {
        var x = document.forms["myForm"]["login"].value;
        var p = document.forms["myForm"]["password"].value;
        var y = document.forms["myForm"]["name"].value;
        var z = document.forms["myForm"]["surname"].value;
        var email = document.forms["myForm"]["email"].value;
        if (x == null || x == "" || p == null || p == "" || y == null || y == "" || z == null || z == "") {
            alert("������! ��� ���� ����������� ��� ����������!");
            return false;
        }

        if (p.length < 6) {
            alert("���������� ������ �� 6 ��������!");
            return false;
        }
        /*if(p.contains){alert("������ ������ ���� ��������� �� ������ �� ����!");
         return false;}*/
        email = email.replace(/^\s+|\s+$/g, '');
        if ((/^([a-z0-9_\-]+\.)*[a-z0-9_\-]+@([a-z0-9][a-z0-9\-]*[a-z0-9]\.)+[a-z]{2,4}$/i).test(email) == false) {
            alert("�� ���������� ����� e-mail");
            return false;
        }
    }

    function createRequest(options) {
        $.ajax(options);
    }

    $(window).load(function () {
        getComments();
    });
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
                var success = $($(response)).filter("#wrapper").find('#right');
                console.log(success);
                $('#reg_html').find('#right').html(success);
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
                var success = $($(response)).filter("#wrapper").find('#login_form');
                console.log(success);
                $('#com_html').find('#login_form').html(success);
                getComments();
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
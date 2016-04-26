$(document).ready(function () {

    function createRequest(options) {
        $.ajax(options);
    }

    function onDbRequestSuccess(resp) {
        var refreshTableOptions = {
            type: "get",
            url: "/laba/coffeeJoin", //this is my servlet
            success: function (response) {
                onCountryRequestSuccess(response);
            }
        };
        createRequest(refreshTableOptions);
    }

    function onCountryRequestSuccess(resp) {
        refreshCoffee(resp);
        refresh_deleteCoffeeSelect(resp);
    }

    $('#addCoffeeForm').submit(function () {
        var ajax_data = {};
        $('.addCoffeeAction').each(function () {
            ajax_data[$(this).attr('name')] = $(this).val();
        });

        // Refresh data

        var options = {
            type: "post",
            url: "/laba/db", //this is my servlet
            data: ajax_data,
            success: function (response) {
                onDbRequestSuccess(response);
            }
        };

        createRequest(options);

        return false;
    });

    $('#deleteCoffeeForm').submit(function () {
        var ajax_data = {};
        $('.deleteCoffeeAction').each(function () {
            ajax_data[$(this).attr('name')] = $(this).val();
        });

        // Refresh data

        var options = {
            type: "post",
            url: "/laba/db", //this is my servlet
            data: ajax_data,
            success: function (response) {
                onDbRequestSuccess(response);
            }
        };

        createRequest(options);

        return false;
    });

    $('#sort').submit(function () {
        var ajax_data = {};
        $('.updateSort').each(function () {
            ajax_data[$(this).attr('name')] = $(this).val();
        });

        // Refresh data

        var options = {
            type: "post",
            url: "/laba/db", //this is my servlet
            data: ajax_data,
            success: function (response) {
                onDbRequestSuccess(response);
            }
        };

        createRequest(options);

        return false;
    });
    function refreshCoffee(data) {
        var source = $('#coffee-table-template').html();
        var template = Handlebars.compile(source);

        var html = template(data);
        $('#coffeeJoinCountryTable').find('tbody').html(html);
    }

    function refresh_deleteCoffeeSelect(data) {
        var source = $('#select-template').html();
        var template = Handlebars.compile(source);

        var html = template(data);
        $('#deleteCoffeeSelect').html(html);
    }
});
$(document).ready(function() {
  
  function createRequest(options) {
    $.ajax(options);
  }

  function onDbRequestSuccess(resp) {
    var options = {
      type: "get",
      url: "/country", //this is my servlet
      success: function(resp) {
        onCountryRequestSuccess(resp);
      }
    }
    createRequest(options);
  }

  function onCountryRequestSuccess(resp) {
    refreshCountry(resp);
    refresh_CountryDeleteSelect(resp);
    refresh_CountryNameSelect(resp)
  }

  $('#addCountryForm').submit(function() {
    var ajax_data = {};
    $('.countryAddInput').each(function() {
      ajax_data[$(this).attr('name')] = $(this).val();
    });

    // Refresh data

    var options = {
      type: "post",
      url: "/db", //this is my servlet
      data: ajax_data,
      success: function(response) {
        onDbRequestSuccess(resp);
      }
    };

    createRequest(options);

    return false;
  });

  function refreshCountry(data) {
    var source   = $('#coffee-table-template').html();
    var template = Handlebars.compile(source);

    var html = template(data);
    $('.coffeeJoinCountryTable tbody').html(html);

    $('table#countryTable tbody')
      // Call the refresh method
      .closest("table#countryTable")
      .table("refresh")
      // Trigger if the new injected markup contain links or buttons that need to be enhanced
      .trigger("create");
  }

  function refresh_CountryNameSelect(data) {
    var delete_s_country = "";
    $.each(data, function() {
      var delete_s_option = "";
      $.each(this, function(k, v) {
        switch (k) {
          case "name":
            {
              delete_s_option = "<option>" + v + "</option>";
            }
            break;
        }

      });
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
    $.each(data, function() {
      var delete_s_option = "";
      $.each(this, function(k, v) {
        switch (k) {
          case "id":
            {
              delete_s_option = "<option>" + v + "</option>";
            }
            break;
        }

      });
      delete_s_country += delete_s_option;
    });
    $('select#deleteCountrySelect')
      // Append the new rows to the body
      .html(delete_s_country)
      // Call the refresh method
      .closest("select#deleteCountrySelect")
      .selectmenu("refresh")
      .trigger("create")
  }
  $('#deleteCountryForm').submit(function() {
    var ajax_data = {};
    $('.countryDeleteInput').each(function() {
      ajax_data[$(this).attr('name')] = $(this).val();
    });
    $.ajax({
      type: "post",
      url: "/db", //this is my servlet
      data: ajax_data,
      success: function(response) {
        $.ajax({
          type: "get",
          url: "/country", //this is my servlet
          success: function(data) {
            refreshCountry(data)
          }
        });
        $.ajax({
          type: "get",
          url: "/country", //this is my servlet
          success: function(data) {
            refresh_CountryDeleteSelect(data)
          }
        });
        $.ajax({
          type: "get",
          url: "/country", //this is my servlet
          success: function(data) {
            refresh_CountryNameSelect(data)
          }
        });
        $.ajax({
          type: "get",
          url: "/coffee", //this is my servlet
          success: function(data) {
            refreshCoffeeTable(data);
          }
        });
        $.ajax({
          type: "get",
          url: "/coffeeJoin", //this is my servlet
          success: function(data) {
            refreshCoffeeJoinTable(data);
          }
        })
      }
    });
    return false;
  });

  $('#addCoffeeForm').submit(function() {
    var ajax_data = {};
    $('.addCoffeeAction').each(function() {
      ajax_data[$(this).attr('name')] = $(this).val();
    });
    $.ajax({
      type: "post",
      url: "/db", //this is my servlet
      data: ajax_data,
      success: function(response) {
        $.ajax({
          type: "get",
          url: "/coffee", //this is my servlet
          success: function(data) {
            refreshCoffeeTable(data);
          }
        });
        $.ajax({
          type: "get",
          url: "/coffee", //this is my servlet
          success: function(data) {
            refresh_deleteCoffeeSelect(data);
          }
        });

        $.ajax({
          type: "get",
          url: "/coffeeJoin", //this is my servlet
          success: function(data) {
            refreshCoffeeJoinTable(data);
          }
        })
      }
    });
    return false;
  });

  function refreshCoffeeTable(data) {
    var tbl_body = "";
    var select_coffee = "";
    $.each(data, function() {
      var tbl_row = "";
      var id = "";
      var country_id = "";
      var name_c = "";
      var select_opt = "";
      $.each(this, function(k, v) {
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

      });
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
    $.each(data, function() {
      var select_opt = "";
      $.each(this, function(k, v) {
        switch (k) {
          case "id":
            {
              select_opt = "<option>" + v + "</option>";
            }
            break;
        }
      });
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
    $.each(data, function() {
      var tbl_row = "";
      var name = "";
      var country = "";
      var coupage = "";
      var arabica = "";
      var robusta = "";
      $.each(this, function(k, v) {
        switch (k) {
          case "name":
            {
              name = "<td>" + v + "</td>";
            }
            break;
          case "country":
            {
              country = "<td>" + v + "</td>";
            }
            break;
          case "coupage":
            {
              coupage = "<td>" + v + "</td>";
            }
            break;
          case "arabica":
            {
              arabica = "<td>" + v + "</td>";
            }
            break;
          case "robusta":
            {
              robusta = "<td>" + v + "</td>";
            }
            break;
        }

      });
      tbl_row = name + country + coupage + arabica + robusta;
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

  $('#deleteCoffeeForm').submit(function() {
    var ajax_data = {};
    $('.deleteCoffeeAction').each(function() {
      ajax_data[$(this).attr('name')] = $(this).val();
    });
    $.ajax({
      type: "post",
      url: "/db", //this is my servlet
      data: ajax_data,
      success: function(response) {
        $.ajax({
          type: "get",
          url: "/coffee", //this is my servlet
          success: function(data) {
            refreshCoffeeTable(data);
          }
        });
        $.ajax({
          type: "get",
          url: "/coffee", //this is my servlet
          success: function(data) {
            refresh_deleteCoffeeSelect(data);
          }
        });
        $.ajax({
          type: "get",
          url: "/coffeeJoin", //this is my servlet
          success: function(data) {
            refreshCoffeeJoinTable(data);
          }
        })
      }
    });
    return false;
  });
});
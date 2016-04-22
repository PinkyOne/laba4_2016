package web.controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "db", value = "/db")
public class DbController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            database.controller.DatabaseController.Connect();
            List<List<Object>> coffeeJoinCountry = genList(
                    database.controller.DatabaseController.getCoffeeTableJoinCountry());
            List<List<Object>> coffee = genList(
                    database.controller.DatabaseController.getCoffeeTable());
            List<List<Object>> country = genList(
                    database.controller.DatabaseController.getCountryTable());
            req.setAttribute("coffeeJoinCountry", coffeeJoinCountry);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    private List<List<Object>> genList(JSONArray data) {
        List<List<Object>> res = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            List<Object> tmp = new ArrayList<>();
            JSONObject jso = data.getJSONObject(i);
            for (Object key : jso.keySet()) {
                tmp.add(jso.get((String) key));
            }
            res.add(tmp);
        }
        return res;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action;
        action = req.getParameter("action");
        if (action == null) action = "";
        try {
            switch (action) {
                case "addToCoffee": {
                    database.controller.DatabaseController.insertIntoCoffee(req.getParameter("coffeeName"), req.getParameter("country_name"));
                }
                break;
                case "deleteCoffee": {
                    database.controller.DatabaseController.deleteFromCoffee(Integer.parseInt(req.getParameter("coffee_id")));
                }
                break;
                case "addToCountry": {
                    database.controller.DatabaseController.insertIntoCountry(req.getParameter("countryName"),
                            Integer.parseInt(req.getParameter("tax")));
//                    req.getRequestDispatcher("/country").forward(req, resp);
                }
                break;
                case "deleteCountry": {
                    database.controller.DatabaseController.deleteFromCountry(Integer.parseInt(req.getParameter("country_id")));
//                    req.getRequestDispatcher("/country").forward(req, resp);
                }
                break;
                default: {
                }
            }
            //  pageContext.forward("/db");
        } catch (Exception e) {
            System.err.print(e.getMessage() + "\n");
            e.printStackTrace();
            req.getRequestDispatcher("/error").forward(req, resp);
        } finally {
            resp.setStatus(200);
        }
    }
}

package web.controllers;

import database.controller.Brands;
import database.controller.Countries;
import database.controller.Coupages;
import database.controller.DatabaseController;
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

import static database.controller.DatabaseController.*;

@WebServlet(name = "db", value = "/db")
public class DbController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            connectDB();
            List<List<Object>> coffeeJoinCountry = genList(
                    database.controller.DatabaseController.getCoffeeTableJoinCountryJoinCoupage());
            req.setAttribute("coffeeJoinCountry", coffeeJoinCountry);
            List<List<Object>> country = genList(
                    getCountryTable().toJSONObject().getJSONArray("countries"));
            req.setAttribute("country", country);
            List<List<Object>> coupage = genList(
                    getCoupageTable().toJSONObject().getJSONArray("coupages"));
            req.setAttribute("coupage", coupage);
            List<List<Object>> coffee = genList(
                    getCoffeeTable().toJSONObject().getJSONArray("brands"));
            req.setAttribute("coffee", coffee);
            closeDB();
        } catch (SQLException | ClassNotFoundException e) {
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
        req.setCharacterEncoding("UTF-8");
        if (action == null) action = "";
        try {
            switch (action) {
                case "addToCoffee": {
                    connectDB();
                    Countries countries = getCountryTable();
                    Coupages coupages = getCoupageTable();

                    String countryName = req.getParameter("country_name");
                    String coupageName = req.getParameter("coupage_name");
                    String text = new String(coupageName.getBytes("ISO-8859-1"), "UTF-8");
                    database.controller.DatabaseController.insertIntoCoffee(
                            req.getParameter("coffeeName"),
                            countries.getCountry(countryName),
                            coupages.getCoupage(text));
                    closeDB();
                }
                break;
                case "deleteCoffee": {
                    String name = new String(req.getParameter("coffee_name")
                            .getBytes("ISO-8859-1"), "UTF-8");
                    connectDB();
                    Brands brands = DatabaseController.getCoffeeTable();
                    database.controller.DatabaseController.deleteFromCoffee(brands.getCoffee(name));
                    closeDB();
                }
                break;
                case "updateSort": {
                    String sortType = new String(req.getParameter("sortType")
                            .getBytes("ISO-8859-1"), "UTF-8");
                    DatabaseController.setSortType(DatabaseController.SortType.getValueS(sortType));
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

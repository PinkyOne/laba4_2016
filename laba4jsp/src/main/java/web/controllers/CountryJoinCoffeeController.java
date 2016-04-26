package web.controllers;

import database.controller.DatabaseController;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static database.controller.DatabaseController.closeDB;
import static database.controller.DatabaseController.connectDB;

@WebServlet(name = "coffeeJoin", value = "/coffeeJoin")
public class CountryJoinCoffeeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            connectDB();
            JSONArray coffeeJoinCountry = DatabaseController.getCoffeeTableJoinCountryJoinCoupage();
            closeDB();
            resp.setContentType("application/json");
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("data",coffeeJoinCountry);
            writer.print(jsonObject);
            writer.flush();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

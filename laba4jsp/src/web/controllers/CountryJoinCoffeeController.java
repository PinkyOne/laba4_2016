package web.controllers;

import database.controller.DatabaseController;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "coffeeJoin", value = "/coffeeJoin")
public class CountryJoinCoffeeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DatabaseController.Connect();
            JSONArray coffeeJoinCountry = DatabaseController.getCoffeeTableJoinCountry();
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.print(coffeeJoinCountry);
            writer.flush();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
        }
    }
}

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

@WebServlet(name = "coffee", value = "/coffee")
public class CoffeeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DatabaseController.Connect();
            resp.setContentType("application/json");
            JSONArray coffee = DatabaseController.getCoffeeTable();
            PrintWriter writer = resp.getWriter();
            writer.print(coffee);
            writer.flush();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

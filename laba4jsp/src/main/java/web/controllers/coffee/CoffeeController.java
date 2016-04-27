package web.controllers.coffee;

import database.controller.coffee.Brands;
import database.controller.DatabaseController;

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

@WebServlet(name = "coffee", value = "/coffee")
public class CoffeeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            connectDB();
            Brands coffee = DatabaseController.getCoffeeTable();
            closeDB();
            PrintWriter writer = resp.getWriter();
            writer.print(coffee);
            writer.flush();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

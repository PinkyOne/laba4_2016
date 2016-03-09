package web.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "db", value = "/db")
public class DatabaseController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            database.controller.DatabaseController.Connect();
            List<List<Object>> coffeeJoinCountry = database.controller.DatabaseController.getCoffeeTableJoinCountry();
            req.setAttribute("coffeeJoinCountry", coffeeJoinCountry);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}

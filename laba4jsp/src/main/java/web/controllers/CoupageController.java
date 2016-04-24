package web.controllers;

import database.controller.Coupages;
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

@WebServlet(name = "coupage", value = "/coupage")
public class CoupageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            connectDB();
            Coupages coupages = DatabaseController.getCoupageTable();
            closeDB();
            PrintWriter writer = resp.getWriter();
            writer.print(coupages);
            writer.flush();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

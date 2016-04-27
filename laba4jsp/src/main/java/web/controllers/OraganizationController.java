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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "org", value = "/org")
public class OraganizationController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("isLoggedIn", User.getInstance().isLoggedIn());

        req.getRequestDispatcher("/pages/registration.jsp").forward(req, resp);
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
                case "reg": {
                    String name = new String(req.getParameter("name").getBytes("ISO-8859-1"), "UTF-8"),
                            surname = new String(req.getParameter("surname").getBytes("ISO-8859-1"), "UTF-8"),
                            email = new String(req.getParameter("email").getBytes("ISO-8859-1"), "UTF-8"),
                            login = new String(req.getParameter("login").getBytes("ISO-8859-1"), "UTF-8"),
                            password = new String(req.getParameter("password").getBytes("ISO-8859-1"), "UTF-8");

                    if (DatabaseController.addRecordToRegistrationTable(name, surname, email, login, password)) {
                        User.getInstance().setLoggedIn(true);
                        req.setAttribute("isLoggedIn", true);
                    }
                    int userId = getUserId(login, password);
                    User.getInstance().setId(userId);
                    req.setAttribute("isLoggedIn", userId > 0);
                    req.getRequestDispatcher("/pages/registration.jsp").forward(req, resp);
                }
                break;
                case "login": {
                    String login = new String(req.getParameter("username").getBytes("ISO-8859-1"), "UTF-8"),
                            password = new String(req.getParameter("password").getBytes("ISO-8859-1"), "UTF-8");
                    int userId = getUserId(login, password);
                    User.getInstance().setId(userId);
                    req.setAttribute("isLoggedIn", userId > 0);
                    req.getRequestDispatcher("/pages/comments.jsp").forward(req, resp);
                }
                break;
                case "logout": {
                    req.setAttribute("isLoggedIn", false);
                    req.getRequestDispatcher("/pages/comments.jsp").forward(req, resp);
                }
                break;
                case "comment": {
                    String text = new String(req.getParameter("text").getBytes("ISO-8859-1"), "UTF-8");
                    DatabaseController.addRecordToForumTable(User.getInstance().getId(), text);
                }
                break;
                case "delete": {

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

    private int getUserId(String login, String password) throws SQLException, ClassNotFoundException {
        int userId = -1;
        JSONObject users = DatabaseController.getUsers();
        JSONArray array = users.getJSONArray("data");
        for (int i = 0; i < array.length(); i++)
            if (array.getJSONObject(i).getString("login").equals(login)
                    && array.getJSONObject(i).getString("password").equals(password)) {
                userId = array.getJSONObject(i).getInt("userId");
                break;
            }
        return userId;
    }
}

package database.controller;


import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;


public class DatabaseController {
    public static Connection conn;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Connect() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager
                    .getConnection("jdbc:postgresql://52.38.201.160:5432/postgres",
                            "postgres", "1234");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    // --------Заполнение таблицы--------
    public static void insertIntoCoffee(String name, String country) throws SQLException {
        Statement statement = conn.createStatement();
        JSONArray countryTable = getCountryTable();
        int countryId = -1;
        for (int i = 0; i < countryTable.length(); i++) {
            JSONObject countryJSON = countryTable.getJSONObject(i);
            if (countryJSON.getString("name").equalsIgnoreCase(country)) {
                countryId = countryJSON.getInt("id");
            }
        }
        if (statement.execute("INSERT INTO coffee (\"name\", \"country_id\") VALUES (\"" + name + "\"," + countryId + "); "))
            System.out.println("Запись занесена");
        else
            System.out.println("Запись не занесена");
        statement.close();
    }

    public static void insertIntoCountry(String name, int tax) throws SQLException {
        Statement statement = conn.createStatement();
        if (statement.execute("INSERT INTO country (\"name\", \"tax\") VALUES (\"" + name + "\"," + tax + "); "))
            System.out.println("Запись занесена");
        else
            System.out.println("Запись не занесена");
        statement.close();
    }

    public static JSONArray getCoffeeTableJoinCountry() throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery("SELECT " +
                "coffee.name," +
                "country.name AS \"country\"" +
                "FROM coffee " +
                "JOIN country " +
                "ON coffee.country_id=country.id " +
                "JOIN coupage " +
                "ON coffee.coupage_id=coupage.id;");
        JSONArray jso = new JSONArray();
        while (res.next()) {
            JSONObject tmp = new JSONObject();
            tmp.put("name", res.getString("name"));
            tmp.put("country", res.getString("country"));
            jso.put(tmp);
        }
        statement.close();
        return jso;
    }

    public static JSONArray getCoffeeTable() throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM product;");
        JSONArray jso = new JSONArray();

        while (res.next()) {
            JSONObject tmp = new JSONObject();
            tmp.put("id", res.getInt("id"));
            tmp.put("name", res.getString("name"));
            tmp.put("country_id", res.getInt("country_id"));
            jso.put(tmp);
        }
        statement.close();
        return jso;
    }

    public static JSONArray getCountryTable() throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM suppliers;");
        JSONArray jso = new JSONArray();
        while (res.next()) {
            JSONObject tmp = new JSONObject();
            tmp.put("id", res.getInt("id"));
            tmp.put("name", res.getString("name"));
            tmp.put("tax", res.getInt("tax"));
            jso.put(tmp);
        }
        statement.close();
        return jso;
    }

    public static void deleteFromCoffee(int id) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("DELETE FROM coffee WHERE id = " + id + " ;");
        statement.close();
    }

    public static void deleteFromCountry(int id) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("DELETE FROM coffee WHERE country_id = " + id + " ;");
        statement.execute("DELETE FROM country WHERE id = " + id + " ;");
        statement.close();
    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException {
        conn.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }

}

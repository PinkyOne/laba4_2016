package database.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;


public class DatabaseController {
    public static Connection conn;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Connect() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");

        conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Alex\\Documents\\GitHub\\laba4_2016\\laba4jsp\\src\\db.s3db");
        System.out.println(conn);
        System.out.println("База Подключена!");
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
                "country.name AS \"country\"," +
                "country.tax " +
                "FROM coffee " +
                "JOIN country " +
                "ON coffee.country_id=country.id;");
        JSONArray jso = new JSONArray();
        while (res.next()) {
            JSONObject tmp = new JSONObject();
            tmp.put("name", res.getString("name"));
            tmp.put("country", res.getString("country"));
            tmp.put("tax", res.getInt("tax"));
            jso.put(tmp);
        }
        statement.close();
        return jso;
    }

    public static JSONArray getCoffeeTable() throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM \"coffee\";");
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
        ResultSet res = statement.executeQuery("SELECT * FROM country;");
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

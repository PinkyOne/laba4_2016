package database.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseController {
    public static Connection conn;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Connect() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");

        conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Alex\\Desktop\\laba4jsp\\src\\database\\db.s3db");

        System.out.println("База Подключена!");
    }

    // --------Заполнение таблицы--------
    public static void insertIntoCoffee(String name, int countryId) throws SQLException {
        Statement statement = conn.createStatement();
        if (statement.execute("INSERT INTO coffee (\"name\", \"country_id\") VALUES (" + name + "," + countryId + "); "))
            System.out.println("Запись занесена");
        else
            System.out.println("Запись не занесена");
        statement.close();
    }

    public static void insertIntoCountry(String name, String tax) throws SQLException {
        Statement statement = conn.createStatement();
        if (statement.execute("INSERT INTO country (\"name\", \"tax\") VALUES (" + name + "," + tax + "); "))
            System.out.println("Запись занесена");
        else
            System.out.println("Запись не занесена");
        statement.close();
    }

    public static List<List<Object>> getCoffeeTableJoinCountry() throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery("SELECT coffee.name,country.name AS \"country\",country.tax FROM coffee JOIN country ON coffee.country_id=country.id;");
        List<List<Object>> result = new ArrayList<>();
        List header = new ArrayList<>();
        header.add("name");
        header.add("country");
        header.add("tax");
        result.add(header);
        while (res.next()) {
            List tmp = new ArrayList<>();
            tmp.add(res.getString("name"));
            tmp.add(res.getString("country"));
            tmp.add(res.getInt("tax"));
            result.add(tmp);
        }
        statement.close();
        return result;
    }

    public static List<List<Object>> getCoffeeTable() throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM \"coffee\";");
        List<List<Object>> result = new ArrayList<>();
        List header = new ArrayList<>();
        header.add("id");
        header.add("name");
        header.add("country_id");
        result.add(header);
        while (res.next()) {
            List tmp = new ArrayList<>();
            tmp.add(res.getInt("id"));
            tmp.add(res.getString("name"));
            tmp.add(res.getInt("country_id"));
            result.add(tmp);
        }
        statement.close();
        return result;
    }

    public static List<List<Object>> getCountryTable() throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM country;");
        List<List<Object>> result = new ArrayList<>();
        List header = new ArrayList<>();
        header.add("id");
        header.add("name");
        header.add("tax");
        result.add(header);
        while (res.next()) {
            List tmp = new ArrayList<>();
            tmp.add(res.getInt("id"));
            tmp.add(res.getString("name"));
            tmp.add(res.getInt("tax"));
            result.add(tmp);
        }
        statement.close();
        return result;
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

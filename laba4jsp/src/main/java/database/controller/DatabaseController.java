package database.controller;


import database.controller.coffee.*;
import org.json.JSONArray;
import org.json.JSONObject;
import web.controllers.User;

import java.sql.*;


public class DatabaseController {

    public enum SortType {
        AZ_NAME("A-Z name"),
        ZA_NAME("Z-A name"),
        AZ__COUNTRY("A-Z country"),
        ZA__COUNTRY("Z-A country"),
        ARABICA_INCREASE("arabica increase"),
        ARABICA_DECREASE("arabica decrease");
        private String value;

        SortType(String s) {
            value = s;
        }

        String getValue() {
            return value;
        }

        public static SortType getValueS(String sortType) {
            for (SortType type :
                    values()) {
                if (type.value.equals(sortType)) {
                    return type;
                }
            }
            return AZ_NAME;
        }
    }

    // region COFFEE
    public static void setSortType(SortType sortType) {
        DatabaseController.sortType = sortType;
    }

    private static Connection conn;

    // end region
    public static SortType getSortType() {
        return sortType;
    }

    private static SortType sortType = SortType.AZ_NAME;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void connectDB() throws ClassNotFoundException, SQLException {
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

    // --------Закрытие--------
    public static void closeDB() throws SQLException {
        conn.close();
        System.out.println("Соединения закрыты");
    }

    // --------Заполнение таблицы--------
    public static void insertIntoCoffee(String name, Country country, Coupage coupage) throws SQLException {
        Statement statement = conn.createStatement();

        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO coffee (name, coupage_id, country_id) VALUES (?,?,?)");
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, coupage.getId());
        preparedStatement.setInt(3, country.getId());

        // if (statement.execute("INSERT INTO coffee (\"name\", \"country_id\",\"coupage_id\") VALUES (\"" + name + "\"," + countryId + "); "))
        if (preparedStatement.execute())
            System.out.println("Запись занесена");
        else
            System.out.println("Запись не занесена");
        statement.close();
    }

    public static JSONArray getCoffeeTableJoinCountryJoinCoupage() throws SQLException {
        Statement statement = conn.createStatement();
        String sortColumn = "", sort = "";

        switch (sortType) {
            case AZ_NAME: {
                sort = " ASC";
                sortColumn = "name";
            }
            break;
            case ZA_NAME: {
                sort = " DESC";
                sortColumn = "name";
            }
            break;
            case AZ__COUNTRY: {
                sort = " ASC";
                sortColumn = "\"country\"";
            }
            break;
            case ZA__COUNTRY: {
                sort = " DESC";
                sortColumn = "\"country\"";
            }
            break;
            case ARABICA_INCREASE: {
                sort = " ASC";
                sortColumn = "\"arabica\"";
            }
            break;
            case ARABICA_DECREASE: {
                sort = " DESC";
                sortColumn = "\"arabica\"";
            }
            break;
            default: {
            }
            break;
        }
        String sortOrder = sortColumn + sort;
        ResultSet res = statement.executeQuery("SELECT " +
                "coffee.name," +
                "country.name AS \"country\", " +
                "coupage.name AS \"coupage\", " +
                "coupage.arabica AS \"arabica\", " +
                "coupage.robusta AS \"robusta\" " +
                "FROM coffee " +
                "JOIN country " +
                "ON coffee.country_id=country.id " +
                "JOIN coupage " +
                "ON coffee.coupage_id=coupage.id " +
                "ORDER BY " + sortOrder + ";");
        JSONArray jso = new JSONArray();
        while (res.next()) {
            JSONObject tmp = new JSONObject();
            tmp.put("name", res.getString("name"));
            tmp.put("country", res.getString("country"));
            tmp.put("coupage", res.getString("coupage"));
            tmp.put("arabica", res.getString("arabica"));
            tmp.put("robusta", res.getString("robusta"));
            jso.put(tmp);
        }
        statement.close();
        return jso;
    }

    public static Brands getCoffeeTable() throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM coffee;");
        Brands brands = new Brands();
        while (res.next()) {
            Coffee coffee = new Coffee(
                    res.getInt("id"),
                    res.getString("name"),
                    res.getInt("country_id"),
                    res.getInt("coupage_id"));
            brands.addCoffee(coffee);
        }
        statement.close();
        return brands;
    }

    public static Countries getCountryTable() throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM country;");
        Countries countries = new Countries();
        while (res.next()) {
            Country country = new Country(res.getInt("id"), res.getString("name"));
            countries.addCountry(country);
        }
        statement.close();
        return countries;
    }

    public static void deleteFromCoffee(Coffee coffee) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("DELETE FROM coffee WHERE id = " + coffee.getId() + " ;");
        statement.close();
    }


    public static Coupages getCoupageTable() throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM coupage;");
        Coupages coupages = new Coupages();
        while (res.next()) {
            Coupage coupage = new Coupage(
                    res.getInt("id"),
                    res.getString("name"),
                    res.getInt("robusta"),
                    res.getInt("arabica"));
            coupages.addCoffee(coupage);
        }
        statement.close();
        return coupages;
    }


    // region ORGANIZATION
    public static boolean addRecordToRegistrationTable(String name, String surname, String email, String login, String password) throws SQLException, ClassNotFoundException {
        try {
            connectDB();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT  INTO \"Registration\" (name,surname,email,login,password) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, login);
            preparedStatement.setString(5, password);
            if (preparedStatement.execute()) {
            }
            closeDB();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static JSONObject getUsers() throws SQLException, ClassNotFoundException {

        connectDB();
        Statement statement = conn.createStatement();

        String selectTableSQL = "SELECT * FROM \"Registration\";";
        // выбираем данные с БД
        ResultSet rs = statement.executeQuery(selectTableSQL);
        JSONObject result = new JSONObject();
        JSONArray users = new JSONArray();
        // И если что то было получено то цикл while сработает
        while (rs.next()) {
            JSONObject user = new JSONObject();
            int userId = rs.getInt(1);
            String name = rs.getString(2);
            String surname = rs.getString(3);
            String email = rs.getString(4);
            String login = rs.getString(5);
            String password = rs.getString(6);
            user.put("userId", userId);
            user.put("name", name);
            user.put("surname", surname);
            user.put("email", email);
            user.put("login", login);
            user.put("password", password);
            users.put(user);
        }
        closeDB();
        result.put("data", users);
        closeDB();
        return result;
    }

    public static JSONObject getContentOfForumTable() throws SQLException, ClassNotFoundException {

        connectDB();
        Statement statement = conn.createStatement();

        //language=PostgreSQL
        String selectTableSQL =
                "SELECT \"Id_com\",\"Forum\".\"Id_user\",name,surname,date,text FROM \"Forum\" " +
                        "JOIN \"Registration\" " +
                        "ON \"Forum\".\"Id_user\" = \"Registration\".\"Id_user\";";
        // выбираем данные с БД
        ResultSet rs = statement.executeQuery(selectTableSQL);
        JSONObject result = new JSONObject();
        JSONArray comments = new JSONArray();
        // И если что то было получено то цикл while сработает
        while (rs.next()) {
            JSONObject comment = new JSONObject();
            int cId = rs.getInt(1);
            comment.put("comId", cId);
            int uId = rs.getInt(2);
            comment.put("userId", uId);
            comment.put("name", rs.getString(3));
            comment.put("surname", rs.getString(4));
            comment.put("date", rs.getDate(5).toString());
            comment.put("text", rs.getString(6));
            if (User.getInstance().getId() == uId) {
                comment.put("test", true);
                comment.put("deleteLink", "/delete?id=" + cId);
            } else comment.put("test", false);
            comments.put(comment);
        }
        result.put("data", comments);
        closeDB();
        return result;
    }

    public static void addRecordToForumTable(int userId, String text) throws SQLException, ClassNotFoundException {
        connectDB();
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO \"Forum\" (\"Id_user\",text) VALUES (?,?)");
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, text);
        preparedStatement.execute();
        closeDB();
    }

    public static void removeRecordRegistrationTable(String idPerson) throws SQLException, ClassNotFoundException {
        connectDB();
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM \"Registration\"  WHERE \"Id_user\" = ?");
        preparedStatement.setInt(1, Integer.parseInt(idPerson));
        preparedStatement.execute();
        closeDB();
    }

    public static void removeRecordForumTable(String idPosition) throws SQLException, ClassNotFoundException {
        connectDB();
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM \"Forum\" WHERE \"Id_com\" = ?");
        preparedStatement.setInt(1, Integer.parseInt(idPosition));
        preparedStatement.execute();
        closeDB();
    }
    // end region ORGANIZATION
}

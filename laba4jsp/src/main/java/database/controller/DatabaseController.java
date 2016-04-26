package database.controller;


import org.json.JSONArray;
import org.json.JSONObject;

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

    public static void setSortType(SortType sortType) {
        DatabaseController.sortType = sortType;
    }

    private static Connection conn;

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
}

package database.controller;

public class Database {
    private static Database ourInstance = new Database();

    private Database() {
    }

    public static Database getInstance() {
        return ourInstance;
    }
}

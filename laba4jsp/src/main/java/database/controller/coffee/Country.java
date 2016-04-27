package database.controller.coffee;

import org.json.JSONObject;

public class Country {
    int id;
    String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public JSONObject toJSONObject() {
        JSONObject jso = new JSONObject();
        jso.put("id", id);
        jso.put("name", name);
        return jso;
    }
}

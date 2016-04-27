package database.controller.coffee;

import org.json.JSONObject;

public class Coffee {
    int id;
    String name;
    int coupage_id;
    int country_id;

    public Coffee(int id, String name, int country_id, int coupage_id) {
        this.id = id;
        this.name = name;
        this.coupage_id = coupage_id;
        this.country_id = country_id;
    }

    public int getCoupage_id() {
        return coupage_id;
    }

    public int getCountry_id() {
        return country_id;
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
        jso.put("country_id", country_id);
        jso.put("coupage_id", coupage_id);
        return jso;
    }
}

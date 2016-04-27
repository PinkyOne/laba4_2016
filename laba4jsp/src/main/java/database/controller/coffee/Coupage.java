package database.controller.coffee;

import org.json.JSONObject;

public class Coupage {
    int id, robusta, arabica;
    String name;

    public Coupage(int id, String name, int robusta, int arabica) {
        this.id = id;
        this.name = name;
        this.robusta = robusta;
        this.arabica = arabica;
    }

    public int getId() {
        return id;
    }

    public int getRobusta() {
        return robusta;
    }

    public int getArabica() {
        return arabica;
    }

    public String getName() {
        return name;
    }

    public JSONObject toJSONObject() {
        JSONObject jso = new JSONObject();
        jso.put("id", id);
        jso.put("name", name);
        jso.put("robusta", robusta);
        jso.put("arabica", arabica);
        return jso;
    }
}

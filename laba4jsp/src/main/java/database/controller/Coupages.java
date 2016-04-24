package database.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class Coupages {
    private Set<Coupage> coupages;

    Coupages() {
        coupages = new HashSet<>();
    }

    void addCoffee(Coupage coupage) {
        coupages.add(coupage);
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        JSONArray countryJSONs = new JSONArray();
        for (Coupage coupage : coupages) {
            countryJSONs.put(coupage.toJSONObject());
        }
        json.put("coupages", countryJSONs);
        return json;
    }

    public Coupage getCoupage(String coupageName) throws Exception {
        for (Coupage coupage :
                coupages) {
            if (coupage.name.equalsIgnoreCase(coupageName)) return coupage;
        }
        throw new Exception("can not found coffee with name = "+coupageName);
    }
}

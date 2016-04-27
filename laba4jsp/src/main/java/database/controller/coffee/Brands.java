package database.controller.coffee;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class Brands {
    private Set<Coffee> brands;

    public Brands() {
        brands = new HashSet<>();
    }

    public void addCoffee(Coffee coffee) {
        brands.add(coffee);
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        JSONArray countryJSONs = new JSONArray();
        for (Coffee country : brands) {
            countryJSONs.put(country.toJSONObject());
        }
        json.put("brands", countryJSONs);
        return json;
    }

    public Coffee getCoffee(String name) throws Exception {
        for (Coffee coffee :
                brands) {
            if (coffee.name.equalsIgnoreCase(name)) return coffee;
        }
        throw new Exception("can not found coffe with name = "+name);
    }
}

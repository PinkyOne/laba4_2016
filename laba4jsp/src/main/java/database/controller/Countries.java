package database.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class Countries {
    private Set<Country> countries;

    public Countries() {
        countries = new HashSet<>();
    }

    public void addCountry(Country country) {
        countries.add(country);
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        JSONArray countryJSONs = new JSONArray();
        for (Country country : countries) {
            countryJSONs.put(country.toJSONObject());
        }
        json.put("countries", countryJSONs);
        return json;
    }

    public Country getCountry(String countryName) throws Exception {
        for (Country coupage :
                countries) {
            if (coupage.name.equalsIgnoreCase(countryName)) return coupage;
        }
        throw new Exception("can not found coffee with name = " + countryName);
    }
}

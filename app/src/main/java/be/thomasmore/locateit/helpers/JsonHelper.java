package be.thomasmore.locateit.helpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.locateit.classes.Department;
import be.thomasmore.locateit.classes.Feedback;
import be.thomasmore.locateit.classes.Product;

public class JsonHelper {

    public Feedback getFeedback(String jsonTekst) {
        Feedback feedback = new Feedback();

        try {

            JSONObject jsonFeedback = new JSONObject(jsonTekst);

            feedback.setBeschrijving(jsonFeedback.getString("beschrijving"));
            feedback.setScore(jsonFeedback.getInt("score"));
            feedback.setTimestamp(jsonFeedback.getInt("timestamp"));


        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return feedback;
    }

    public JSONObject getJsonFeedback (Feedback feedback) {
        JSONObject jsonObjectFeedback = new JSONObject();
        try {
            jsonObjectFeedback.put("score", feedback.getScore());
            jsonObjectFeedback.put("beschrijving", feedback.getBeschrijving());
            jsonObjectFeedback.put("timestamp", feedback.getTimestamp());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jsonObjectFeedback;
    }

    public List<Product> getProducts(String jsonText){
        List<Product> products = new ArrayList<Product>();

        try{
            JSONArray jsonProductArray = new JSONArray(jsonText);
            for (int i=0; i<jsonProductArray.length(); i++){
                JSONObject jsonProduct = jsonProductArray.getJSONObject(i);

                Product product = new Product();
                product.setId(jsonProduct.getString("_id"));
                product.setNaam(jsonProduct.getString("naam"));
                product.setBeschrijving(jsonProduct.getString("beschrijving"));
                product.setAfdeling(jsonProduct.getString("afdeling"));
                product.setPrijs(jsonProduct.getDouble("prijs"));

                Log.i("JSON Object: ", product.toString());

                products.add(product);
            }
        }catch (JSONException e){
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return products;
    }

    public List<Department> getDepartments(String jsonText){
        List<Department> departments = new ArrayList<Department>();

        try{
            JSONArray jsonProductArray = new JSONArray(jsonText);
            for (int i=0; i<jsonProductArray.length(); i++){
                JSONObject jsonProduct = jsonProductArray.getJSONObject(i);

                Department department = new Department();
                department.setId(jsonProduct.getString("_id"));
                department.setNaam(jsonProduct.getString("naam"));

                Log.i("JSON Object: ", department.toString());

                departments.add(department);
            }
        }catch (JSONException e){
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return departments;
    }
}

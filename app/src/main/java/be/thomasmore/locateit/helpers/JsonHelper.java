package be.thomasmore.locateit.helpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.thomasmore.locateit.classes.Feedback;

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
}

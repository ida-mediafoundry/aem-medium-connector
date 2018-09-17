package be.ida.component.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class dataAcquisitionService {
    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        dataAcquisitionService http = new dataAcquisitionService();

        System.out.println("Sending HTTP GET request");
        http.getMediumPosts();

    }

    public JSONArray getMediumPosts() throws IOException,JSONException {
        String url = "https://medium.com/feed/ida-mediafoundry";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //Request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // XML to JSON
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(response.toString());

            JSONArray items = xmlJSONObj.getJSONObject("rss").getJSONObject("channel").getJSONArray("item");
            return items;
//            String jsonPrettyPrintString = items.toString(4);
//            System.out.println(jsonPrettyPrintString);
        } catch (JSONException je) {
            System.out.println(je.toString());
            return null;
        }

    }
}

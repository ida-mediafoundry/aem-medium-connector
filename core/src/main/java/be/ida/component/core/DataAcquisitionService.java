package be.ida.component.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataAcquisitionService {
    private final Logger LOG = LoggerFactory.getLogger(DataAcquisitionService.class);
    private final String USER_AGENT = "Mozilla/5.0";
    private String url = "https://medium.com/feed/ida-mediafoundry";

    public String retrieveRssFeedXML() throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        LOG.debug("Sending 'GET' request to URL : " + url);
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        LOG.debug("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}

package be.ida.medium.connector;

import be.ida.medium.model.MediumPost;
import be.ida.medium.parser.MediumRssFeedParser;
import be.ida.medium.service.MediumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MediumConnector {
    private final String USER_AGENT = "Mozilla/5.0";
    private final static Logger LOG = LoggerFactory.getLogger(MediumConnector.class);

    //TODO make configurable
    private final String URL = "https://medium.com/feed/ida-mediafoundry";

    private MediumService mediumService;
    private MediumRssFeedParser mediumRssFeedParser;

    public MediumConnector(MediumService mediumService, MediumRssFeedParser mediumRssFeedParser) {
        this.mediumService = mediumService;
        this.mediumRssFeedParser = mediumRssFeedParser;
    }

    public void process() {
        String rawXmlString = retrieveRssFeedXML();

        MediumPost mediumPost = mediumRssFeedParser.XmlToMediumPost(rawXmlString);

        mediumService.storeMediumPost(mediumPost);
    }

    private String retrieveRssFeedXML() {

        URL url = null;
        HttpURLConnection httpURLConnection = null;
        String rawXmlString = null;

        try {
            url = new URL(URL);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            LOG.debug("Sending 'GET' request to URL : " + url.getHost());
            httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = httpURLConnection.getResponseCode();
            LOG.debug("Response Code : " + responseCode);

            String inputLine;
            StringBuilder response = new StringBuilder();

            try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            rawXmlString = response.toString();
            LOG.debug(rawXmlString);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }

        return rawXmlString;
    }
}

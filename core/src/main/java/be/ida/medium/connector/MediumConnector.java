package be.ida.medium.connector;

import be.ida.medium.bean.MediumPublication;
import be.ida.medium.bean.publication.Publication;
import be.ida.medium.connector.config.MediumConnectorConfig;
import be.ida.medium.parser.MediumRssFeedParser;
import be.ida.medium.service.MediumService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component(name = "Medium Connector", service = MediumConnector.class, immediate = true)
public class MediumConnector {
    private final static Logger LOG = LoggerFactory.getLogger(MediumConnector.class);
    String responseString;
    @Reference
    private MediumService mediumService;
    @Reference
    private MediumConnectorConfig mediumConnectorConfig;

    public void process() {
        String json = retrieveJson();

        final MediumRssFeedParser mediumRssFeedParser = new MediumRssFeedParser();

        Publication pub = mediumRssFeedParser.jsonToPublication(json);

        MediumPublication mediumPublication = mediumRssFeedParser.publicationToMediumPublication(pub);

        mediumService.storeMediumPublication(mediumPublication);
    }

    private String retrieveJson() {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet("https://medium.com/ida-mediafoundry/latest?format=json&limit=30");
            CloseableHttpResponse response = httpclient.execute(httpget);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseString = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            LOG.error("unable to get HTTP request body");

        }
        int startOfJson = StringUtils.indexOf(responseString, "succes");
        String jsonString = StringUtils.substring(responseString, startOfJson - 2);
        return jsonString;
    }
}

package be.ida.medium.connector;

import be.ida.medium.bean.MediumPublication;
import be.ida.medium.bean.publication.Publication;
import be.ida.medium.connector.config.MediumConnectorConfig;
import be.ida.medium.parser.MediumJsonParser;
import be.ida.medium.service.MediumService;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component(name = "Medium Connector", service = MediumConnector.class, immediate = true)
public class MediumConnector {
    private final static Logger LOG = LoggerFactory.getLogger(MediumConnector.class);
    String responseString;
    @Reference
    private MediumService mediumService;
    @Reference
    private MediumConnectorConfig mediumConnectorConfig;

    public void process() {
        String url = mediumConnectorConfig.getMediumFeedUrl();

        String json = retrieveJson(url);

        final MediumJsonParser mediumJsonParser = new MediumJsonParser();

        Publication pub = mediumJsonParser.jsonToPublication(json);

        MediumPublication mediumPublication = mediumJsonParser.publicationToMediumPublication(pub);

        mediumService.storeMediumPublication(mediumPublication);
    }

    private String retrieveJson(String url) {
        String jsonString = null;

        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpget);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseString = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            LOG.error("unable to get HTTP request body");

        }

        Pattern pattern = Pattern.compile("\\{.*\\:\\{.*\\:.*\\}\\}");
        Matcher matcher = pattern.matcher(responseString);

        if (matcher.find()) {
            jsonString = matcher.group(0);
        }

        return jsonString;
    }
}

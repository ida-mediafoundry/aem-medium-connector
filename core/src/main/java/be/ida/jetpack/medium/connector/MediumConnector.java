package be.ida.jetpack.medium.connector;

import be.ida.jetpack.medium.bean.publication.Publication;
import be.ida.jetpack.medium.connector.config.MediumConnectorConfig;
import be.ida.jetpack.medium.model.MediumPublication;
import be.ida.jetpack.medium.parser.MediumJsonParser;
import be.ida.jetpack.medium.parser.MediumPublicationMapper;
import be.ida.jetpack.medium.service.MediumService;
import be.ida.jetpack.medium.service.MediumXFManagerService;
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
    final MediumJsonParser mediumJsonParser = new MediumJsonParser();
    final MediumPublicationMapper mediumPublicationMapper = new MediumPublicationMapper();

    @Reference
    private MediumService mediumService;

    @Reference
    private MediumXFManagerService mediumXFManagerService;

    @Reference
    private MediumConnectorConfig mediumConnectorConfig;

    public void process() {
        final String url = mediumConnectorConfig.getMediumFeedUrl();
        final String rawJsonString = retrieveRawJson(url);
        final Publication pub = mediumJsonParser.jsonToPublication(rawJsonString);
        final MediumPublication mediumPublication = mediumPublicationMapper.publicationToMediumPublication(pub);
        mediumService.storeMediumPublication(mediumPublication);
    }

    private String retrieveRawJson( final String url ) {
        String jsonString = null;
        String responseString = null;

        if ( url != null ) {
            try ( final CloseableHttpClient httpclient = getDefaultHttpClient() ) {
                final HttpGet httpget = new HttpGet(url);
                final CloseableHttpResponse response = httpclient.execute(httpget);

                final HttpEntity entity = response.getEntity();
                if ( entity != null ) {
                    responseString = EntityUtils.toString(entity, "UTF-8");
                }
            } catch ( final IOException e ) {
                LOG.error("unable to get HTTP request body");
            }

            final Pattern pattern = Pattern.compile("\\{.*\\:\\{.*\\:.*\\}");
            final Matcher matcher = pattern.matcher(responseString);

            if ( matcher.find() ) {
                jsonString = matcher.group(0);
            }
        }

        return jsonString;
    }

    protected CloseableHttpClient getDefaultHttpClient() {
        return HttpClients.createDefault();
    }
}

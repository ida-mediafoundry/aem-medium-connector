package be.ida.medium.connector;

import be.ida.medium.model.MediumPost;
import be.ida.medium.parser.MediumRssFeedParser;
import be.ida.medium.service.MediumService;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MediumConnector {
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
        SyndFeed syndFeed = retrieveRssFeed();

        List<MediumPost> mediumPosts = mediumRssFeedParser.syndFeedToMediumPosts(syndFeed);

        mediumService.storeMediumPost(mediumPosts);
    }

    private SyndFeed retrieveRssFeed(){
        SyndFeed feed = null;

        try {
            feed = new SyndFeedInput().build(new XmlReader(new URL(URL)));
        } catch (IOException e) {
            LOG.error("Could not retrieve RSS feed", e);
        } catch (FeedException e) {
            LOG.error("Could not parse retrieved RSS feed", e);
        }

        return feed;
    }
}

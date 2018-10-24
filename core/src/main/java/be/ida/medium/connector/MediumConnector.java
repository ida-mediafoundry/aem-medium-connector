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
        SyndFeed syndFeed = retrieveRssFeed();

        MediumPost mediumPost = mediumRssFeedParser.syndFeedToMediumPost(syndFeed);

        mediumService.storeMediumPost(mediumPost);
    }

    public SyndFeed retrieveRssFeed(){
        SyndFeed feed = null;

        try {
            feed = new SyndFeedInput().build(new XmlReader(new URL(URL)));
        } catch (IOException | FeedException e) {
            e.printStackTrace();
        }

        return feed;
    }
}

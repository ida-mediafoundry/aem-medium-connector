package be.ida.medium.parser;

import be.ida.TestResourceUtil;
import be.ida.medium.model.MediumPublication;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class MediumRssFeedParserTest {
    MediumRssFeedParser mediumRssFeedParser = new MediumRssFeedParser();

    private static String EXAMPLE_TITLE = "Dummy title";
    private static String EXAMPLE_LINK = "https://medium.com/p/dea34a3ec034";
    private static String EXAMPLE_IMAGE_SOURCE = "https://cdn-images-1.medium.com/max/1024/1*o6hiCteU7wHTzKhTauIPzg.jpeg";
    private static String EXAMPLE_CREATOR = "Dummy user";
    private static String EXAMPLE_PUBLICATION_DATE = "Tue Oct 16 10:01:01 CEST 2018";

    @Test
    public void test_syndFeedToMediumPosts_happyPath() throws IOException, FeedException {
        List<MediumPublication> mediumPublications = mediumRssFeedParser.syndFeedToMediumPosts(getMockSyndFeed("rss/rss-feed-full"));

        assertThat(mediumPublications).isNotEmpty();
        assertThat(mediumPublications.size()).isEqualTo(10);
        assertThat(mediumPublications.get(2)).isNotNull();

        MediumPublication firstMediumPublication = mediumPublications.get(0);

        assertThat(firstMediumPublication.getTitle()).isEqualTo(EXAMPLE_TITLE);
        assertThat(firstMediumPublication.getLink()).isEqualTo(EXAMPLE_LINK);
        assertThat(firstMediumPublication.getImageSource()).isEqualTo(EXAMPLE_IMAGE_SOURCE);
        assertThat(firstMediumPublication.getCreator()).isEqualTo(EXAMPLE_CREATOR);
        assertThat(firstMediumPublication.getPublicationDate()).isEqualTo(EXAMPLE_PUBLICATION_DATE);
    }

    private SyndFeed getMockSyndFeed(String fileName) throws IOException, FeedException {
        SyndFeed feed = null;

        String rawRssFeed = TestResourceUtil.getRawTestResource(fileName);
        feed = new SyndFeedInput().build(new XmlReader(new ByteArrayInputStream(rawRssFeed.getBytes(StandardCharsets.UTF_8))));

        return feed;
    }
}
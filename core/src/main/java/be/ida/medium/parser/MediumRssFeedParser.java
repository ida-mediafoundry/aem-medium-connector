package be.ida.medium.parser;

import be.ida.medium.bean.MediumPost;
import be.ida.medium.bean.MediumPublication;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MediumRssFeedParser {
    public MediumPublication syndFeedToMediumPosts(SyndFeed syndFeed) {
        MediumPublication mediumPublication = new MediumPublication();
        List<MediumPost> mediumPosts = new ArrayList<>();


        for (SyndEntry syndEntry : syndFeed.getEntries()) {
            MediumPost mediumPost = new MediumPost();
            String mediumPostId = StringUtils.substringAfterLast(syndEntry.getUri(), "/");

            mediumPost.setCreator(syndEntry.getAuthor());
            mediumPost.setPublicationDate(syndEntry.getPublishedDate().toString());
            mediumPost.setLink(syndEntry.getUri());
            mediumPost.setTitle(syndEntry.getTitle());
            mediumPost.setId(mediumPostId);

            List<SyndContent> syndContents = syndEntry.getContents();

            if (!syndContents.isEmpty()) {
                SyndContent syndContent = syndContents.get(0);

                if (syndContent != null) {
                    Document doc = Jsoup.parseBodyFragment(syndContent.getValue());

                    Elements imageTag = doc.select("img");

                    if (imageTag != null) {
                        String publicationImageUrl = imageTag.first().attr("src");
                        mediumPost.setImageSource(publicationImageUrl);
                    }
                }
            }

            mediumPosts.add(mediumPost);
        }


        mediumPublication.setPosts(mediumPosts);
        mediumPublication.setName(syndFeed.getTitle());
        mediumPublication.setId(getPublicationId(syndFeed));
        return mediumPublication;
    }

    public String getPublicationId(SyndFeed syndFeed) {
        String link = syndFeed.getLink();
        String publicationId = StringUtils.substringAfter(link, "source=rss");

        return publicationId;

    }
}

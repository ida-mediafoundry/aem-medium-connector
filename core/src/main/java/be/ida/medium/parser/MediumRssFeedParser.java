package be.ida.medium.parser;

import be.ida.medium.bean.MediumPost;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class MediumRssFeedParser {
    public List<MediumPost> syndFeedToMediumPosts(SyndFeed syndFeed){
        List<MediumPost> mediumPublications = new ArrayList<>();

        if(syndFeed != null){
            for (SyndEntry syndEntry : syndFeed.getEntries()){
                MediumPost mediumPublication = new MediumPost();
                String mediumPublicationId = StringUtils.substringAfterLast(syndEntry.getUri(), "/");

                mediumPublication.setCreator(syndEntry.getAuthor());
                mediumPublication.setPublicationDate(syndEntry.getPublishedDate().toString());
                mediumPublication.setLink(syndEntry.getUri());
                mediumPublication.setTitle(syndEntry.getTitle());
                mediumPublication.setId(mediumPublicationId);

                if(!syndEntry.getContents().isEmpty()){
                    Document doc = Jsoup.parseBodyFragment(syndEntry.getContents().get(0).getValue());
                    String publicationImageUrl = doc.select("img").first().attr("src");
                    mediumPublication.setImageSource(publicationImageUrl);
                }

                mediumPublications.add(mediumPublication);
            }
        }

        return mediumPublications;
    }
}

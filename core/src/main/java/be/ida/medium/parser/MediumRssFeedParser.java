package be.ida.medium.parser;

import be.ida.medium.model.MediumPost;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MediumRssFeedParser {
    public MediumPost syndFeedToMediumPost(SyndFeed syndFeed){
        MediumPost mediumPost = null;

        //TODO: map syndFeed to mediumPost

        //.getAuthor()
        //.getPublishedDate()
        //.getTitle()
        //.getUri()

        //TODO: get photo author en article out of syndFeed

        for (SyndEntry syndEntry: syndFeed.getEntries()){
            if(!syndEntry.getContents().isEmpty()){
                Document doc = Jsoup.parseBodyFragment(syndEntry.getContents().get(0).getValue());
                String publicationImageUrl = doc.select("img").first().attr("src");
            }
        }

        return mediumPost;
    }
}

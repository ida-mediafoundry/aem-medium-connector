package be.ida.medium.parser;

import be.ida.medium.model.MediumPublication;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class MediumRssFeedParser {
    public List<MediumPublication> syndFeedToMediumPosts(SyndFeed syndFeed){
        List<MediumPublication> mediumPublications = new ArrayList<>();

        for (SyndEntry syndEntry : syndFeed.getEntries()){
            MediumPublication mediumPublication = new MediumPublication();

            mediumPublication.setCreator(syndEntry.getAuthor());
            mediumPublication.setPublicationDate(syndEntry.getPublishedDate().toString());
            mediumPublication.setLink(syndEntry.getUri());
            mediumPublication.setTitle(syndEntry.getTitle());

            if(!syndEntry.getContents().isEmpty()){
                Document doc = Jsoup.parseBodyFragment(syndEntry.getContents().get(0).getValue());
                String publicationImageUrl = doc.select("img").first().attr("src");
                mediumPublication.setImageSource(publicationImageUrl);
            }

            mediumPublications.add(mediumPublication);
        }

        return mediumPublications;
    }
}

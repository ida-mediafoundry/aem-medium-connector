package be.ida.medium.parser;

import be.ida.medium.bean.MediumPost;
import be.ida.medium.bean.MediumPublication;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class MediumRssFeedParser {
    public MediumPublication syndFeedToMediumPosts(SyndFeed syndFeed){
        MediumPublication mediumPublication = new MediumPublication();
        List<MediumPost> mediumPosts = new ArrayList<>();

        for (SyndEntry syndEntry : syndFeed.getEntries()){
            MediumPost mediumPost = new MediumPost();
            String mediumPostId = StringUtils.substringAfterLast(syndEntry.getUri(), "/");

            mediumPost.setCreator(syndEntry.getAuthor());
            mediumPost.setPublicationDate(syndEntry.getPublishedDate().toString());
            mediumPost.setLink(syndEntry.getUri());
            mediumPost.setTitle(syndEntry.getTitle());
            mediumPost.setId(mediumPostId);


            if(!syndEntry.getContents().isEmpty()){
                Document doc = Jsoup.parseBodyFragment(syndEntry.getContents().get(0).getValue());
                String publicationImageUrl = doc.select("img").first().attr("src");
                mediumPost.setImageSource(publicationImageUrl);
            }

            mediumPosts.add(mediumPost);
        }

        mediumPublication.setPosts(mediumPosts);
        mediumPublication.setName(syndFeed.getTitle());
        return mediumPublication;
    }
}

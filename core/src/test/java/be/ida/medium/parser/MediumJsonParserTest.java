package be.ida.medium.parser;

import be.ida.TestResourceUtil;
import be.ida.medium.bean.MediumPost;
import be.ida.medium.bean.publication.Publication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rometools.rome.io.FeedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(JUnit4.class)
public class MediumJsonParserTest {
    private static String EXAMPLE_TITLE = "Dummy title";
    private static String EXAMPLE_LINK = "https://medium.com/p/dea34a3ec034";
    private static String EXAMPLE_IMAGE_SOURCE = "https://cdn-images-1.medium.com/max/1024/1*o6hiCteU7wHTzKhTauIPzg.jpeg";
    private static String EXAMPLE_CREATOR = "Dummy user";
    private static String EXAMPLE_PUBLICATION_DATE = "Tue Oct 16 10:01:01 CEST 2018";
    MediumJsonParser mediumJsonParser = new MediumJsonParser();

    @Test
    public void test_syndFeedToMediumPosts_happyPath() throws IOException, FeedException {
        List<MediumPost> mediumPublications = mediumJsonParser.publicationToMediumPublication(getMockPublication("medium-response-samples/medium-publication-dummy.json")).getPosts();

        assertThat(mediumPublications).isNotEmpty();
        assertThat(mediumPublications.size()).isEqualTo(10);
        assertThat(mediumPublications.get(2)).isNotNull();

        MediumPost firstMediumPublication = mediumPublications.get(0);

        assertThat(firstMediumPublication.getTitle()).isEqualTo(EXAMPLE_TITLE);
        assertThat(firstMediumPublication.getLink()).isEqualTo(EXAMPLE_LINK);
        assertThat(firstMediumPublication.getImageSource()).isEqualTo(EXAMPLE_IMAGE_SOURCE);
        assertThat(firstMediumPublication.getCreator()).isEqualTo(EXAMPLE_CREATOR);
        assertThat(firstMediumPublication.getPublicationDate()).isEqualTo(EXAMPLE_PUBLICATION_DATE);
    }

    private Publication getMockPublication(String fileName) throws IOException, FeedException {

        String rawJson = TestResourceUtil.getRawTestResource(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        Publication publication = objectMapper.readValue(rawJson, Publication.class);
        return publication;
    }
}
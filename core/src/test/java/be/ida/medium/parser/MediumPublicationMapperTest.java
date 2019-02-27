package be.ida.medium.parser;

import be.ida.TestResourceUtil;
import be.ida.medium.bean.publication.Publication;
import be.ida.medium.model.MediumPost;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(JUnit4.class)
public class MediumPublicationMapperTest {
    private static String EXAMPLE_ID = "a54a084bb59f";
    private static String EXAMPLE_LINK = "silicon-valley-musings-a54a084bb59f";
    private static String EXAMPLE_IMAGE_SOURCE = MediumPublicationMapper.IMAGE_BASE_PATH + "1*rv5iOqDoWuJ5CoZdyRXj8g.png";
    private static String EXAMPLE_CREATOR = "Bert Swinnen";
    private static Long EXAMPLE_PUBLICATION_DATE = 1535595788571L;
    MediumPublicationMapper mediumPublicationMapper = new MediumPublicationMapper();


    @Test
    public void test_publicationToMediumPublication_happyPath() throws IOException {
        List<MediumPost> mediumPosts = mediumPublicationMapper.publicationToMediumPublication(getMockPublication("medium-response-samples/medium-publication-dummy.json")).getPosts();

        assertThat(mediumPosts).isNotEmpty();
        assertThat(mediumPosts.get(2)).isNotNull();

        MediumPost firstMediumPublication = mediumPosts.get(0);

        assertThat(firstMediumPublication.getId()).isEqualTo(EXAMPLE_ID);
        assertThat(firstMediumPublication.getLink()).isEqualTo(EXAMPLE_LINK);
        assertThat(firstMediumPublication.getImageSource()).isEqualTo(EXAMPLE_IMAGE_SOURCE);
        assertThat(firstMediumPublication.getCreator()).isEqualTo(EXAMPLE_CREATOR);
        assertThat(firstMediumPublication.getPublicationDate()).isEqualTo(EXAMPLE_PUBLICATION_DATE);
    }

    private Publication getMockPublication(String fileName) throws IOException {

        String rawJson = TestResourceUtil.getRawTestResource(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        Publication publication = objectMapper.readValue(rawJson, Publication.class);
        return publication;
    }
}
package be.ida.jetpack.medium.repository;

import be.ida.jetpack.medium.model.MediumPost;
import be.ida.jetpack.medium.model.MediumPublication;
import be.ida.jetpack.medium.repository.impl.MediumPublicationRepositoryImpl;
import io.wcm.testing.mock.aem.junit.AemContext;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static be.ida.jetpack.medium.MediumConstants.MEDIUM_ROOT;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MediumPublicationRepositoryImplTest {

    @Rule
    public final AemContext context = new AemContext();

    @Mock
    private ResourceResolverFactory resourceResolverFactory;

    @InjectMocks
    private MediumPublicationRepositoryImpl mediumRepository;

    private String MEDIUM_PUBLICATION_TITLE = "Ida-mediafoundry";
    private String MEDIUM_PUBLICATION_ID = "a1a776694b7a";
    private String MEDIUM_POST_ID = "a54a084bb59f";
    private String MEDIUM_POST_TITLE_VALUE = "Article about AEM";
    private String MEDIUM_POST_CREATOR_VALUE = "Thijs Lanssens";
    private String MEDIUM_POST_LINK_VALUE = "digital-transformation-581ab03b6e7a";
    private String MEDIUM_POST_IMAGE_SOURCE_VALUE = "1*vbqSD5TQkOs0DQ238U8wlA.jpeg";
    private Long MEDIUM_POST_PUBLICATION_DATE_VALUE = 1543219656439L;
    private Long MEDIUM_POST_UPDATE_DATE_VALUE = 1543219656439L;

    @Before
    public void init() throws LoginException {
        when(resourceResolverFactory.getServiceResourceResolver(anyMap())).thenReturn(context.resourceResolver());
        context.addModelsForPackage("be.ida.jetpack.medium");
        context.create().page("/content");
    }

    @Test
    public void test_storeMediumPost_givenProperMediumPost_expectJcrNodeAdded() {
        mediumRepository.storeMediumPublication(getMediumPublicationStub());

        Resource mediumPostResource = context.resourceResolver().getResource(MEDIUM_ROOT + MEDIUM_PUBLICATION_ID + "/" + MEDIUM_POST_ID);
        MediumPost mediumPost = mediumPostResource.adaptTo(MediumPost.class);

        assertThat(mediumPost.getTitle()).isEqualTo(MEDIUM_POST_TITLE_VALUE);
        assertThat(mediumPost.getCreator()).isEqualTo(MEDIUM_POST_CREATOR_VALUE);
        assertThat(mediumPost.getLink()).isEqualTo(MEDIUM_POST_LINK_VALUE);
        assertThat(mediumPost.getImageSource()).isEqualTo(MEDIUM_POST_IMAGE_SOURCE_VALUE);
        assertThat(mediumPost.getPublicationDate()).isEqualTo(MEDIUM_POST_PUBLICATION_DATE_VALUE);
        assertThat(mediumPost.getUpdateDate()).isEqualTo(MEDIUM_POST_UPDATE_DATE_VALUE);
    }

    private MediumPublication getMediumPublicationStub() {
        MediumPublication mediumPublication = new MediumPublication();
        mediumPublication.setName(MEDIUM_PUBLICATION_TITLE);
        mediumPublication.setId(MEDIUM_PUBLICATION_ID);

        List<MediumPost> mediumPosts = new ArrayList<>();
        MediumPost mediumPost = new MediumPost();

        mediumPost.setId(MEDIUM_POST_ID);
        mediumPost.setTitle(MEDIUM_POST_TITLE_VALUE);
        mediumPost.setCreator(MEDIUM_POST_CREATOR_VALUE);
        mediumPost.setLink(MEDIUM_POST_LINK_VALUE);
        mediumPost.setImageSource(MEDIUM_POST_IMAGE_SOURCE_VALUE);
        mediumPost.setPublicationDate(MEDIUM_POST_PUBLICATION_DATE_VALUE);
        mediumPost.setUpdateDate(MEDIUM_POST_UPDATE_DATE_VALUE);
        mediumPost.setPublicationId(MEDIUM_PUBLICATION_ID);
        mediumPosts.add(mediumPost);
        mediumPublication.setPosts(mediumPosts);
        return mediumPublication;
    }
}

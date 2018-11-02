package be.ida.medium.repository;

import be.ida.medium.bean.MediumPost;
import be.ida.medium.model.MediumPostModel;
import be.ida.medium.repository.impl.MediumRepositoryImpl;
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

import static be.ida.medium.repository.impl.MediumRepositoryImpl.JCR_CONTENT_BASE_PATH;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MediumRepositoryImplTest {
    @Rule
    public final AemContext context = new AemContext();

    @Mock
    private ResourceResolverFactory resourceResolverFactory;

    @InjectMocks
    private MediumRepositoryImpl mediumRepository;

    private String MEDIUM_POST_TITLE_VALUE = "Article about AEM";
    private String MEDIUM_POST_CREATOR_VALUE = "Thijs Lanssens";
    private String MEDIUM_POST_LINK_VALUE = "google.be";
    private String MEDIUM_POST_IMAGE_SOURCE_VALUE = "google.be/image123";
    private String MEDIUM_POST_PUBLICATION_DATE_VALUE = "2/2/2222";

    @Before
    public void init() throws LoginException {
        when(resourceResolverFactory.getServiceResourceResolver(anyMap())).thenReturn(context.resourceResolver());
        context.addModelsForPackage("be.ida.medium");
        context.create().page(JCR_CONTENT_BASE_PATH + "/publicationName");
    }

    @Test
    public void test_storeMediumPost_givenProperMediumPost_expectJcrNodeAdded() {
        mediumRepository.storeMediumPost(getMediumPostStub());

        Resource mediumPostResource = context.resourceResolver().getResource(JCR_CONTENT_BASE_PATH + "/publicationName/" + MEDIUM_POST_TITLE_VALUE);
        MediumPostModel mediumPostModel = mediumPostResource.adaptTo(MediumPostModel.class);

        assertThat(mediumPostModel.getTitle()).isEqualTo(MEDIUM_POST_TITLE_VALUE);
        assertThat(mediumPostModel.getCreator()).isEqualTo(MEDIUM_POST_CREATOR_VALUE);
        assertThat(mediumPostModel.getLink()).isEqualTo(MEDIUM_POST_LINK_VALUE);
        assertThat(mediumPostModel.getImageSource()).isEqualTo(MEDIUM_POST_IMAGE_SOURCE_VALUE);
        assertThat(mediumPostModel.getPublicationDate()).isEqualTo(MEDIUM_POST_PUBLICATION_DATE_VALUE);
    }

    private MediumPost getMediumPostStub() {
        MediumPost mediumPost = new MediumPost();
        mediumPost.setTitle(MEDIUM_POST_TITLE_VALUE);
        mediumPost.setCreator(MEDIUM_POST_CREATOR_VALUE);
        mediumPost.setLink(MEDIUM_POST_LINK_VALUE);
        mediumPost.setImageSource(MEDIUM_POST_IMAGE_SOURCE_VALUE);
        mediumPost.setPublicationDate(MEDIUM_POST_PUBLICATION_DATE_VALUE);
        return mediumPost;
    }
}
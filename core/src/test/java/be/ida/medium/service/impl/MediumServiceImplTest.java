package be.ida.medium.service.impl;


import be.ida.medium.bean.MediumPost;
import be.ida.medium.bean.MediumPublication;
import be.ida.medium.repository.impl.MediumRepositoryImpl;
import io.wcm.testing.mock.aem.junit.AemContext;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static be.ida.medium.repository.impl.MediumRepositoryImpl.JCR_CONTENT_BASE_PATH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MediumServiceImplTest {
    @InjectMocks
    private MediumServiceImpl mediumService;

    @Mock
    private MediumRepositoryImpl mediumRepository;

    private List<MediumPost> mediumPosts;
    private MediumPublication mediumPublication;

    @Before
    public void init() throws LoginException {
        mediumPublication = new MediumPublication();
        mediumPosts = new ArrayList<>();
        mediumPosts.add(new MediumPost());
        mediumPosts.add(new MediumPost());
        mediumPosts.add(new MediumPost());
        mediumPublication.setPosts(mediumPosts);
    }

    @Ignore
    @Test
    public void test_storeMediumPosts_given_X_expects_Y(){
        mediumService.storeMediumPublication(mediumPublication);

        verify(mediumRepository, times(1)).storeMediumPublication(mediumPublication);
    }
}

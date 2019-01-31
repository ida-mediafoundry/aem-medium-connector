package be.ida.medium.service.impl;


import be.ida.medium.model.MediumPost;
import be.ida.medium.model.MediumPublication;
import be.ida.medium.repository.impl.MediumRepositoryImpl;
import org.apache.sling.api.resource.LoginException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Test
    public void test_storeMediumPosts_given_X_expects_Y() {
        mediumService.storeMediumPublication(mediumPublication);

        verify(mediumRepository, times(1)).storeMediumPublication(mediumPublication);
    }
}

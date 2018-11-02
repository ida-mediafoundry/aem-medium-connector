package be.ida.medium.repository;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit.AemContext;
import org.apache.sling.api.resource.Resource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class JcrMediumRepositoryTest {
    @Rule
    public final AemContext context = new AemContext();

    @Test
    public void testSomething() {
        Resource resource = context.resourceResolver().getResource("/content/sample/en");
        Page page = resource.adaptTo(Page.class);
        // further testing
    }
}
package be.ida.medium.repository;

import io.wcm.testing.mock.aem.junit.AemContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MediumRepositoryImplIT {
    @Rule
    public final AemContext context = new AemContext();

    @Test
    public void testSomething() {
        //TODO: get MediumRepositoryImpl from content and test storeMediumPost
        //TODO: check if jcr got updated properly
    }
}
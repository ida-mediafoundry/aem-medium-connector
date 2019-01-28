package be.ida.medium.bean.publication;

import be.ida.TestResourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

@RunWith(JUnit4.class)
public class MediumPublicationTest {

    @Test
    public void test_jackson() throws IOException {
        String rawJson = TestResourceUtil.getRawTestResource("medium-response-samples/medium-publication-dummy.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Publication mediumPublication = objectMapper.readValue(rawJson, Publication.class);
    }
}
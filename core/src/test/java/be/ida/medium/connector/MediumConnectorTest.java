package be.ida.medium.connector;

import be.ida.medium.parser.MediumRssFeedParser;
import be.ida.medium.repository.MediumAEMRepository;
import be.ida.medium.repository.MediumRepository;
import be.ida.medium.service.MediumService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MediumConnectorTest {
    MediumConnector mediumConnector = null;

    @Before
    public void init() {
        MediumRssFeedParser mediumRssFeedParser = new MediumRssFeedParser();
        MediumRepository mediumRepository = new MediumAEMRepository();
        MediumService mediumService = new MediumService(mediumRepository);

        mediumConnector = new MediumConnector(mediumService, mediumRssFeedParser);
    }

    @Test
    public void exampleTest() {
        mediumConnector.process();
    }
}
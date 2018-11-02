package be.ida.medium.connector;

import be.ida.medium.parser.MediumRssFeedParser;
import be.ida.medium.repository.impl.MediumRepositoryImpl;
import be.ida.medium.repository.MediumRepository;
import be.ida.medium.service.impl.MediumServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MediumConnectorIT {
    MediumConnector mediumConnector = null;

    @Before
    public void init() {
        MediumRssFeedParser mediumRssFeedParser = new MediumRssFeedParser();
        MediumRepository mediumRepository = new MediumRepositoryImpl();
        MediumServiceImpl mediumService = new MediumServiceImpl(mediumRepository);

        mediumConnector = new MediumConnector(mediumService, mediumRssFeedParser);
    }

    @Test
    public void exampleTest() {
        mediumConnector.process();
    }
}
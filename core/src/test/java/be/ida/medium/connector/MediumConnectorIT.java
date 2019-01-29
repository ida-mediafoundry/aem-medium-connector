package be.ida.medium.connector;

import be.ida.medium.parser.MediumJsonParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MediumConnectorIT {
    MediumConnector mediumConnector = null;

    @Before
    public void init() {
        MediumJsonParser mediumJsonParser = new MediumJsonParser();
        mediumConnector = new MediumConnector();
    }

    @Test
    public void exampleTest() {
        mediumConnector.process();
    }
}
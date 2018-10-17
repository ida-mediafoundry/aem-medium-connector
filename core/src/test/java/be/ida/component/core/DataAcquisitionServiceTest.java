package be.ida.component.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

@RunWith(JUnit4.class)
public class DataAcquisitionServiceTest {
   DataAcquisitionService dataAcquisitionService = new DataAcquisitionService();

   @Test
   public void test_retrieveRssFeedXML_givenX_expectsY() throws IOException {
       dataAcquisitionService.retrieveRssFeedXML();
   }
}

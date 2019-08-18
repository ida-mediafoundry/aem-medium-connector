package be.ida.jetpack.medium.connector;


import be.ida.TestResourceUtil;
import be.ida.jetpack.medium.connector.config.MediumConnectorConfig;
import be.ida.jetpack.medium.service.MediumService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MediumConnectorTest {

    private static final String EXAMPLE_URL = "https://medium.com/ida-mediafoundry/latest?format=json";

    @Spy
    @InjectMocks
    MediumConnector mediumConnector = new MediumConnector();

    @Mock
    MediumService mediumService;

    @Mock
    MediumConnectorConfig mediumConnectorConfig;

    @Mock
    CloseableHttpClient closeableHttpClient;

    @Mock
    CloseableHttpResponse closeableHttpResponse;

    @Mock
    HttpEntity httpEntity;


    @Ignore
    @Test
    public void test_process_happyPath() throws IOException {
        final String rawJsonString = TestResourceUtil.getRawTestResource("medium-response-samples/medium-publication-dummy-original.json");

        when(mediumConnectorConfig.getMediumFeedUrl()).thenReturn("www.medium.com/test");

        doReturn(closeableHttpClient).when(mediumConnector).getDefaultHttpClient();

        when(closeableHttpClient.execute(any())).thenReturn(closeableHttpResponse);
        when(closeableHttpResponse.getEntity()).thenReturn(httpEntity);

        when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(rawJsonString.getBytes(StandardCharsets.UTF_8)));
        when(httpEntity.getContentLength()).thenReturn(0L);

        mediumConnector.process();

        verify(mediumService, times(1)).storeMediumPublication(any());
    }
}

package be.ida.jetpack.medium.connector.config.impl;

import be.ida.jetpack.medium.connector.config.MediumConnectorConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Designate(ocd = MediumConnectorConfigImpl.Config.class)
@Component(service = MediumConnectorConfig.class,
        immediate = true
)
public class MediumConnectorConfigImpl implements MediumConnectorConfig {
    private final static Logger LOG = LoggerFactory.getLogger(MediumConnectorConfig.class);
    private String mediumFeedUrl;

    @Activate
    @Modified
    protected void configureProperties(final Config config) {
        mediumFeedUrl = config.medium_feed_url();
        LOG.info("MediumConnectorConfigImpl configuration updated.");
    }

    @Override
    public String getMediumFeedUrl() {
        return mediumFeedUrl;
    }

    @ObjectClassDefinition(name = "MediumConnectorConfig",
            description = "Configure the Medium feed URL.")
    public static @interface Config {
        @AttributeDefinition(name = "Medium Feed URL")
        String medium_feed_url() default "https://medium.com/ida-mediafoundry/latest?format=json&limit=30";
    }
}


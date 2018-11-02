package be.ida.medium.job;

import be.ida.medium.connector.MediumConnector;
import be.ida.medium.parser.MediumRssFeedParser;
import be.ida.medium.repository.impl.MediumRepositoryImpl;
import be.ida.medium.repository.MediumRepository;
import be.ida.medium.service.impl.MediumServiceImpl;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd=MediumImportJob.Config.class)
@Component(service=Runnable.class)
public class MediumImportJob implements Runnable {

    @ObjectClassDefinition(name="MediumImportJob",
                           description = "Initiates Medium rss feed import workflow")
    public static @interface Config {

        @AttributeDefinition(name = "Cron-job expression")
        String scheduler_expression() default "*/30 * * * * ?";

        @AttributeDefinition(name = "Concurrent task",
                description = "Whether or not to schedule this task concurrently")
        boolean scheduler_concurrent() default false;

        @AttributeDefinition(name = "Enabled", description = "Enable Scheduler", type = AttributeType.BOOLEAN)
        boolean serviceEnabled() default true;

    }

    private final Logger logger = LoggerFactory.getLogger(getClass());
    MediumConnector mediumConnector = null;

    @Activate
    protected void activate(final Config config) {
        logger.info("MediumImportJob runnable activated.");

        MediumRssFeedParser mediumRssFeedParser = new MediumRssFeedParser();
        MediumRepository mediumRepository = new MediumRepositoryImpl();
        MediumServiceImpl mediumService = new MediumServiceImpl(mediumRepository);

        mediumConnector = new MediumConnector(mediumService, mediumRssFeedParser);
    }

    @Override
    public void run() {
        logger.info("MediumImportJob runnable is being triggered.");

        if(mediumConnector != null){
            mediumConnector.process();
        }
    }
}

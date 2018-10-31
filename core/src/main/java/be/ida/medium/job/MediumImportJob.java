package be.ida.medium.job;

import be.ida.medium.connector.MediumConnector;
import be.ida.medium.parser.MediumRssFeedParser;
import be.ida.medium.repository.MediumAEMRepository;
import be.ida.medium.repository.MediumRepository;
import be.ida.medium.service.MediumService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd= MediumImportJob.Config.class)
@Component(service=Runnable.class)
public class MediumImportJob implements Runnable {

    @ObjectClassDefinition(name="MediumImportJob",
                           description = "Initiates Medium rss feed import workflow")
    public static @interface Config {

        @AttributeDefinition(name = "scheduler.expression")
        String scheduler_expression() default "*/1 * * * * ?";

        @AttributeDefinition(name = "scheduler.concurrent",
                             description = "Whether or not to schedule this task concurrently")
        boolean scheduler_concurrent() default false;
    }

    private static final Logger LOG = LoggerFactory.getLogger(MediumImportJob.class);

    MediumConnector mediumConnector = null;

    @Activate
    protected void activate() {
        LOG.info("MediumImportJob runnable activated.");

        MediumRssFeedParser mediumRssFeedParser = new MediumRssFeedParser();
        MediumRepository mediumRepository = new MediumAEMRepository();
        MediumService mediumService = new MediumService(mediumRepository);

        mediumConnector = new MediumConnector(mediumService, mediumRssFeedParser);
    }

    @Override
    public void run() {
        LOG.info("MediumImportJob runnable is being triggered.");

        mediumConnector.process();
    }
}

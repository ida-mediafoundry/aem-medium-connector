package be.ida.medium.job;

import be.ida.medium.connector.MediumConnector;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd=MediumImportJob.Config.class)
@Component(service=Runnable.class)
public class MediumImportJob implements Runnable {
    private final static Logger LOG = LoggerFactory.getLogger(MediumImportJob.class);

    @Reference
    private MediumConnector mediumConnector;

    @Override
    public void run() {
        LOG.info("MediumImportJob runnable is being triggered.");

        mediumConnector.process();
    }

    @Activate
    protected void activate(final Config config) {
        LOG.info("MediumImportJob runnable activated.");
    }

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
}

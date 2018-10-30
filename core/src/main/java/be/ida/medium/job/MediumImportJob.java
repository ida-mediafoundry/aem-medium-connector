/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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

        @AttributeDefinition(name = "Cron-job expression")
        String scheduler_expression() default "*/1 * * * * ?";

        @AttributeDefinition(name = "Concurrent task",
                             description = "Whether or not to schedule this task concurrently")
        boolean scheduler_concurrent() default false;
    }

    private final Logger LOG = LoggerFactory.getLogger(getClass());

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

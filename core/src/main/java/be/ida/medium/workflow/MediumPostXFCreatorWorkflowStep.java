package be.ida.medium.workflow;


import be.ida.medium.model.MediumPost;
import be.ida.medium.service.MediumService;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = WorkflowProcess.class)
public class MediumPostXFCreatorWorkflowStep implements WorkflowProcess {
    private static final Logger LOG = LoggerFactory.getLogger(MediumPostXFCreatorWorkflowStep.class);

    protected String PAYLOAD_RESOURCE_PATH = "payloadResourcePath";

    @Reference
    MediumService mediumservice;

    @Override
    public void execute( final WorkItem workItem, final WorkflowSession workflowSession, final MetaDataMap metaDataMap ) throws WorkflowException {
        LOG.info("MediumPost node is about to get processed");
        final String resourcePath = workItem.getWorkflowData().getPayload().toString();

        if ( StringUtils.isNotEmpty(resourcePath) ) {
            final String mediumPublicationId = extractMediumPublicationId(resourcePath);

            if ( mediumPublicationId != null ) {
                final MediumPost mediumPost = mediumservice.getMediumPost(mediumPublicationId, getMediumPostId(resourcePath));

                if ( mediumPost != null ) {
                    LOG.info("mediumPost about to get conversed");


                }
            }
        }
    }

    private String getMediumPostId( final String resourcePath ) {
        return StringUtils.substringAfterLast(resourcePath, "/");
    }

    private String extractMediumPublicationId( final String resourcePath ) {
        String mediumPublicationId = null;
        final String postInfoExcluded = StringUtils.substringBeforeLast(resourcePath, "/posts");

        if ( StringUtils.isNotEmpty(postInfoExcluded) ) {
            mediumPublicationId = getMediumPostId(postInfoExcluded);
        }
        return mediumPublicationId;
    }
}



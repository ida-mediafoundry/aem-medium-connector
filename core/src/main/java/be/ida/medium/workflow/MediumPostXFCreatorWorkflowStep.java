package be.ida.medium.workflow;


import be.ida.medium.model.MediumPublication;
import be.ida.medium.service.MediumService;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
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
        final MetaDataMap transferredMetaDataMap = workItem.getWorkflowData().getMetaDataMap();
        final String resourcePath = workItem.getWorkflowData().getPayload().toString();

        if ( StringUtils.isNotEmpty(resourcePath) ) {
            final MediumPublication mediumPublication = mediumservice.getMediumPublication(resourcePath);
            if ( mediumPublication != null ) {

            }
        }

    }
}



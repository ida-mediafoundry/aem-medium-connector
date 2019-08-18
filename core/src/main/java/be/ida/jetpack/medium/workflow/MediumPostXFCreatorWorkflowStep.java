package be.ida.jetpack.medium.workflow;


import be.ida.jetpack.medium.model.MediumPost;
import be.ida.jetpack.medium.service.MediumService;
import be.ida.jetpack.medium.service.MediumXFManagerService;
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

    @Reference
    MediumXFManagerService mediumXfManagerService;

    @Override
    public void execute(final WorkItem workItem, final WorkflowSession workflowSession, final MetaDataMap metaDataMap) throws WorkflowException {
        LOG.info("MediumPost node is about to get processed");
        final String mediumPostPath = workItem.getWorkflowData().getPayload().toString();

        if (StringUtils.isNotEmpty(mediumPostPath)) {
                final MediumPost mediumPost = mediumservice.getMediumPostByPath(mediumPostPath);

                if (mediumPost != null) {
                    LOG.info("mediumPost about to get converted to experience fragment.");

                    mediumXfManagerService.createMediumXF(mediumPost);
                }
        }
    }
}



package be.ida.medium.repository.impl;

import be.ida.medium.bean.MediumPost;
import be.ida.medium.bean.MediumPublication;
import be.ida.medium.connector.MediumConnector;
import be.ida.medium.repository.MediumRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.vault.util.JcrConstants;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static be.ida.medium.model.MediumPostModel.*;

@Component(name="Medium Repository", service= MediumRepository.class, immediate=true)
public class MediumRepositoryImpl implements MediumRepository{
    private final static Logger LOG = LoggerFactory.getLogger(MediumConnector.class);

    // TODO make configurable
    private static final String DEFAULT_USER = "medium-service-user";
    private static final String DEFAULT_SERVICE = "medium-service-user";

    public static final String JCR_CONTENT_BASE_PATH = "/content/data/medium/";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void storeMediumPublication(MediumPublication mediumPublication) {
        try(ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(getCredentials())){
            Resource mediumResource = resourceResolver.getResource(getPublicationFolder(mediumPublication));

            if(mediumResource == null){
                mediumResource = createMediumResource(resourceResolver, mediumPublication);
            }

            for (MediumPost mediumPost : mediumPublication.getPosts()) {
                try{
                    resourceResolver.create(mediumResource, mediumPost.getId(), extractProperties(mediumPost));
                } catch ( PersistenceException e) {
                    LOG.error("Could not create new node in JCR", e);
                }
            }
            resourceResolver.commit();
        }
        catch (LoginException e) {
            LOG.error("Could not open ResourceResolver properly", e);
        } catch ( PersistenceException e) {
            LOG.error("Could not commit changes to JCR", e);
        }
    }

    private Resource createMediumResource(ResourceResolver resourceResolver, MediumPublication mediumPublication) {
        String pathWithPubTitle = getPublicationFolder(mediumPublication);
        String[] nodesList = StringUtils.split(pathWithPubTitle, "/");
        Iterator<String> nodesIterator = Arrays.stream(nodesList).iterator();

        Resource resource = resourceResolver.getResource("/" + nodesIterator.next());

        while(nodesIterator.hasNext()){
            String currentNode = nodesIterator.next();
            String newPath = resource.getPath() + "/" + currentNode;
            try {
                resourceResolver.create(resource, currentNode, new HashMap<>());
            } catch (PersistenceException e) {
                LOG.error("Unable to create node: " + currentNode, e);
            }
            resource = resourceResolver.getResource(newPath);
        }

        return resource;
    }

    private String getPublicationFolder(MediumPublication mediumPublication) {
        return JCR_CONTENT_BASE_PATH + mediumPublication.getName();
    }

    private Map<String, Object> getCredentials() {
        Map<String, Object> credentials = new HashMap<>();

        credentials.put(ResourceResolverFactory.USER, DEFAULT_USER);
        credentials.put(ResourceResolverFactory.SUBSERVICE, DEFAULT_SERVICE);

        return credentials;
    }



    private Map<String, Object> extractProperties(MediumPost mediumPost) {
        Map<String, Object> properties = new HashMap<>();

        properties.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);
        properties.put(MEDIUM_POST_TITLE, mediumPost.getTitle());
        properties.put(MEDIUM_POST_LINK, mediumPost.getLink());
        properties.put(MEDIUM_POST_IMAGE_SOURCE, mediumPost.getImageSource());
        properties.put(MEDIUM_POST_CREATOR, mediumPost.getCreator());
        properties.put(MEDIUM_POST_PUBLICATION_DATE, mediumPost.getPublicationDate());
        properties.put(MEDIUM_POST_ID, mediumPost.getId());

        return properties;
    }
}

package be.ida.medium.repository.impl;

import be.ida.medium.bean.MediumPost;
import be.ida.medium.connector.MediumConnector;
import be.ida.medium.repository.MediumRepository;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static be.ida.medium.model.MediumPostModel.*;

@Component(name="Medium Repository", service= MediumRepository.class, immediate=true)
public class MediumRepositoryImpl implements MediumRepository{
    private final static Logger LOG = LoggerFactory.getLogger(MediumConnector.class);

    // TODO make configurable
    private static final String DEFAULT_USER = "medium-service-user";
    private static final String DEFAULT_SERVICE = "medium-service-user";

    public static final String JCR_CONTENT_BASE_PATH = "/content/data/medium";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void storeMediumPost(MediumPost mediumPost) {
        try(ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(getCredentials())){
            Resource publicationResource = resourceResolver.getResource(getPublicationFolder());

            if(publicationResource != null){
                publicationResource = createPublicationResource(resourceResolver);
            }

            resourceResolver.create(publicationResource, mediumPost.getTitle(), extractProperties(mediumPost));
            resourceResolver.commit();
        } catch (LoginException | PersistenceException e) {
            LOG.error("Impossible to store medium post for link {}", mediumPost.getLink(), e);
        }
    }

    private Resource createPublicationResource(ResourceResolver resourceResolver) {
        Resource resource = null;
        //TODO: create publicationResource if it doesnt exist yet.
        return resource;
    }

    private String getPublicationFolder() {
        // TODO retrieve publicationName
        return JCR_CONTENT_BASE_PATH + "/publicationName";
    }

    private Map<String, Object> getCredentials() {
        Map<String, Object> credentials = new HashMap<>();

        credentials.put(ResourceResolverFactory.USER, DEFAULT_USER);
        credentials.put(ResourceResolverFactory.SUBSERVICE, DEFAULT_SERVICE);

        return credentials;
    }

    private Map<String, Object> extractProperties(MediumPost mediumPost) {
        Map<String, Object> properties = new HashMap<>();

        properties.put(MEDIUM_POST_TITLE, mediumPost.getTitle());
        properties.put(MEDIUM_POST_LINK, mediumPost.getLink());
        properties.put(MEDIUM_POST_IMAGE_SOURCE, mediumPost.getImageSource());
        properties.put(MEDIUM_POST_CREATOR, mediumPost.getCreator());
        properties.put(MEDIUM_POST_PUBLICATION_DATE, mediumPost.getPublicationDate());
        properties.put(MEDIUM_POST_ID, mediumPost.getId());
        return properties;
    }
}

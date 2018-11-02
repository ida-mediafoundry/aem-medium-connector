package be.ida.medium.repository.impl;

import be.ida.medium.connector.MediumConnector;
import be.ida.medium.model.MediumPost;
import be.ida.medium.repository.MediumRepository;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component(name="Medium Repository", service= MediumRepository.class, immediate=true)
public class MediumRepositoryImpl implements MediumRepository{
    private final static Logger LOG = LoggerFactory.getLogger(MediumConnector.class);

    // TODO make configurable
    private static final String DEFAULT_USER = "admin";
    private static final String DEFAULT_SERVICE = "admin";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void storeMediumPost(MediumPost mediumPost) {
        try(ResourceResolver resourceResolver = resourceResolverFactory.getResourceResolver(getCredentials())){
            Resource publicationResource = resourceResolver.getResource(getPublicationFolder());

            if(publicationResource != null){
                resourceResolver.create(publicationResource, "postName", extractProperties(mediumPost));
                resourceResolver.commit();
            }
        } catch (LoginException | PersistenceException e) {
            LOG.error("Impossible to store medium post for link {}", mediumPost.getLink(), e);
        }
    }

    private String getPublicationFolder() {
        // TODO "/content/data/medium/<e.g. ida>"
        return "/content";
    }

    private Map<String, Object> getCredentials() {
        Map<String, Object> credentials = new HashMap<>();
        credentials.put(ResourceResolverFactory.USER, DEFAULT_USER);
        credentials.put(ResourceResolverFactory.SUBSERVICE, DEFAULT_SERVICE);
        return credentials;
    }

    private Map<String, Object> extractProperties(MediumPost mediumPost) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("postTitle", mediumPost.getTitle());
        properties.put("postImageSource", mediumPost.getImageSource());
        properties.put("postCreator", mediumPost.getCreator());
        properties.put("postLink", mediumPost.getLink());
        properties.put("postDate", mediumPost.getPublicationDate());

        return properties;
    }
}

package be.ida.medium.repository;

import be.ida.medium.model.MediumPost;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Map;

@Component(name="Jcr Medium Repository", service=MediumRepository.class, immediate=true)
public class JcrMediumRepository implements MediumRepository{
    private static final String DEFAULT_USER = "admin";
    private static final String DEFAULT_SERVICE = "admin";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void storeMediumPost(MediumPost mediumPublication) {
        try(ResourceResolver resourceResolver = resourceResolverFactory.getResourceResolver(getCredentials())){
            Resource publicationResource = resourceResolver.getResource("/content");

            if(publicationResource != null){
                resourceResolver.create(publicationResource, "postName", getProperties(mediumPublication));
                resourceResolver.commit();
            }
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> getCredentials() {
        Map<String, Object> credentials = new HashMap<>();
        credentials.put(ResourceResolverFactory.USER, DEFAULT_USER);
        credentials.put(ResourceResolverFactory.SUBSERVICE, DEFAULT_SERVICE);
        return credentials;
    }

    private Map<String, Object> getProperties(MediumPost mediumPublication) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("postTitle", mediumPublication.getTitle());
        properties.put("postImageSource", mediumPublication.getImageSource());
        properties.put("postCreator", mediumPublication.getCreator());
        properties.put("postLink", mediumPublication.getLink());
        properties.put("postDate", mediumPublication.getPublicationDate());
        return properties;
    }
}

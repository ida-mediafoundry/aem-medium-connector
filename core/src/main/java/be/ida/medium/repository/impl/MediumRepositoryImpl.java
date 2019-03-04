package be.ida.medium.repository.impl;

import be.ida.medium.connector.MediumConnector;
import be.ida.medium.model.MediumPost;
import be.ida.medium.model.MediumPublication;
import be.ida.medium.repository.MediumRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.vault.util.JcrConstants;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static be.ida.medium.model.MediumPost.*;


@Component(name = "Medium Repository", service = MediumRepository.class, immediate = true)
public class MediumRepositoryImpl implements MediumRepository {
    public static final String JCR_CONTENT_BASE_PATH = "/content/data/medium/";
    private final static Logger LOG = LoggerFactory.getLogger(MediumConnector.class);
    // TODO make configurable
    private static final String DEFAULT_USER = "medium-service-user";
    private static final String DEFAULT_SERVICE = "medium-service-user";
    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void storeMediumPublication( final MediumPublication mediumPublication ) {
        try ( final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(getCredentials()) ) {
            Resource mediumResource = resourceResolver.getResource(getPublicationFolder(mediumPublication));

            if ( mediumResource == null ) {
                mediumResource = createMediumResource(resourceResolver, mediumPublication);
                setPublicationNodeName(mediumPublication, resourceResolver);
            }

            for ( final MediumPost mediumPost : mediumPublication.getPosts() ) {
                try {
                    resourceResolver.create(mediumResource, mediumPost.getId(), extractProperties(mediumPost));
                    LOG.info("Node created for: " + mediumPost.getId());
                } catch ( final PersistenceException e ) {
                    LOG.info("Node already exists for: " + mediumPost.getId());
                }
            }
            resourceResolver.commit();
        } catch ( final LoginException e ) {
            LOG.error("Could not open ResourceResolver properly", e);
        } catch ( final PersistenceException e ) {
            LOG.error("Could not commit changes to JCR", e);
        }
    }

    @Override
    public MediumPublication getMediumPublication( final String mediumPublicationId ) {
        MediumPublication mediumPublication = new MediumPublication();

        try ( final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(getCredentials()) ) {
            mediumPublication = Optional.ofNullable(resourceResolver.getResource(JCR_CONTENT_BASE_PATH + mediumPublicationId))
                    .map(resource -> resource.adaptTo(MediumPublication.class))
                    .orElse(null);
        } catch ( final LoginException e ) {
            LOG.error("Could not open ResourceResolver properly", e);
        }

        return mediumPublication;
    }

    //TODO: work with lucine index, only mediumPostId is needed as param
    @Override
    public MediumPost getMediumPost( final String mediumPublicationId, final String mediumPostId ) {
        final MediumPublication mediumPublication = getMediumPublication(mediumPublicationId);
        return mediumPublication.getPosts().stream().filter(post -> post.getId().equals(mediumPostId)).findFirst().orElse(null);
    }

    private Resource createMediumResource( final ResourceResolver resourceResolver, final MediumPublication mediumPublication ) {
        final String pathWithPubTitle = getPublicationFolder(mediumPublication);
        final String[] nodesList = StringUtils.split(pathWithPubTitle, "/");

        final Iterator<String> nodesIterator = Arrays.stream(nodesList).iterator();


        Resource resource = resourceResolver.getResource("/" + nodesIterator.next());

        while ( nodesIterator.hasNext() ) {
            final String currentNode = nodesIterator.next();
            final String newPath = resource.getPath() + "/" + currentNode;
            try {
                resourceResolver.create(resource, currentNode, new HashMap<>());
            } catch ( final PersistenceException e ) {
                LOG.error("Unable to create node: " + currentNode, e);
            }
            resource = resourceResolver.getResource(newPath);
        }

        return resource;
    }

    private String getPublicationFolder( final MediumPublication mediumPublication ) {
        return JCR_CONTENT_BASE_PATH + mediumPublication.getId() + "/posts";
    }

    private Map<String, Object> getCredentials() {
        final Map<String, Object> credentials = new HashMap<>();

        credentials.put(ResourceResolverFactory.USER, DEFAULT_USER);
        credentials.put(ResourceResolverFactory.SUBSERVICE, DEFAULT_SERVICE);

        return credentials;
    }

    private Map<String, Object> extractProperties( final MediumPost mediumPost ) {
        final Map<String, Object> properties = new HashMap<>();

        final LocalDate publicationDate =
                Instant.ofEpochMilli(mediumPost.getPublicationDate()).atZone(ZoneId.systemDefault()).toLocalDate();

        final LocalDate updateDate =
                Instant.ofEpochMilli(mediumPost.getUpdateDate()).atZone(ZoneId.systemDefault()).toLocalDate();

        properties.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);
        properties.put(MEDIUM_POST_TITLE, mediumPost.getTitle());
        properties.put(MEDIUM_POST_LINK, mediumPost.getLink());
        properties.put(MEDIUM_POST_IMAGE_SOURCE, mediumPost.getImageSource());
        properties.put(MEDIUM_POST_CREATOR, mediumPost.getCreator());
        properties.put(MEDIUM_POST_PUBLICATION_DATE, mediumPost.getPublicationDate());
        properties.put(MEDIUM_POST_UPDATE_DATE, mediumPost.getPublicationDate());
        properties.put(MEDIUM_POST_ID, mediumPost.getId());

        return properties;
    }

    private void setPublicationNodeName( final MediumPublication mediumPublication, final ResourceResolver resourceResolver ) {
        final Resource resource = resourceResolver.getResource(JCR_CONTENT_BASE_PATH + mediumPublication.getId());
        final ModifiableValueMap map = resource.adaptTo(ModifiableValueMap.class);
        map.put("name", mediumPublication.getName());
    }


}

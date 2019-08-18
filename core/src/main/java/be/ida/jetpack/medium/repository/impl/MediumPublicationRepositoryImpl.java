package be.ida.jetpack.medium.repository.impl;

import be.ida.jetpack.medium.model.MediumPost;
import be.ida.jetpack.medium.model.MediumPublication;
import be.ida.jetpack.medium.repository.MediumPublicationRepository;
import org.apache.jackrabbit.vault.util.JcrConstants;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static be.ida.jetpack.medium.MediumConstants.*;
import static be.ida.jetpack.medium.model.MediumPost.*;


@Component(name = "Medium Publication Repository", service = MediumPublicationRepository.class, immediate = true)
public class MediumPublicationRepositoryImpl implements MediumPublicationRepository {
    private final static Logger LOG = LoggerFactory.getLogger(MediumPublicationRepository.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    static Map<String, Object> getCredentials() {
        final Map<String, Object> credentials = new HashMap<>();

        credentials.put(ResourceResolverFactory.USER, USER);
        credentials.put(ResourceResolverFactory.SUBSERVICE, SERVICE);

        return credentials;
    }

    @Override
    public void storeMediumPublication(final MediumPublication mediumPublication) {
        try (final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(getCredentials())) {
            String mediumPublicationFolder = getPublicationFolder(mediumPublication);
            Resource mediumPublicationResource = resourceResolver.getResource(mediumPublicationFolder);

            if (mediumPublicationResource == null) {
                Map<String, Object> publicationProperties = new HashMap<>();
                publicationProperties.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);
                publicationProperties.put("name", mediumPublication.getName());
                publicationProperties.put("id", mediumPublication.getId());

                publicationProperties.put("sling:resourceType", RESOURCE_TYPE_MEDIUM_PUBLICATION);

                mediumPublicationResource = ResourceUtil.getOrCreateResource(
                        resourceResolver,
                        mediumPublicationFolder,
                        publicationProperties,
                        "nt:unstructured",
                        true);
            }

            for (final MediumPost mediumPost : mediumPublication.getPosts()) {
                try {
                    resourceResolver.create(mediumPublicationResource, mediumPost.getId(), getPostProperties(mediumPost, mediumPublication.getId()));
                    LOG.info("Node created for: " + mediumPost.getId());
                } catch (final PersistenceException e) {
                    LOG.info("Node already exists for: " + mediumPost.getId());
                }
            }
            resourceResolver.commit();
        } catch (final LoginException e) {
            LOG.error("Could not open ResourceResolver properly", e);
        } catch (final PersistenceException e) {
            LOG.error("Could not commit changes to JCR", e);
        }
    }

    @Override
    public MediumPublication getMediumPublication(final String mediumPublicationId) {
        MediumPublication mediumPublication = new MediumPublication();

        try (final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(getCredentials())) {
            mediumPublication = Optional.ofNullable(resourceResolver.getResource(MEDIUM_ROOT + mediumPublicationId))
                    .map(resource -> resource.adaptTo(MediumPublication.class))
                    .orElse(null);
        } catch (final LoginException e) {
            LOG.error("Could not open ResourceResolver properly", e);
        }

        return mediumPublication;
    }

    private String getPublicationFolder(final MediumPublication mediumPublication) {
        return MEDIUM_ROOT + mediumPublication.getId();
    }

    private Map<String, Object> getPostProperties(final MediumPost mediumPost, String publicationId) {
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
        properties.put(MEDIUM_PUBLICATION_ID, publicationId);
        properties.put(SLING_RESOURCE_TYPE, RESOURCE_TYPE_MEDIUM_POST);

        return properties;
    }
}

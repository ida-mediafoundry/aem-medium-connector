package be.ida.medium.repository.impl;

import be.ida.medium.model.MediumPost;
import be.ida.medium.repository.MediumXFManagerRepository;
import be.ida.medium.util.MediumResourceUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static be.ida.medium.model.MediumPost.*;

@Component(name = "XF Manager Repo", service = MediumXFManagerRepository.class, immediate = true)
public class MediumXFManagerRepositoryImpl implements MediumXFManagerRepository {
    private static final String EXPERIENCE_FRAGMENT_BASE_PATH = "/content/experience-fragments/medium/%s/posts";
    private final static Logger LOG = LoggerFactory.getLogger(MediumXFManagerRepository.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void createMediumXF( final MediumPost mediumPost, final String mediumPublicationId ) {
        try ( final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(MediumRepositoryImpl.getCredentials()) ) {
            final Resource baseResource = MediumResourceUtil.createResourceByPath(resourceResolver, String.format(EXPERIENCE_FRAGMENT_BASE_PATH, mediumPublicationId));

            if ( baseResource != null ) {
                final PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

                if ( pageManager != null ) {
                    final Resource resource = resourceResolver.getResource(baseResource.getPath() + "/" + mediumPost.getId());

                    if ( resource == null ) {
                        final Page experienceFragment = pageManager.create(baseResource.getPath(), mediumPost.getId(), "/libs/cq/experience-fragments/components/experiencefragment/template", mediumPost.getTitle());

                        final Page experienceFragmentVariation = pageManager.create(experienceFragment.getPath(), "medium-master-variation", "/apps/settings/experience-fragments/templates/experience-fragment-template-medium", mediumPost.getTitle());

                        resolveVariationProperties(experienceFragmentVariation, mediumPost);

                        resourceResolver.commit();
                    }

                }
            }

        } catch ( final LoginException e ) {
            LOG.error("Could not open ResourceResolver properly", e);
        } catch ( final WCMException e ) {
            e.printStackTrace();
        } catch ( final PersistenceException e ) {
            e.printStackTrace();
        }
    }

    private void resolveVariationProperties( final Page experienceFragmentVariation, final MediumPost mediumPost ) {
        final Resource variationContentResource = experienceFragmentVariation.getContentResource();

        if ( variationContentResource != null ) {
            final Resource variationRootResource = variationContentResource.getChild("root");

            if ( variationRootResource != null ) {
                updateSpecificVariationNode(variationRootResource, MEDIUM_POST_CREATOR, mediumPost.getId());
                updateSpecificVariationNode(variationRootResource, MEDIUM_POST_CREATOR, mediumPost.getCreator());
                updateSpecificVariationNode(variationRootResource, MEDIUM_POST_IMAGE_SOURCE, mediumPost.getImageSource());
                updateSpecificVariationNode(variationRootResource, MEDIUM_POST_LINK, mediumPost.getLink());
                updateSpecificVariationNode(variationRootResource, MEDIUM_POST_PUBLICATION_DATE, mediumPost.getPublicationDate());
                updateSpecificVariationNode(variationRootResource, MEDIUM_POST_TITLE, mediumPost.getTitle());
                updateSpecificVariationNode(variationRootResource, MEDIUM_POST_UPDATE_DATE, mediumPost.getUpdateDate());
            }
        }
    }

    private void updateSpecificVariationNode( final Resource variationRootResource, final String childName, final Object value ) {
        final ModifiableValueMap modifiableValueMap = variationRootResource.getChild(childName).adaptTo(ModifiableValueMap.class);

        if ( modifiableValueMap != null ) {
            modifiableValueMap.put("text", value);
        }
    }
}

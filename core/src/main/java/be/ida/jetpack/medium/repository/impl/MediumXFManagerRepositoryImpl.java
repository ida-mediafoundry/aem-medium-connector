package be.ida.jetpack.medium.repository.impl;

import be.ida.jetpack.medium.model.MediumPost;
import be.ida.jetpack.medium.repository.MediumXFManagerRepository;
import be.ida.jetpack.medium.strategy.AbstractXFVariationCreationStrategy;
import be.ida.jetpack.medium.strategy.XFRegularVariationCreationStrategy;
import be.ida.jetpack.medium.strategy.XFRowVariationCreationStrategy;
import be.ida.jetpack.medium.strategy.XFTileVariationCreationStrategy;
import be.ida.jetpack.medium.util.MediumResourceUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(name = "XF Manager Repo", service = MediumXFManagerRepository.class, immediate = true)
public class MediumXFManagerRepositoryImpl implements MediumXFManagerRepository {
    private static final String EXPERIENCE_FRAGMENT_BASE_PATH = "/content/experience-fragments/medium/%s";
    private final static Logger LOG = LoggerFactory.getLogger(MediumXFManagerRepository.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void createMediumXF(final MediumPost mediumPost) {
        try (final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(MediumPublicationRepositoryImpl.getCredentials())) {
            if (mediumPost != null) {
                String experienceFragmentRootPath = String.format(EXPERIENCE_FRAGMENT_BASE_PATH, mediumPost.getPublicationId());
                final Resource experienceFragmentRootResource = ResourceUtil.getOrCreateResource(
                        resourceResolver,
                        experienceFragmentRootPath,
                        "",
                        "",
                        true);

                final PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

                if (pageManager != null) {
                    final Resource resource = resourceResolver.getResource(experienceFragmentRootResource.getPath() + "/" + mediumPost.getId());

                    if (resource == null) {
                        final Page experienceFragment = pageManager.create(experienceFragmentRootResource.getPath(), mediumPost.getId(), "/libs/cq/experience-fragments/components/experiencefragment/template", mediumPost.getTitle());

                        resourceResolver.getResource("/conf/medium/settings/wcm/templates/experience-fragment-template-medium-row/initial");

                        final AbstractXFVariationCreationStrategy rowCreationStrategy = new XFRowVariationCreationStrategy();
                        rowCreationStrategy.createXFVariation(experienceFragment, mediumPost, pageManager);

                        final AbstractXFVariationCreationStrategy tileCreationStrategy = new XFTileVariationCreationStrategy();
                        tileCreationStrategy.createXFVariation(experienceFragment, mediumPost, pageManager);

                        final AbstractXFVariationCreationStrategy regularCreationStrategy = new XFRegularVariationCreationStrategy();
                        regularCreationStrategy.createXFVariation(experienceFragment, mediumPost, pageManager);

                        resourceResolver.commit();
                    }
                }
            }
        } catch (final LoginException e) {
            LOG.error("Could not open ResourceResolver properly", e);
        } catch (final WCMException | PersistenceException e) {
            LOG.error("Could not persist properly", e);
        }
    }
}

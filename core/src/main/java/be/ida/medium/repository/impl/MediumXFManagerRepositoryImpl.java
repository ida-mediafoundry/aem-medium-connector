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

@Component(name = "XF Manager Repo", service = MediumXFManagerRepository.class, immediate = true)
public class MediumXFManagerRepositoryImpl implements MediumXFManagerRepository {
    private static final String EXPERIENCE_FRAGMENT_BASE_PATH = "/content/experience-fragments/medium/%s/posts";
    private final static Logger LOG = LoggerFactory.getLogger(MediumXFManagerRepository.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void createMediumXF(MediumPost mediumPost, String mediumPublicationId) {
        try (final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(MediumRepositoryImpl.getCredentials())) {
            Resource baseResource = MediumResourceUtil.createResourceByPath(resourceResolver, String.format(EXPERIENCE_FRAGMENT_BASE_PATH, mediumPublicationId));

            if (baseResource != null) {
                PageManager pageManager = resourceResolver.adaptTo(PageManager.class);


                if (pageManager != null) {
                    Page experienceFragment = pageManager.create(baseResource.getPath(), mediumPost.getId(), "/libs/cq/experience-fragments/components/experiencefragment/template", mediumPost.getTitle());
                    Page experienceFragmentVariant = pageManager.create(experienceFragment.getPath(), "master", "/apps/settings/experience-fragments/templates/experience-fragment-template-medium", mediumPost.getTitle());
                    experienceFragmentVariant.
                }
            }

        } catch (final LoginException e) {
            LOG.error("Could not open ResourceResolver properly", e);
        } catch (WCMException e) {
            e.printStackTrace();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
}

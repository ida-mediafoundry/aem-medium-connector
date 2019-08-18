package be.ida.jetpack.medium.repository.impl;

import be.ida.jetpack.medium.connector.MediumConnector;
import be.ida.jetpack.medium.model.MediumPost;
import be.ida.jetpack.medium.model.MediumPublication;
import be.ida.jetpack.medium.repository.MediumPostRepository;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static be.ida.jetpack.medium.MediumConstants.*;


@Component(name = "Medium Post Repository", service = MediumPostRepository.class, immediate = true)
public class MediumPostRepositoryImpl implements MediumPostRepository {
    private final static Logger LOG = LoggerFactory.getLogger(MediumPostRepository.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    static Map<String, Object> getCredentials() {
        final Map<String, Object> credentials = new HashMap<>();

        credentials.put(ResourceResolverFactory.USER, USER);
        credentials.put(ResourceResolverFactory.SUBSERVICE, SERVICE);

        return credentials;
    }

    @Override
    public MediumPost getMediumPostByPath(final String mediumPostPath) {
        MediumPost mediumPost = null;

        try(ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(getCredentials())){
            Resource mediumPostResource = resourceResolver.getResource(mediumPostPath);
            mediumPost = mediumPostResource.adaptTo(MediumPost.class);
        } catch (LoginException e) {
            e.printStackTrace();
        }

        return mediumPost;
    }

}

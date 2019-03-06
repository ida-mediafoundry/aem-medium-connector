package be.ida.medium.strategy;

import be.ida.medium.model.MediumPost;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;

import static be.ida.medium.model.MediumPost.*;

public abstract class AbstractXFVariationCreationStrategy {
    final String templatePath;
    final String variationNodeName;

    public AbstractXFVariationCreationStrategy( final String templatePath, final String variationNodeName ) {
        this.templatePath = templatePath;
        this.variationNodeName = variationNodeName;
    }

    public void createXFVariation( final Page experienceFragment, final MediumPost mediumPost, final PageManager pageManager ) throws WCMException {
        final Page experienceFragmentVariation = pageManager.create(experienceFragment.getPath(), variationNodeName, templatePath, variationNodeName);

        resolveVariationProperties(experienceFragmentVariation, mediumPost);
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

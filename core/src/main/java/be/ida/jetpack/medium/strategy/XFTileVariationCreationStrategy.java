package be.ida.jetpack.medium.strategy;

public class XFTileVariationCreationStrategy extends AbstractXFVariationCreationStrategy {
    static final String TEMPLATE_PATH = "/conf/medium/settings/wcm/templates/experience-fragment-template-medium-tile";
    static final String VARIATION_NAME = "medium-tile-variation";

    public XFTileVariationCreationStrategy() {
        super(TEMPLATE_PATH, VARIATION_NAME);
    }

    //TODO: specific implementation of each variation strategy
}

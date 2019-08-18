package be.ida.jetpack.medium.strategy;

public class XFRegularVariationCreationStrategy extends AbstractXFVariationCreationStrategy {
    static final String TEMPLATE_PATH = "/conf/medium/settings/wcm/templates/experience-fragment-template-medium-regular";
    static final String VARIATION_NAME = "medium-regular-variation";

    public XFRegularVariationCreationStrategy() {
        super(TEMPLATE_PATH, VARIATION_NAME);
    }

    //TODO: specific implementation of each variation strategy
}

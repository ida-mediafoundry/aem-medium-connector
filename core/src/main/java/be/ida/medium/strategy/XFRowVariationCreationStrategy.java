package be.ida.medium.strategy;

public class XFRowVariationCreationStrategy extends AbstractXFVariationCreationStrategy {
    static final String TEMPLATE_PATH = "/conf/medium/settings/wcm/templates/experience-fragment-template-medium-row/initial";
    static final String VARIATION_NAME = "medium-row-variation";

    public XFRowVariationCreationStrategy() {
        super(TEMPLATE_PATH, VARIATION_NAME);
    }

    //TODO: specific implementation of each variation strategy
}

package be.ida.medium.strategy;

public class XFRegularVariationCreationStrategy extends AbstractXFVariationCreationStrategy {
    static final String TEMPLATE_PATH = "/apps/settings/experience-fragments/templates/experience-fragment-template-regular";
    static final String VARIATION_NAME = "medium-regular-variation";

    public XFRegularVariationCreationStrategy() {
        super(TEMPLATE_PATH, VARIATION_NAME);
    }

    //TODO: specific implementation of each variation strategy
}

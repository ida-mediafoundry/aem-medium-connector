package be.ida.medium.strategy;

public class XFRowVariationCreationStrategy extends AbstractXFVariationCreationStrategy {
    static final String TEMPLATE_PATH = "/apps/settings/experience-fragments/templates/experience-fragment-template-medium-row";
    static final String VARIATION_NAME = "medium-row-variation";

    public XFRowVariationCreationStrategy() {
        super(TEMPLATE_PATH, VARIATION_NAME);
    }

    //TODO: specific implementation of each variation strategy
}

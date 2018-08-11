package be.ida.component.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractComponent {

    protected Logger log = LoggerFactory.getLogger("Library");

    public abstract String getComponentName();

}

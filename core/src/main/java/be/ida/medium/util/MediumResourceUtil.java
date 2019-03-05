package be.ida.medium.util;

import be.ida.medium.connector.MediumConnector;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class MediumResourceUtil {
    private final static Logger LOG = LoggerFactory.getLogger(MediumConnector.class);

    public static Resource createResourceByPath(final ResourceResolver resourceResolver, final String path) throws PersistenceException {
        final String[] nodesList = StringUtils.split(path, "/");

        final Iterator<String> nodesIterator = Arrays.stream(nodesList).iterator();

        Resource resource = resourceResolver.getResource("/" + nodesIterator.next());

        while (nodesIterator.hasNext()) {
            final String currentNode = nodesIterator.next();
            final String newPath = resource.getPath() + "/" + currentNode;
            try {
                resourceResolver.create(resource, currentNode, new HashMap<>());
            } catch (final PersistenceException e) {
                LOG.error("Unable to create node: " + currentNode, e);
            }
            resource = resourceResolver.getResource(newPath);
        }

        resourceResolver.commit();

        return resource;
    }
}

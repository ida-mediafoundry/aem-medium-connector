package be.ida.jetpack.medium.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = Resource.class)
public class MediumPublication {

    @Inject
    private String name;

    @Inject
    private String id;

    @Inject @Optional
    private List<MediumPost> posts;

    public String getId() {
        return id;
    }

    public void setId( final String id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( final String name ) {
        this.name = name;
    }

    public List<MediumPost> getPosts() {
        return posts;
    }

    public void setPosts( final List<MediumPost> posts ) {
        this.posts = posts;
    }
}

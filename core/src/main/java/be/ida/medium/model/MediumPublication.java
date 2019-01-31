package be.ida.medium.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = Resource.class)
public class MediumPublication {

    @Inject
    private String name;

    @Inject
    private List<MediumPost> posts;

    private String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MediumPost> getPosts() {
        return posts;
    }

    public void setPosts(List<MediumPost> posts) {
        this.posts = posts;
    }
}

package be.ida.medium.bean;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Model(adaptables = Resource.class)
public class MediumPublication {

    @Inject
    @Named("path")
    private String name;

    @Inject
    private List<MediumPost> posts;

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

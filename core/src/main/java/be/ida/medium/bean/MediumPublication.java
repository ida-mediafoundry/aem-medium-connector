package be.ida.medium.bean;

import java.util.List;

public class MediumPublication {
    private String name;
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

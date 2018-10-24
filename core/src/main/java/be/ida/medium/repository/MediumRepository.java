package be.ida.medium.repository;

import be.ida.medium.model.MediumPost;

import java.util.List;

public interface MediumRepository {
    void storeMediumPost(List<MediumPost> mediumPosts);
}

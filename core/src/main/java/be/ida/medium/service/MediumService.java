package be.ida.medium.service;

import be.ida.medium.model.MediumPost;

import java.util.List;

public interface MediumService {
    void storeMediumPosts(List<MediumPost> mediumPosts);
}

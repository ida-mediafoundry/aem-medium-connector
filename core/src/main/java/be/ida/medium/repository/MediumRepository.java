package be.ida.medium.repository;

import be.ida.medium.model.MediumPublication;

import java.util.List;

public interface MediumRepository {
    void storeMediumPost(List<MediumPublication> mediumPublications);
}

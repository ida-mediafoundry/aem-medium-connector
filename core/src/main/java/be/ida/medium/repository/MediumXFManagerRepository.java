package be.ida.medium.repository;

import be.ida.medium.model.MediumPost;

public interface MediumXFManagerRepository {
    public void createMediumXF(MediumPost mediumPost, String mediumPublicationId);
}

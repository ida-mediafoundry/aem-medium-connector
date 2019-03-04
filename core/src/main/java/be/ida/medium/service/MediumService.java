package be.ida.medium.service;

import be.ida.medium.model.MediumPost;
import be.ida.medium.model.MediumPublication;

public interface MediumService {
    void storeMediumPublication( MediumPublication mediumPublication );

    MediumPublication getMediumPublication( String mediumPublicationId );

    MediumPost getMediumPost( String mediumPublicationId, String mediumPostId );
}

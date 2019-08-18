package be.ida.jetpack.medium.service;

import be.ida.jetpack.medium.model.MediumPost;
import be.ida.jetpack.medium.model.MediumPublication;

public interface MediumService {
    void storeMediumPublication( MediumPublication mediumPublication );

    MediumPublication getMediumPublication( String mediumPublicationId );

    MediumPost getMediumPostByPath( String mediumPostPath );
}

package be.ida.medium.repository;

import be.ida.medium.model.MediumPublication;

public interface MediumRepository {
    void storeMediumPublication(MediumPublication mediumPublication);

    MediumPublication getMediumPublication(String resourcePath);
}

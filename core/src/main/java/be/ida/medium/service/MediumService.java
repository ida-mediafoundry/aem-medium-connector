package be.ida.medium.service;

import be.ida.medium.bean.MediumPublication;

public interface MediumService {
    void storeMediumPublication(MediumPublication mediumPublication);

    MediumPublication getMediumPublication(String resourcePath);
}

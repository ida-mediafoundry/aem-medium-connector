package be.ida.medium.service;

import be.ida.medium.model.MediumPost;
import be.ida.medium.repository.MediumRepository;

public class MediumService {
    private MediumRepository mediumRepository;

    public MediumService(MediumRepository mediumRepository) {
        this.mediumRepository = mediumRepository;
    }

    public void storeMediumPost(MediumPost mediumPost){
        //TODO: business logic
        mediumRepository.storeMediumPost(mediumPost);
    }
}

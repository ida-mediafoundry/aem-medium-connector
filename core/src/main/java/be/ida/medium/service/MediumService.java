package be.ida.medium.service;

import be.ida.medium.model.MediumPost;
import be.ida.medium.repository.MediumAEMRepository;

public class MediumService {
    private MediumAEMRepository mediumRepository;

    public MediumService(MediumAEMRepository mediumRepository) {
        this.mediumRepository = mediumRepository;
    }

    public void storeMediumPost(MediumPost mediumPost){
        //TODO: business logic
        mediumRepository.storeMediumPost(mediumPost);
    }
}

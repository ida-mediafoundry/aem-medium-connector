package be.ida.medium.service;

import be.ida.medium.model.MediumPublication;
import be.ida.medium.repository.MediumRepository;

import java.util.List;

public class MediumService {
    private MediumRepository mediumRepository;

    public MediumService(MediumRepository mediumRepository) {
        this.mediumRepository = mediumRepository;
    }

    public void storeMediumPost(List<MediumPublication> mediumPublications){
        //TODO: business logic
        mediumRepository.storeMediumPost(mediumPublications);
    }
}

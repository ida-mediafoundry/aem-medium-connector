package be.ida.medium.service;

import be.ida.medium.model.MediumPost;
import be.ida.medium.repository.MediumRepository;

import java.util.List;

public class MediumService {
    private MediumRepository mediumRepository;

    public MediumService(MediumRepository mediumRepository) {
        this.mediumRepository = mediumRepository;
    }

    public void storeMediumPost(List<MediumPost> mediumPosts){
        //TODO: business logic
        mediumPosts.forEach(post -> mediumRepository.storeMediumPost(post));
    }
}

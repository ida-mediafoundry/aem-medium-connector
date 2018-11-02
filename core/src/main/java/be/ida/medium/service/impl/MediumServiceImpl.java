package be.ida.medium.service.impl;

import be.ida.medium.model.MediumPost;
import be.ida.medium.repository.MediumRepository;
import be.ida.medium.service.MediumService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(name="Medium Service", service= MediumService.class, immediate=true)
public class MediumServiceImpl implements MediumService{
    @Reference
    private MediumRepository mediumRepository;

    public MediumServiceImpl(MediumRepository mediumRepository) {
        this.mediumRepository = mediumRepository;
    }

    public void storeMediumPosts(List<MediumPost> mediumPosts){
        mediumPosts.forEach(post -> mediumRepository.storeMediumPost(post));
    }
}

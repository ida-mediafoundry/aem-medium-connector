package be.ida.medium.service.impl;

import be.ida.medium.model.MediumPost;
import be.ida.medium.model.MediumPublication;
import be.ida.medium.repository.MediumRepository;
import be.ida.medium.service.MediumService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(name = "Medium Service", service = MediumService.class, immediate = true)
public class MediumServiceImpl implements MediumService {
    @Reference
    private MediumRepository mediumRepository;

    public void storeMediumPublication( final MediumPublication mediumPublication ) {
        mediumRepository.storeMediumPublication(mediumPublication);
    }

    public MediumPublication getMediumPublication( final String mediumPublicationId ) {
        return mediumRepository.getMediumPublication(mediumPublicationId);

    }

    public MediumPost getMediumPost( final String mediumPublicationId, final String mediumPostId ) {
        return mediumRepository.getMediumPost(mediumPublicationId, mediumPostId);

    }
}

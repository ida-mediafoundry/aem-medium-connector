package be.ida.jetpack.medium.service.impl;

import be.ida.jetpack.medium.model.MediumPost;
import be.ida.jetpack.medium.model.MediumPublication;
import be.ida.jetpack.medium.repository.MediumPostRepository;
import be.ida.jetpack.medium.repository.MediumPublicationRepository;
import be.ida.jetpack.medium.service.MediumService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(name = "Medium Service", service = MediumService.class, immediate = true)
public class MediumServiceImpl implements MediumService {
    @Reference
    private MediumPublicationRepository mediumPublicationRepository;
    @Reference
    private MediumPostRepository mediumPostRepository;

    @Override
    public void storeMediumPublication( final MediumPublication mediumPublication ) {
        mediumPublicationRepository.storeMediumPublication(mediumPublication);
    }
    @Override
    public MediumPublication getMediumPublication( final String mediumPublicationId ) {
        return mediumPublicationRepository.getMediumPublication(mediumPublicationId);

    }

    @Override
    public MediumPost getMediumPostByPath( final String mediumPostPath ) {
        return mediumPostRepository.getMediumPostByPath(mediumPostPath);

    }
}

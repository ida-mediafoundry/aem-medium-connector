package be.ida.medium.service.impl;

import be.ida.medium.model.MediumPost;
import be.ida.medium.repository.MediumXFManagerRepository;
import be.ida.medium.service.MediumXFManagerService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(name = "XF Manager Service", service = MediumXFManagerService.class, immediate = true)
public class MediumXFManagerServiceImpl implements MediumXFManagerService {
    @Reference
    MediumXFManagerRepository mediumXfManagerRepository;

    @Override
    public void createMediumXF(MediumPost mediumPost, String mediumPublicationId) {
        mediumXfManagerRepository.createMediumXF(mediumPost, mediumPublicationId);
    }
}

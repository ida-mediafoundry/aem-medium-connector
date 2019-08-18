package be.ida.jetpack.medium.service.impl;

import be.ida.jetpack.medium.model.MediumPost;
import be.ida.jetpack.medium.repository.MediumXFManagerRepository;
import be.ida.jetpack.medium.service.MediumXFManagerService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(name = "XF Manager Service", service = MediumXFManagerService.class, immediate = true)
public class MediumXFManagerServiceImpl implements MediumXFManagerService {
    @Reference
    MediumXFManagerRepository mediumXfManagerRepository;

    @Override
    public void createMediumXF(MediumPost mediumPost) {
        mediumXfManagerRepository.createMediumXF(mediumPost);
    }
}

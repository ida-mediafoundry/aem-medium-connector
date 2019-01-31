package be.ida.medium.service.impl;

import be.ida.medium.model.MediumPublication;
import be.ida.medium.repository.MediumRepository;
import be.ida.medium.service.MediumService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(name = "Medium Service", service = MediumService.class, immediate = true)
public class MediumServiceImpl implements MediumService {
    @Reference
    private MediumRepository mediumRepository;

    public void storeMediumPublication(MediumPublication mediumPublication) {
        mediumRepository.storeMediumPublication(mediumPublication);
    }

    public MediumPublication getMediumPublication(String resourcePath) {
        return mediumRepository.getMediumPublication(resourcePath);

    }
}

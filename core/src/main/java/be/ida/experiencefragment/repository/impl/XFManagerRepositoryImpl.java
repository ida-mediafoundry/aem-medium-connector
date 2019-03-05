package be.ida.experiencefragment.repository.impl;

import be.ida.experiencefragment.repository.XFManagerRepository;
import org.osgi.service.component.annotations.Component;

@Component(name = "XF Manager Repo", service = XFManagerRepository.class, immediate = true)
public class XFManagerRepositoryImpl implements XFManagerRepository {
}

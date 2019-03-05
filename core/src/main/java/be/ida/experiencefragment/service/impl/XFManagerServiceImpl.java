package be.ida.experiencefragment.service.impl;

import be.ida.experiencefragment.service.XFManagerService;
import org.osgi.service.component.annotations.Component;

@Component(name = "XF Manager Service", service = XFManagerService.class, immediate = true)
public class XFManagerServiceImpl implements XFManagerService {
}

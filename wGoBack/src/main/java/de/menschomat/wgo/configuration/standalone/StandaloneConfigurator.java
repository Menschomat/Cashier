package de.menschomat.wgo.configuration.standalone;

import de.menschomat.wgo.database.model.User;
import de.menschomat.wgo.database.repositories.UserRepository;
import de.menschomat.wgo.rest.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Configurable
public class StandaloneConfigurator {
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        User standAlone = new User();
        standAlone.id = "standalone";
        System.out.println(standAlone);
        System.out.println(userRepository.toString());
        userRepository.save(standAlone);
    }
}

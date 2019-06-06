package de.menschomat.wgo.configuration.standalone;

import de.menschomat.wgo.database.model.DBUser;
import de.menschomat.wgo.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Configurable
public class StandaloneConfigurator {
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        DBUser standAlone = new DBUser();
        standAlone.id = "standalone";
        System.out.println(standAlone);
        System.out.println(userRepository.toString());
        userRepository.save(standAlone);
    }
}

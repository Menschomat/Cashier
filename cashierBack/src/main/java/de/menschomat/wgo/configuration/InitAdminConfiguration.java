package de.menschomat.wgo.configuration;

import de.menschomat.wgo.database.model.DBUser;
import de.menschomat.wgo.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Configurable
public class InitAdminConfiguration {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init() {
        if (userRepository.findAll().isEmpty()) {
            DBUser admin = new DBUser();
            admin.username = "admin";
            admin.password = bCryptPasswordEncoder.encode("admin123");
            admin.role = "ADMIN";
            userRepository.save(admin);
        }
    }
}

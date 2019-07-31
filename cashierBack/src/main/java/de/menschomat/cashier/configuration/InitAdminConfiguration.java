package de.menschomat.cashier.configuration;


import de.menschomat.cashier.database.jpa.model.DBUser;
import de.menschomat.cashier.database.jpa.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
public class InitAdminConfiguration {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public InitAdminConfiguration(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void init() {
        if (userRepository.findAll().isEmpty()) {
            Logger logger = LoggerFactory.getLogger(InitAdminConfiguration.class);
            DBUser admin = new DBUser();
            admin.setUsername("admin");
            String defaultPassword = "cashier";
            admin.setPassword(bCryptPasswordEncoder.encode(defaultPassword));
            admin.setRole("ADMIN");
            logger.info("ADMININIT:" + "[user:" + admin.getUsername() + "|password:" + defaultPassword + "]");
            userRepository.save(admin);
        }
    }
}

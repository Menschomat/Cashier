package de.menschomat.cashier.configuration;


import de.menschomat.cashier.database.jpa.model.DBUser;
import de.menschomat.cashier.database.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.annotation.PostConstruct;

@Configurable
public class InitAdminConfiguration {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public InitAdminConfiguration(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void init() {
        DBUser admin = new DBUser();
        admin.setUsername("admin");
        admin.setPassword(bCryptPasswordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        if (userRepository.findAll().isEmpty()) {
            userRepository.save(admin);
        }
    }
}

package de.menschomat.cashier.configuration;


import de.menschomat.cashier.database.jpa.model.DBUser;
import de.menschomat.cashier.database.jpa.model.Transaction;
import de.menschomat.cashier.database.jpa.repositories.TransactionRepository;
import de.menschomat.cashier.database.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
@Configurable
public class InitAdminConfiguration {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public InitAdminConfiguration(UserRepository userRepository, TransactionRepository transactionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void init() {
        DBUser admin = new DBUser();
        admin.setUsername("admin");
        admin.setPassword(bCryptPasswordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        if (userRepository.findAll().isEmpty()) {
            System.out.println("ADD_NEW");
            admin = userRepository.save(admin);
        }else{
            System.out.println("FOUND");
            admin = userRepository.findByUsername("admin");
        }
        Transaction toAdd = new Transaction();
        toAdd.setDate(new Date());
        toAdd.setAmount((float) 200);
        toAdd.setTitle("dsafdsaf");
        toAdd.setUser(admin);
        transactionRepository.save(toAdd);
    }
}

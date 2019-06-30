package de.menschomat.cashier.rest;


import de.menschomat.cashier.database.jpa.model.DBUser;
import de.menschomat.cashier.database.jpa.model.RestUser;
import de.menschomat.cashier.database.jpa.repositories.TagRepository;
import de.menschomat.cashier.database.jpa.repositories.TransactionRepository;
import de.menschomat.cashier.database.jpa.repositories.UserRepository;

import de.menschomat.cashier.rest.model.ChangePWModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/user")
public class UserHandler {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TagRepository tagRepository;
    private final TransactionRepository transactionRepository;

    public UserHandler(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, TagRepository tagRepository, TransactionRepository transactionRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.transactionRepository = transactionRepository;
    }


    @GetMapping(value = "/current", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public RestUser getCurrentUser(Authentication authentication) {
        Optional<DBUser> dbUserOptional = userRepository.findById(authentication.getName());
        if (dbUserOptional.isPresent()) {
            return new RestUser(dbUserOptional.get());
        } else
            throw new UsernameNotFoundException("USER NOT FOUND");

    }

    @PostMapping(value = "/current", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public RestUser updateCurrentUser(Authentication authentication, @RequestBody RestUser toAdd) {
        Optional<DBUser> dbUserOptional = userRepository.findById(authentication.getName());
        if (dbUserOptional.isPresent()) {
            DBUser user = dbUserOptional.get();
            user.updateFromRestUser(toAdd);
            userRepository.save(user);
            return new RestUser(user);
        } else
            throw new UsernameNotFoundException("USER NOT FOUND");

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<RestUser> saveAndUpdateUsers(@RequestBody List<DBUser> toAdd, HttpServletResponse response) {
        toAdd.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
        try {
            userRepository.saveAll(toAdd);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return getAllUsers();
    }

    @PostMapping(value = "/password", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<String> updatePassword(Authentication authentication, @RequestBody ChangePWModel changePWModel) {
        Optional<DBUser> dbUserOptional = userRepository.findById(authentication.getName());
        if (dbUserOptional.isPresent()) {
            DBUser toModify = dbUserOptional.get();
            if (passwordEncoder.matches(changePWModel.getOldPW(), toModify.getPassword())) {
                System.out.println(changePWModel.getNewPW());
                toModify.setPassword(passwordEncoder.encode(changePWModel.getNewPW()));
                userRepository.save(toModify);
                return new ResponseEntity<>("result successful result", HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("CURRENT_WRONG");
        } else
            throw new UsernameNotFoundException("USER NOT FOUND");

    }

    // ADMIN Role required
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<RestUser> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(RestUser::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/single", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<RestUser> addUser(@RequestBody DBUser toAdd) {
        toAdd.setPassword(passwordEncoder.encode(toAdd.getPassword()));
        userRepository.save(toAdd);
        return getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<RestUser> deleteUsers(@RequestBody List<DBUser> toDelete) {
        toDelete.forEach(user -> {
            Optional<DBUser> userOptional = userRepository.findById(user.getId());
            if (userOptional.isPresent()) {
                DBUser dbuser = userOptional.get();
                tagRepository.deleteAll(dbuser.getTags());
                transactionRepository.deleteAll(dbuser.getTransactions());
            }
        });
        userRepository.deleteAll(toDelete);
        return getAllUsers();
    }
}
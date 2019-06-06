package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.model.DBUser;
import de.menschomat.wgo.database.repositories.UserRepository;
import de.menschomat.wgo.rest.model.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/user")
public class UserHandler {

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<DBUser> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/current", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public DBUser getUser(Authentication authentication) {

        return userRepository.findByUsername(authentication.getName());
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<DBUser> saveAndUpdateUsers(@RequestBody List<DBUser> toAdd) {
        toAdd.forEach(user -> user.password = new BCryptPasswordEncoder().encode(user.password));
        userRepository.saveAll(toAdd);
        return getAllUsers();
    }

    @PostMapping(value = "/single", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<DBUser> addUser(@RequestBody DBUser toAdd) {
        userRepository.save(toAdd);
        return getAllUsers();
    }

    @DeleteMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<DBUser> deleteUsers(@RequestBody List<DBUser> toDelete) {
        userRepository.deleteAll(toDelete);
        return getAllUsers();
    }
}
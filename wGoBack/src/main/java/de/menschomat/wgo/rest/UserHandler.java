package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.model.User;
import de.menschomat.wgo.database.repositories.UserRepository;
import de.menschomat.wgo.rest.model.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/user")
public class UserHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserSessionData userSession;

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/current", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public User getUser() {

        return userRepository.findById(userSession.getUserID()).get();
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<User> saveAndUpdateUsers(@RequestBody List<User> toAdd) {
        userRepository.saveAll(toAdd);
        return getAllUsers();
    }

    @PostMapping(value = "/single", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<User> addUser(@RequestBody User toAdd) {
        userRepository.save(toAdd);
        return getAllUsers();
    }

    @DeleteMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<User> deleteUsers(@RequestBody List<User> toDelete) {
        userRepository.deleteAll(toDelete);
        return getAllUsers();
    }
}
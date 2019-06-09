package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.model.DBUser;
import de.menschomat.wgo.database.repositories.UserRepository;
import de.menschomat.wgo.rest.model.RestUser;
import de.menschomat.wgo.rest.model.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/user")
public class UserHandler {

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping(value = "/current", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public RestUser getUser(Authentication authentication) {

        return new RestUser(userRepository.findById(authentication.getName()).get());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<RestUser> saveAndUpdateUsers(@RequestBody List<DBUser> toAdd) {
        toAdd.forEach(user -> user.password = new BCryptPasswordEncoder().encode(user.password));
        userRepository.saveAll(toAdd);
        return getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/single", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<RestUser> addUser(@RequestBody DBUser toAdd) {
        toAdd.password = new BCryptPasswordEncoder().encode(toAdd.password);
        userRepository.save(toAdd);
        return getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<RestUser> deleteUsers(@RequestBody List<DBUser> toDelete) {
        userRepository.deleteAll(toDelete);
        return getAllUsers();
    }
}
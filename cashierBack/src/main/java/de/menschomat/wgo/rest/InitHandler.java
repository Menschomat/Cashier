package de.menschomat.wgo.rest;


import de.menschomat.wgo.database.mongo.repositories.MongoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/init")
public class InitHandler {

    @Autowired
    private MongoUserRepository userRepository;

    @GetMapping(value = "/checkup/firstTime", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public Boolean checkFirstTime(Authentication authentication) {

        return userRepository.findById(authentication.getName()).get().initialized;
    }
}

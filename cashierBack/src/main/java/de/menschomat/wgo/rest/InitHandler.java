package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/init")
public class InitHandler {

    @Value("${standAlone.active}")
    private Boolean standAlone;


    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/checkup/standalone", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public Boolean checkStandAlone() {
        return false;
    }

    @GetMapping(value = "/checkup/firstTime", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public Boolean checkFirstTime(Authentication authentication) {

        return userRepository.findById(authentication.getName()).get().initialized;
    }
}

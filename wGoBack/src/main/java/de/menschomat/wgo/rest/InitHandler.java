package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.model.User;
import de.menschomat.wgo.database.repositories.UserRepository;
import de.menschomat.wgo.rest.model.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/init")
public class InitHandler {

    @Value("${standAlone.active}")
    private Boolean standAlone;

   @Autowired
    UserSessionData userSession;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/checkup/standalone", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public Boolean checkStandAlone() {
        if (userRepository.findById("standalone").isPresent()) {
            userSession.setUserID("standalone");
            return true;
        }
        return false;
    }

    @GetMapping(value = "/checkup/firstTime", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public Boolean checkFirstTime() {
        if (userSession.getUserID().isEmpty() && standAlone) {
            userSession.setUserID("standalone");
        }
        System.out.println(userSession.toString());
        System.out.println(userSession.getUserID());

        return userRepository.findById(userSession.getUserID()).get().initialized;
    }
}

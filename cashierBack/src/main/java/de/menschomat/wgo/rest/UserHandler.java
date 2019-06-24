package de.menschomat.wgo.rest;

import com.mongodb.BulkWriteException;
import de.menschomat.wgo.database.model.DBUser;
import de.menschomat.wgo.database.repositories.TagRepository;
import de.menschomat.wgo.database.repositories.TransactionRepository;
import de.menschomat.wgo.database.repositories.UserRepository;
import de.menschomat.wgo.rest.model.ChangePWModel;
import de.menschomat.wgo.rest.model.RestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
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
        return new RestUser(userRepository.findById(authentication.getName()).get());
    }

    @PostMapping(value = "/current", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public RestUser updateCurrentUser(Authentication authentication, @RequestBody RestUser toAdd) {
        DBUser user = userRepository.findById(authentication.getName()).get();
        user.updateFromRestUser(toAdd);
        userRepository.save(user);
        return new RestUser(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<RestUser> saveAndUpdateUsers(@RequestBody List<DBUser> toAdd, HttpServletResponse response) {
        toAdd.forEach(user -> user.password = passwordEncoder.encode(user.password));
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
        DBUser toModify = userRepository.findById(authentication.getName()).get();
        if (passwordEncoder.matches(changePWModel.getOldPW(), toModify.password)) {
            System.out.println(changePWModel.getNewPW());
            toModify.password = passwordEncoder.encode(changePWModel.getNewPW());
            userRepository.save(toModify);
            return new ResponseEntity<>("result successful result",
                    HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("CURRENT_WRONG");
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
        toAdd.password = passwordEncoder.encode(toAdd.password);
        userRepository.save(toAdd);
        return getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<RestUser> deleteUsers(@RequestBody List<DBUser> toDelete) {
        toDelete.forEach(user -> {
            tagRepository.deleteAll(tagRepository.findAllByLinkedUserID(user.id));
            transactionRepository.deleteAll(transactionRepository.findAllByLinkedUserID(user.id));
        });
        userRepository.deleteAll(toDelete);
        return getAllUsers();
    }
}
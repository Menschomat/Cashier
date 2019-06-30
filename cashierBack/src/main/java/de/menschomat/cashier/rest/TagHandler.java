
package de.menschomat.cashier.rest;

import de.menschomat.cashier.database.jpa.model.DBUser;
import de.menschomat.cashier.database.jpa.model.Tag;
import de.menschomat.cashier.database.jpa.repositories.TagRepository;
import de.menschomat.cashier.database.jpa.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/tag")
public class TagHandler {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public TagHandler(TagRepository tagRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Tag> getAllTags(Authentication authentication) {
        Optional<DBUser> dbUserOptional = userRepository.findById(authentication.getName());
        if (dbUserOptional.isPresent()) {
            tagRepository.flush();
            return dbUserOptional.get().getTags();
        } else
            throw new UsernameNotFoundException("USER NOT FOUND");
    }

    @PostMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Tag> addAllTags(Authentication authentication, @RequestBody List<Tag> tags) {
        Optional<DBUser> dbUserOptional = userRepository.findById(authentication.getName());
        if (dbUserOptional.isPresent()) {
            tags.forEach(tag -> tag.setUser(dbUserOptional.get()));
            System.out.println(tags);
            tagRepository.saveAll(tags);
            return getAllTags(authentication);
        } else
            throw new UsernameNotFoundException("USER NOT FOUND");
    }
}
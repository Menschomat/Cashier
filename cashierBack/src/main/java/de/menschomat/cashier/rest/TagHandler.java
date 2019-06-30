
package de.menschomat.cashier.rest;

import de.menschomat.cashier.database.jpa.model.Tag;
import de.menschomat.cashier.database.jpa.repositories.TagRepository;
import de.menschomat.cashier.database.jpa.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        tagRepository.flush();
        return userRepository.findById(authentication.getName()).get().getTags();
    }

    @PostMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Tag> addAllTags(Authentication authentication, @RequestBody List<Tag> tags) {
        tags.forEach(tag -> {
            tag.setUser(userRepository.findById(authentication.getName()).get());
        });
        System.out.println(tags);
        tagRepository.saveAll(tags);
        return getAllTags(authentication);
    }
}

package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.model.Tag;
import de.menschomat.wgo.database.repositories.TagRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/tag")
public class TagHandler {

    private final TagRepository tagRepository;

    public TagHandler(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Tag> getAllTags(Authentication authentication) {
        return tagRepository.findAllByLinkedUserID(authentication.getName());
    }

    @GetMapping(value = "/title", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Tag> getTagsByTitles(@RequestBody List<String> list) {
        return tagRepository.findAllByTitleIn(list);
    }


    @PostMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Tag> addAllTags(Authentication authentication, @RequestBody List<Tag> tags) {
        tags.forEach(tag -> {
            tag.linkedUserID = authentication.getName();
        });
        System.out.println(tags);
        tagRepository.saveAll(tags);
        return getAllTags(authentication);
    }
}
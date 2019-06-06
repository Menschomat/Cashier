
package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.model.Tag;
import de.menschomat.wgo.database.repositories.TagRepository;
import de.menschomat.wgo.database.repositories.UserRepository;
import de.menschomat.wgo.rest.model.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/tag")
public class TagHandler {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Tag> getAllTags(Authentication authentication) {
        return tagRepository.findAllByLinkedUserID(userRepository.findByUsername(authentication.getName()).id);
    }

    @GetMapping(value = "/title", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Tag> getTagsByTitles(@RequestBody List<String> list) {
        return tagRepository.findAllByTitleIn(list);
    }


    @PostMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Tag> addAllTags(Authentication authentication, @RequestBody List<Tag> tags) {
        String userID = userRepository.findByUsername(authentication.getName()).id;
       /* String userID = userRepository.findByUsername(authentication.getName()).id;
        List<Tag> userTags = tagRepository.findAllByLinkedUserID(userID);
        List<Tag> out = tags.stream().filter(tag -> userTags.stream().anyMatch(t -> t.title.equals(tag.title))).map(tag -> userTags.stream().filter(t -> t.title.equals(tag.title)).findFirst().get()).collect(Collectors.toList());
        List<Tag> idChanged = tags.stream().filter(tag -> userTags.stream().noneMatch(t -> t.title.equals(tag.title))).collect(Collectors.toList());
        idChanged.forEach(tag -> {
            tag.linkedUserID = userID;
        });
        out.addAll(idChanged);*/
        tags.forEach(tag -> {
            tag.linkedUserID = userID;
        });
        System.out.println(tags);
        tagRepository.saveAll(tags);
        return getAllTags(authentication);
    }
}
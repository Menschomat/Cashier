
package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.model.Tag;
import de.menschomat.wgo.database.repositories.TagRepository;
import de.menschomat.wgo.rest.model.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/tag")
public class TagHandler {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    UserSessionData userSession;

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Tag> getAllTags() {
        return tagRepository.findAllByLinkedUserID(userSession.getUserID());
    }

    @GetMapping(value = "/title", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Tag> getTagsByTitles(@RequestBody List<String> list) {
        return tagRepository.findAllByTitleIn(list);
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Tag> addTag(@RequestBody Tag toAdd) {
        tagRepository.save(toAdd);
        return getAllTags();
    }

    @PostMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Tag> addAllTags(@RequestBody List<Tag> tags) {
        tagRepository.saveAll(tags);
        return getAllTags();
    }
}
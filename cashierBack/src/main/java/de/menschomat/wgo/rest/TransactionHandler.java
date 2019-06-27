package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.jpa.model.DBUser;
import de.menschomat.wgo.database.jpa.model.Transaction;
import de.menschomat.wgo.database.jpa.model.TransactionResult;
import de.menschomat.wgo.database.jpa.repositories.TagRepository;
import de.menschomat.wgo.database.jpa.repositories.TransactionRepository;
import de.menschomat.wgo.database.jpa.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/transaction")
@Transactional
public class TransactionHandler {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;


    public TransactionHandler(TransactionRepository transactionRepository, UserRepository userRepository, TagRepository tagRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> getAllTransactions(Authentication authentication) {
        return userRepository.findById(authentication.getName()).get().getTransactions();
    }

    @GetMapping(value = "/latest", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> getLatestTransactions(Authentication authentication) {
        return transactionRepository.findByUser(userRepository.findById(authentication.getName()).get(), PageRequest.of(0, 5, Sort.by("date").descending())).getContent();
    }
/*
    @GetMapping(value = "/paged", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public TransactionResult getPaged(Authentication authentication,
                                      @RequestParam int size,
                                      @RequestParam int page,
                                      @RequestParam(value = "sortBy", required = false) String sortBy,
                                      @RequestParam(value = "sortDir", required = false) String sortDir) {
        Page<Transaction> resultPage = transactionRepository.findByLinkedUserID(authentication.getName(), getPageRequest(page, size, sortBy, sortDir));
        return new TransactionResult(resultPage.getTotalPages(), resultPage.getTotalElements(), resultPage.getContent());
    }

    @GetMapping(value = "/paged/len", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public PageInfo getNumOfPages(Authentication authentication, @RequestParam int size) {
        return new PageInfo(transactionRepository.findByLinkedUserID(authentication.getName(), PageRequest.of(0, size)).getTotalPages());

    } */

    @GetMapping(value = "/date", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public TransactionResult getNumOfPages(Authentication authentication,
                                           @RequestParam Date from, @RequestParam Date to,
                                           @RequestParam int page, @RequestParam int size,
                                           @RequestParam(value = "sortBy", required = false) String sortBy,
                                           @RequestParam(value = "sortDir", required = false) String sortDir) {
        Page<Transaction> resultPage = transactionRepository.findByDateBetweenAndUser(
                from,
                to,
                userRepository.findById(authentication.getName()).get(),
                getPageRequest(page, size, sortBy, sortDir)
        );
        return new TransactionResult(resultPage.getTotalPages(), resultPage.getTotalElements(), resultPage.getContent());
    }

    @GetMapping(value = "/date/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> getNumOfPages(Authentication authentication, @RequestParam String from, @RequestParam String to) throws ParseException {

        SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.GERMANY);
        List<Transaction> out = transactionRepository.findByDateBetweenAndUser(ISO8601DATEFORMAT.parse(from), ISO8601DATEFORMAT.parse(to), userRepository.findById(authentication.getName()).get());
        return out;
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> addTransaction(Authentication authentication, @RequestBody Transaction toAdd) {
        final Optional<DBUser> user_o = userRepository.findById(authentication.getName());
        if (user_o.isPresent()) {
            toAdd.setUser(user_o.get());
            toAdd.setTags(toAdd.getTags().stream().map(tag -> {
                tag.setUser(user_o.get());
                return tag;
            }).collect(Collectors.toList()));
            tagRepository.saveAll(toAdd.getTags());
            transactionRepository.saveAndFlush(toAdd);
            return getLatestTransactions(authentication);
        } else {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }

    @DeleteMapping(value = "/id", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> deleteTransactionsByID(Authentication authentication, @RequestBody List<String> toDelete) {
        final Optional<DBUser> user_o = userRepository.findById(authentication.getName());
        if (user_o.isPresent()) {
            toDelete.forEach(t_id -> transactionRepository.deleteByIdAndUser(t_id, user_o.get()));
            return getLatestTransactions(authentication);
        } else {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }

    private PageRequest getPageRequest(int page, int size, String sortBy, String sortDir) {
        PageRequest pageRequest;
        if (sortBy != null) {
            if (sortDir != null && sortDir.equals("asc")) {
                pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
            } else {
                pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
            }
        } else {
            pageRequest = PageRequest.of(page, size);
        }
        return pageRequest;
    }
}
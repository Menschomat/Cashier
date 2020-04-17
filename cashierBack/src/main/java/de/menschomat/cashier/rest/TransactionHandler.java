package de.menschomat.cashier.rest;

import de.menschomat.cashier.database.jpa.model.DBUser;
import de.menschomat.cashier.database.jpa.model.Tag;
import de.menschomat.cashier.database.jpa.model.Transaction;
import de.menschomat.cashier.database.jpa.model.TransactionResult;
import de.menschomat.cashier.database.jpa.repositories.TagRepository;
import de.menschomat.cashier.database.jpa.repositories.TransactionRepository;
import de.menschomat.cashier.database.jpa.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
        Optional<DBUser> dbUserOptional = userRepository.findById(authentication.getName());
        if (dbUserOptional.isPresent()) {
            return dbUserOptional.get().getTransactions();
        } else
            throw new UsernameNotFoundException("USER NOT FOUND");
    }

    @GetMapping(value = "/latest", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> getLatestTransactions(Authentication authentication) {
        Optional<DBUser> dbUserOptional = userRepository.findById(authentication.getName());
        if (dbUserOptional.isPresent()) {
            return transactionRepository.findByUser(dbUserOptional.get(), PageRequest.of(0, 5, Sort.by("date").descending())).getContent();
        } else
            throw new UsernameNotFoundException("USER NOT FOUND");
    }

    @GetMapping(value = "/date", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public TransactionResult getNumOfPages(Authentication authentication,
                                           @RequestParam Date from, @RequestParam Date to,
                                           @RequestParam int page, @RequestParam int size,
                                           @RequestParam(value = "sortBy", required = false) String sortBy,
                                           @RequestParam(value = "sortDir", required = false) String sortDir) {
        Optional<DBUser> dbUserOptional = userRepository.findById(authentication.getName());
        if (dbUserOptional.isPresent()) {
            Page<Transaction> resultPage = transactionRepository.findByDateBetweenAndUser(from, to, dbUserOptional.get(), getPageRequest(page, size, sortBy, sortDir));
            return new TransactionResult(resultPage.getTotalPages(), resultPage.getTotalElements(), resultPage.getContent());
        } else
            throw new UsernameNotFoundException("USER NOT FOUND");

    }

    @GetMapping(value = "/date/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> getNumOfPages(Authentication authentication, @RequestParam String from, @RequestParam String to) throws ParseException {
        Optional<DBUser> dbUserOptional = userRepository.findById(authentication.getName());
        if (dbUserOptional.isPresent()) {
            SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.GERMANY);
            return transactionRepository.findByDateBetweenAndUser(ISO8601DATEFORMAT.parse(from), ISO8601DATEFORMAT.parse(to), dbUserOptional.get());
        } else
            throw new UsernameNotFoundException("USER NOT FOUND");

    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> addTransaction(Authentication authentication, @RequestBody Transaction toAdd) {
        final Optional<DBUser> user_o = userRepository.findById(authentication.getName());
        if (user_o.isPresent()) {
            toAdd.setUser(user_o.get());
            if (toAdd.getTags() == null)
                toAdd.setTags(new ArrayList<>());
            toAdd.setTags(toAdd.getTags().stream().peek(tag -> tag.setUser(user_o.get())).collect(Collectors.toList()));
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
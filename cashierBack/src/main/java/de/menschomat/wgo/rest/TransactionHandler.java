package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.model.Transaction;
import de.menschomat.wgo.database.repositories.TransactionRepository;
import de.menschomat.wgo.rest.model.PageInfo;
import de.menschomat.wgo.rest.model.TransactionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/transaction")
public class TransactionHandler {

    private final TransactionRepository transactionRepository;

    public TransactionHandler(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> getAllTransactions(Authentication authentication) {
        return transactionRepository.findAllByLinkedUserID(authentication.getName());
    }

    @GetMapping(value = "/paged", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public TransactionResult getPaged(Authentication authentication, @RequestParam int size, @RequestParam int page) {
        Page<Transaction> resultPage = transactionRepository.findByLinkedUserID(authentication.getName(), PageRequest.of(page, size));
        return new TransactionResult(resultPage.getTotalPages(), resultPage.getTotalElements(), resultPage.getContent());
    }

    @GetMapping(value = "/paged/len", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public PageInfo getNumOfPages(Authentication authentication, @RequestParam int size) {
        return new PageInfo(transactionRepository.findByLinkedUserID(authentication.getName(), PageRequest.of(0, size)).getTotalPages());

    }

    @GetMapping(value = "/date", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public TransactionResult getNumOfPages(Authentication authentication, @RequestParam Date from, @RequestParam Date to, @RequestParam int page, @RequestParam int size) {
        Page<Transaction> resultPage = transactionRepository.findByDateBetweenAndLinkedUserID(from, to, authentication.getName(), PageRequest.of(page, size));
        return new TransactionResult(resultPage.getTotalPages(), resultPage.getTotalElements(), resultPage.getContent());
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> addTransaction(Authentication authentication, @RequestBody Transaction toAdd) {
        toAdd.linkedUserID = authentication.getName();
        transactionRepository.save(toAdd);
        return getAllTransactions(authentication);
    }

    @DeleteMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> deleteTransactions(Authentication authentication, @RequestBody List<Transaction> toDelete) {
        transactionRepository.deleteAll(toDelete);
        return getAllTransactions(authentication);
    }
}
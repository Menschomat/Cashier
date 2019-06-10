package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.model.Transaction;
import de.menschomat.wgo.database.repositories.TransactionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
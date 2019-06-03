package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.model.Transaction;
import de.menschomat.wgo.database.repositories.TransactionRepository;
import de.menschomat.wgo.rest.model.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/transaction")
public class TransactionHandler {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    UserSessionData userSession;

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> getAllTransactions() {
        System.out.println(userSession.getUserID());
        return transactionRepository.findAllByLinkedUserID(userSession.getUserID());
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> addTransaction(@RequestBody Transaction toAdd) {
        transactionRepository.save(toAdd);
        return getAllTransactions();
    }

    @DeleteMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> deleteTransactions(@RequestBody List<Transaction> toDelete) {
        transactionRepository.deleteAll(toDelete);
        return getAllTransactions();
    }
}
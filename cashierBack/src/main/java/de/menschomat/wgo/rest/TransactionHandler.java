package de.menschomat.wgo.rest;

import de.menschomat.wgo.database.model.Transaction;
import de.menschomat.wgo.database.repositories.TransactionRepository;
import de.menschomat.wgo.rest.model.PageInfo;
import de.menschomat.wgo.rest.model.TransactionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @GetMapping(value = "/latest", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> getLatestTransactions(Authentication authentication) {
        return transactionRepository.findByLinkedUserID(authentication.getName(), PageRequest.of(0, 5, Sort.by("date").descending())).getContent();
    }

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

    }

    @GetMapping(value = "/date", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public TransactionResult getNumOfPages(Authentication authentication,
                                           @RequestParam Date from, @RequestParam Date to,
                                           @RequestParam int page, @RequestParam int size,
                                           @RequestParam(value = "sortBy", required = false) String sortBy,
                                           @RequestParam(value = "sortDir", required = false) String sortDir) {
        Page<Transaction> resultPage = transactionRepository.findByDateBetweenAndLinkedUserID(
                from,
                to,
                authentication.getName(),
                getPageRequest(page, size, sortBy, sortDir)
        );
        return new TransactionResult(resultPage.getTotalPages(), resultPage.getTotalElements(), resultPage.getContent());
    }

    @GetMapping(value = "/date/all", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> getNumOfPages(Authentication authentication, @RequestParam String from, @RequestParam String to) throws ParseException {

        SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.GERMANY);
        return transactionRepository.findByDateBetweenAndLinkedUserID(ISO8601DATEFORMAT.parse(from), ISO8601DATEFORMAT.parse(to), authentication.getName());
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> addTransaction(Authentication authentication, @RequestBody Transaction toAdd) {
        toAdd.linkedUserID = authentication.getName();
        transactionRepository.save(toAdd);
        return getLatestTransactions(authentication);
    }

    @DeleteMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> deleteTransactions(Authentication authentication, @RequestBody List<Transaction> toDelete) {
        transactionRepository.deleteAll(toDelete);
        return getLatestTransactions(authentication);
    }

    @DeleteMapping(value = "/id", produces = APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Transaction> deleteTransactionsByID(Authentication authentication, @RequestBody List<String> toDelete) {
        transactionRepository.deleteAllById(toDelete);
        return getLatestTransactions(authentication);
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
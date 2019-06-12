package de.menschomat.wgo.rest.model;

import de.menschomat.wgo.database.model.Transaction;

import java.util.List;

public class TransactionResult {
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int totalPages;
    public List<Transaction> transactions;

    public TransactionResult(int totalPages, List<Transaction> content) {
        this.totalPages = totalPages;
        this.transactions = content;
    }
}

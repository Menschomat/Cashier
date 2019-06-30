package de.menschomat.cashier.database.jpa.model;

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

    public long getTotalEntries() {
        return totalEntries;
    }

    public void setTotalEntries(long totalEntries) {
        this.totalEntries = totalEntries;
    }

    public long totalEntries;
    public List<Transaction> transactions;

    public TransactionResult(int totalPages, long totalEntries, List<Transaction> content) {
        this.totalPages = totalPages;
        this.totalEntries = totalEntries;
        this.transactions = content;
    }
}

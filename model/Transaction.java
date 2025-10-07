package model;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int accountId;
    private String type; // DEPOSIT or WITHDRAW
    private double amount;
    private Timestamp timestamp;

    public Transaction() {}

    public Transaction(int id, int accountId, String type, double amount, Timestamp timestamp) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}
// filepath: /home/stm/Desktop/ARK_S3/Smart-Banking-System-main/service/CustomerService.java
package service;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.TransactionDAO;
import model.Account;
import model.Customer;
import model.Transaction;

public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO();
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public boolean register(String username, String password, double initialBalance) {
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
        Customer registeredCustomer = customerDAO.register(customer);
        if (registeredCustomer != null) {
            Account account = new Account();
            account.setCustomerId(registeredCustomer.getId());
            account.setBalance(initialBalance);
            boolean created = accountDAO.createAccount(account);
            if (created) {
                // Log initial deposit transaction if initialBalance > 0
                if (initialBalance > 0) {
                    Transaction transaction = new Transaction();
                    transaction.setAccountId(account.getId());
                    transaction.setType("DEPOSIT");
                    transaction.setAmount(initialBalance);
                    transactionDAO.addTransaction(transaction);
                }
                return true;
            }
        }
        return false;
    }

    public Customer login(String username, String password) {
        return customerDAO.login(username, password);
    }

    public double getBalance(int customerId) {
        Account account = accountDAO.getAccountByCustomerId(customerId);
        if (account != null) {
            return account.getBalance();
        }
        return -1;
    }

    public boolean deposit(int customerId, double amount) {
        if (amount <= 0) return false;
        Account account = accountDAO.getAccountByCustomerId(customerId);
        if (account == null) return false;
        double newBalance = account.getBalance() + amount;
        boolean updated = accountDAO.updateBalance(account.getId(), newBalance);
        if (updated) {
            Transaction transaction = new Transaction();
            transaction.setAccountId(account.getId());
            transaction.setType("DEPOSIT");
            transaction.setAmount(amount);
            transactionDAO.addTransaction(transaction);
            return true;
        }
        return false;
    }

    public boolean withdraw(int customerId, double amount) {
        if (amount <= 0) return false;
        Account account = accountDAO.getAccountByCustomerId(customerId);
        if (account == null) return false;
        if (account.getBalance() < amount) return false;
        double newBalance = account.getBalance() - amount;
        boolean updated = accountDAO.updateBalance(account.getId(), newBalance);
        if (updated) {
            Transaction transaction = new Transaction();
            transaction.setAccountId(account.getId());
            transaction.setType("WITHDRAW");
            transaction.setAmount(amount);
            transactionDAO.addTransaction(transaction);
            return true;
        }
        return false;
    }

    public java.util.List<Transaction> getMiniStatement(int customerId) {
        Account account = accountDAO.getAccountByCustomerId(customerId);
        if (account == null) return java.util.Collections.emptyList();
        return transactionDAO.getRecentTransactionsByAccountId(account.getId(), 5);
    }
}
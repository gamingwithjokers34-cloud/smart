// filepath: /home/stm/Desktop/ARK_S3/Smart-Banking-System-main/service/StaffService.java
package service;

import dao.CustomerDAO;
import dao.StaffDAO;
import dao.TransactionDAO;
import model.Customer;
import model.Staff;
import model.Transaction;

import java.util.List;

public class StaffService {
    private StaffDAO staffDAO = new StaffDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public Staff login(String username, String password) {
        return staffDAO.login(username, password);
    }

    public List<Customer> viewCustomers() {
        return customerDAO.getAllCustomers();
    }

    public List<Transaction> viewAllTransactions() {
        return transactionDAO.getAllTransactions();
    }
}
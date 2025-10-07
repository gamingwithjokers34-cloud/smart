// filepath: /home/stm/Desktop/ARK_S3/Smart-Banking-System-main/service/AdminService.java
package service;

import dao.AdminDAO;
import dao.CustomerDAO;
import dao.StaffDAO;
import dao.TransactionDAO;
import model.Admin;
import model.Customer;
import model.Staff;
import model.Transaction;

import java.util.List;

public class AdminService {
    private AdminDAO adminDAO = new AdminDAO();
    private StaffDAO staffDAO = new StaffDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public Admin login(String username, String password) {
        return adminDAO.login(username, password);
    }

    public boolean addStaff(String username, String password) {
        Staff staff = new Staff();
        staff.setUsername(username);
        staff.setPassword(password);
        return staffDAO.addStaff(staff);
    }

    public List<Customer> viewCustomers() {
        return customerDAO.getAllCustomers();
    }

    public List<Transaction> viewAllTransactions() {
        return transactionDAO.getAllTransactions();
    }
}
import model.Admin;
import model.Customer;
import model.Staff;
import model.Transaction;
import service.AdminService;
import service.CustomerService;
import service.StaffService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    private static CustomerService customerService = new CustomerService();
    private static StaffService staffService = new StaffService();
    private static AdminService adminService = new AdminService();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Smart Banking System ===");
            System.out.println("1. Customer");
            System.out.println("2. Staff");
            System.out.println("3. Admin");
            System.out.println("4. Exit");
            System.out.print("Select user type: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    customerMenu();
                    break;
                case "2":
                    staffMenu();
                    break;
                case "3":
                    adminMenu();
                    break;
                case "4":
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void customerMenu() {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Back");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    customerRegister();
                    break;
                case "2":
                    Customer customer = customerLogin();
                    if (customer != null) {
                        customerOperations(customer);
                    }
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void customerRegister() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Enter initial deposit amount: ");
        double initialBalance;
        try {
            initialBalance = Double.parseDouble(scanner.nextLine());
            if (initialBalance < 0) {
                System.out.println("Initial balance cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return;
        }

        boolean success = customerService.register(username, password, initialBalance);
        if (success) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed. Username might be taken.");
        }
    }

    private static Customer customerLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        Customer customer = customerService.login(username, password);
        if (customer == null) {
            System.out.println("Login failed. Check credentials.");
        } else {
            System.out.println("Login successful. Welcome, " + customer.getUsername() + "!");
        }
        return customer;
    }

    private static void customerOperations(Customer customer) {
        while (true) {
            System.out.println("\n--- Customer Operations ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Mini-statement (last 5)");
            System.out.println("5. Logout");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter deposit amount: ");
                    try {
                        double amount = Double.parseDouble(scanner.nextLine());
                        if (amount <= 0) {
                            System.out.println("Amount must be positive.");
                            break;
                        }
                        boolean deposited = customerService.deposit(customer.getId(), amount);
                        if (deposited) {
                            System.out.println("Deposit successful.");
                        } else {
                            System.out.println("Deposit failed.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount.");
                    }
                    break;
                case "2":
                    System.out.print("Enter withdrawal amount: ");
                    try {
                        double amount = Double.parseDouble(scanner.nextLine());
                        if (amount <= 0) {
                            System.out.println("Amount must be positive.");
                            break;
                        }
                        boolean withdrawn = customerService.withdraw(customer.getId(), amount);
                        if (withdrawn) {
                            System.out.println("Withdrawal successful.");
                        } else {
                            System.out.println("Withdrawal failed. Check balance.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount.");
                    }
                    break;
                case "3":
                    double balance = customerService.getBalance(customer.getId());
                    if (balance >= 0) {
                        System.out.printf("Your balance: %.2f\n", balance);
                    } else {
                        System.out.println("Error retrieving balance.");
                    }
                    break;
                case "4":
                    java.util.List<Transaction> mini = customerService.getMiniStatement(customer.getId());
                    if (mini.isEmpty()) {
                        System.out.println("No recent transactions.");
                    } else {
                        System.out.println("\n--- Mini Statement ---");
                        for (Transaction t : mini) {
                            System.out.printf("%s: %s %.2f (ID %d)\n", t.getTimestamp(), t.getType(), t.getAmount(), t.getId());
                        }
                    }
                    break;
                case "5":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void staffMenu() {
        System.out.println("\n--- Staff Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        Staff staff = staffService.login(username, password);
        if (staff == null) {
            System.out.println("Login failed.");
            return;
        }
        System.out.println("Login successful. Welcome, " + staff.getUsername() + "!");

        while (true) {
            System.out.println("\n--- Staff Operations ---");
            System.out.println("1. View Customers & Balances");
            System.out.println("2. Create Reports (View All Transactions)");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Logout");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    List<Customer> customers = staffService.viewCustomers();
                    System.out.println("\n--- Customers List ---");
                    if (customers.isEmpty()) {
                        System.out.println("No customers found.");
                    } else {
                        for (Customer c : customers) {
                            double bal = customerService.getBalance(c.getId());
                            System.out.printf("ID: %d, Username: %s, Balance: %.2f\n", c.getId(), c.getUsername(), bal);
                        }
                    }
                    break;
                case "2":
                    List<Transaction> transactions = staffService.viewAllTransactions();
                    System.out.println("\n--- Transactions Report ---");
                    if (transactions.isEmpty()) {
                        System.out.println("No transactions found.");
                    } else {
                        for (Transaction t : transactions) {
                            System.out.printf("ID: %d, AccountID: %d, Type: %s, Amount: %.2f, Time: %s\n",
                                    t.getId(), t.getAccountId(), t.getType(), t.getAmount(), t.getTimestamp());
                        }
                    }
                    break;
                case "3":
                    System.out.print("Enter customer ID: ");
                    try {
                        int cid = Integer.parseInt(scanner.nextLine());
                        System.out.print("New username: ");
                        String nu = scanner.nextLine().trim();
                        System.out.print("New password: ");
                        String np = scanner.nextLine().trim();
                        boolean ok = staffService.updateCustomer(cid, nu, np);
                        System.out.println(ok ? "Customer updated." : "Update failed.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid id.");
                    }
                    break;
                case "4":
                    System.out.print("Enter customer ID: ");
                    try {
                        int cid = Integer.parseInt(scanner.nextLine());
                        boolean ok = staffService.deleteCustomer(cid);
                        System.out.println(ok ? "Customer deleted." : "Delete failed.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid id.");
                    }
                    break;
                case "5":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void adminMenu() {
        System.out.println("\n--- Admin Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        Admin admin = adminService.login(username, password);
        if (admin == null) {
            System.out.println("Login failed.");
            return;
        }
        System.out.println("Login successful. Welcome, " + admin.getUsername() + "!");

        while (true) {
            System.out.println("\n--- Admin Operations ---");
            System.out.println("1. Manage Staff (Add New Staff)");
            System.out.println("2. Manage Customers (View/Update/Delete Customers)");
            System.out.println("3. View Reports (All Transactions)");
            System.out.println("4. View Staff");
            System.out.println("5. Logout");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("a) Add staff  b) Remove staff  c) Back");
                    String staffAction = scanner.nextLine().trim();
                    if ("a".equalsIgnoreCase(staffAction)) {
                        System.out.print("Enter new staff username: ");
                        String staffUsername = scanner.nextLine().trim();
                        System.out.print("Enter new staff password: ");
                        String staffPassword = scanner.nextLine().trim();
                        boolean added = adminService.addStaff(staffUsername, staffPassword);
                        System.out.println(added ? "Staff added successfully." : "Failed to add staff. Username might be taken.");
                    } else if ("b".equalsIgnoreCase(staffAction)) {
                        List<Staff> staffListForDelete = adminService.viewAllStaff();
                        if (staffListForDelete.isEmpty()) {
                            System.out.println("No staff to remove.");
                        } else {
                            for (Staff s : staffListForDelete) {
                                System.out.printf("ID: %d, Username: %s\n", s.getId(), s.getUsername());
                            }
                            System.out.print("Enter staff ID to remove: ");
                            try {
                                int sid = Integer.parseInt(scanner.nextLine());
                                boolean removed = adminService.removeStaff(sid);
                                System.out.println(removed ? "Staff removed." : "Remove failed.");
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid id.");
                            }
                        }
                    }
                    break;
                case "2":
                    List<Customer> customers = adminService.viewCustomers();
                    System.out.println("\n--- Customers List ---");
                    if (customers.isEmpty()) {
                        System.out.println("No customers found.");
                    } else {
                        for (Customer c : customers) {
                            double bal = customerService.getBalance(c.getId());
                            System.out.printf("ID: %d, Username: %s, Balance: %.2f\n", c.getId(), c.getUsername(), bal);
                        }
                        System.out.println("a) Update customer  b) Delete customer  c) Back");
                        String sub = scanner.nextLine().trim();
                        if ("a".equalsIgnoreCase(sub)) {
                            System.out.print("Enter customer ID: ");
                            try {
                                int cid = Integer.parseInt(scanner.nextLine());
                                System.out.print("New username: ");
                                String nu = scanner.nextLine().trim();
                                System.out.print("New password: ");
                                String np = scanner.nextLine().trim();
                                boolean ok = adminService.updateCustomer(cid, nu, np);
                                System.out.println(ok ? "Customer updated." : "Update failed.");
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid id.");
                            }
                        } else if ("b".equalsIgnoreCase(sub)) {
                            System.out.print("Enter customer ID: ");
                            try {
                                int cid = Integer.parseInt(scanner.nextLine());
                                boolean ok = adminService.deleteCustomer(cid);
                                System.out.println(ok ? "Customer deleted." : "Delete failed.");
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid id.");
                            }
                        }
                    }
                    break;
                case "3":
                    List<Transaction> transactions = adminService.viewAllTransactions();
                    System.out.println("\n--- Transactions Report ---");
                    if (transactions.isEmpty()) {
                        System.out.println("No transactions found.");
                    } else {
                        for (Transaction t : transactions) {
                            System.out.printf("ID: %d, AccountID: %d, Type: %s, Amount: %.2f, Time: %s\n",
                                    t.getId(), t.getAccountId(), t.getType(), t.getAmount(), t.getTimestamp());
                        }
                    }
                    break;
                case "4":
                    List<Staff> staffList = adminService.viewAllStaff();
                    System.out.println("\n--- Staff List ---");
                    if (staffList.isEmpty()) {
                        System.out.println("No staff found.");
                    } else {
                        for (Staff s : staffList) {
                            System.out.println("ID: " + s.getId() + ", Username: " + s.getUsername());
                        }
                    }
                    break;
                case "5":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

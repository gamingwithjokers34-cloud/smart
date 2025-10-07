## Setup Instructions

This project is a fully functional, console-based Java banking system. To run it successfully, make sure you:

1. Have a MySQL server running with the required database and tables (use the provided SQL script).


2. Update the database connection details in db/DBConnection.java to match your MySQL configuration.


3. Compile all Java files while keeping the package structure intact.


4. Run Main.java from the project root directory.


## Important Notes

Password Storage: Currently, passwords are stored in plain text for simplicity. In a real production system, implement secure password hashing (e.g., bcrypt, Argon2).

Error Handling: Only basic error handling is provided. Consider adding more robust exception management.

Concurrency: This is a single-user console application; concurrent access is not supported.

Input Validation: Basic input checks are included, but you may want to expand validation for better reliability.

JDBC Driver: Ensure that the MySQL JDBC driver (Connector/J) is available in your classpath.


## How to test:
Create the database and tables.
Insert the initial admin user.
Compile and run the app.
Register a customer, login as customer, deposit, withdraw, check balance.
Login as staff (you need to add staff via admin first or insert manually).
Login as admin, add staff, view customers, view transactions.

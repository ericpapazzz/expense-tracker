# Expense Tracker

A simple Java console application to track your expenses and monthly budgets. This project allows you to add, update, list, and delete expenses, as well as set and monitor monthly budgets. If your expenses exceed your set budget for a month, you'll receive a warning.

## Features

- Add, update, list, and delete expenses
- Set, update, and list monthly budgets
- Export expenses to CSV
- Data persistence using CSV files
- Warning when monthly expenses exceed the budget

## Project Structure

```
expense-tracker/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/Main.java
│   │   │   ├── models/Expense.java
│   │   │   ├── models/Budget.java
│   │   │   ├── services/ExpenseService.java
│   │   │   └── services/BudgetService.java
│   │   └── resources/
│   └── test/
├── expenses.csv
├── budgets.csv
├── pom.xml
└── README.md
```

## Getting Started

### Prerequisites
- Java 8 or higher
- (Optional) Maven for easier build and run

### Compile and Run

#### Using Command Line

1. **Compile:**
   ```sh
   javac -d out src/main/java/models/*.java src/main/java/services/*.java src/main/java/com/example/*.java
   ```
2. **Run:**
   ```sh
   java -cp out com.example.Main <command> [options]
   ```

#### Using Maven

1. **Build:**
   ```sh
   mvn clean compile
   ```
2. **Run:**
   ```sh
   mvn exec:java -Dexec.mainClass="com.example.Main" -Dexec.args="<command> [options]"
   ```

## Usage

### Expense Commands

- **Add an expense:**
  ```sh
  java -cp out com.example.Main add --description "Lunch" --amount 12.5 --category Food
  ```
- **Update an expense:**
  ```sh
  java -cp out com.example.Main update --id 1 --description "Dinner" --amount 20.0 --category Food
  ```
- **List expenses:**
  ```sh
  java -cp out com.example.Main list
  java -cp out com.example.Main list --category Food
  ```
- **Delete an expense:**
  ```sh
  java -cp out com.example.Main delete --id 1
  ```
- **Export expenses to CSV:**
  ```sh
  java -cp out com.example.Main export --file my_expenses
  ```
- **Show summary for a month:**
  ```sh
  java -cp out com.example.Main summary
  java -cp out com.example.Main summary --month 6
  ```

### Budget Commands

- **Add a budget:**
  ```sh
  java -cp out com.example.Main budget add --month 6 --amount 500
  ```
- **Update a budget:**
  ```sh
  java -cp out com.example.Main budget update --id 1 --month 6 --amount 600
  ```
- **List budgets:**
  ```sh
  java -cp out com.example.Main budget list
  ```

## Data Files
- `expenses.csv`: Stores all expenses
- `budgets.csv`: Stores all budgets

## Notes
- All data is stored in CSV files in the project root.
- The application will warn you if your expenses for a month exceed the set budget.

## License
MIT

---

Enjoy tracking your expenses and budgets!

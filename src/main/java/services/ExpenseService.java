package services;
import models.Expense;

import java.time.LocalDate;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import models.Budget;
import services.BudgetService;

public class ExpenseService {

     public ExpenseService() {
        loadExpensesFromCsv(); // Load expenses from CSV on initialization
    }

    private static final String expensesCsvFile = "expenses.csv";

    // list to store expenses
    private List<Expense> expenses = new java.util.ArrayList<>();

    public List<Expense> getExpenses() {
        return new java.util.ArrayList<>(expenses); // Return a copy to prevent external modification
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = new java.util.ArrayList<>(expenses); // Create a copy to maintain encapsulation
    }

    public void add(Expense expense) {
        expense.setDescription(expense.getDescription() != null ? expense.getDescription() : "No description provided");
        expense.setAmount(expense.getAmount());
        expense.setDate(LocalDate.now());
        expense.setCategory(expense.getCategory() != null ? expense.getCategory() : "Uncategorized");
        expenses.add(expense);
        // Save to CSV after adding
        saveExpensesToCsv();
        System.out.println("Expense added successfully  (ID: " + expense.getId() + ")");

        if(isMonthExpensesExceedingBudget(expense.getDate().getMonthValue())) {
            System.out.println("Warning: Monthly expenses exceed the budget for " + expense.getDate().getMonth().name());
        }

    }

    public void update(int id, Expense updatedExpense){
        loadExpensesFromCsv(); // Load expenses from CSV before updating
        for (Expense existingExpense : expenses) {
            if (existingExpense.getId() == id) {
                existingExpense.setDescription(updatedExpense.getDescription() != null ? updatedExpense.getDescription() : existingExpense.getDescription());
                existingExpense.setAmount(updatedExpense.getAmount() > 0 ? updatedExpense.getAmount() : existingExpense.getAmount());
                existingExpense.setDate(updatedExpense.getDate() != null ? updatedExpense.getDate() : existingExpense.getDate());
                existingExpense.setCategory(updatedExpense.getCategory() != null ? updatedExpense.getCategory() : existingExpense.getCategory());
                // Save to CSV after updating
                saveExpensesToCsv();
                System.out.println("Expense updated successfully (ID: " + id + ")");
                return;
            }
        }
    }

    // list of all expenses
    public List<Expense> list(String category) {
        loadExpensesFromCsv(); // Load expenses from CSV before listing
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return new java.util.ArrayList<>();
        } else if (category!=null && !category.isEmpty()) {

            System.out.printf("%-4s %-12s %-15s %-8s %-8s%n", "ID", "Date", "Description", "Amount", "Category");
            for (Expense expense : expenses) {
                if(expense.getCategory().equalsIgnoreCase(category)) {
                    // Print only expenses that match the specified category
                    System.out.printf(
                    "%-4d %-12s %-15s $%-7.2f %-8s%n",
                    expense.getId(),
                    expense.getDate(),
                    expense.getDescription(),
                    expense.getAmount(),
                    expense.getCategory()
                );
                }
            }
            return new java.util.ArrayList<>(expenses);
            
        } else{
            // If no category is specified, list all expenses
            System.out.printf("%-4s %-12s %-15s %-8s %-8s%n", "ID", "Date", "Description", "Amount", "Category");
            for (Expense expense : expenses) {
                System.out.printf(
                    "%-4d %-12s %-15s $%-7.2f %-8s%n",
                    expense.getId(),
                    expense.getDate(),
                    expense.getDescription(),
                    expense.getAmount(),
                    expense.getCategory()
                );
            }
            return new java.util.ArrayList<>(expenses);
        }
    }

    public void delete(int id) {
        loadExpensesFromCsv(); // Load expenses from CSV before deleting
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                expenses.remove(expense);
                saveExpensesToCsv(); // Save to CSV after deletion
                System.out.println("Expense deleted successfully (ID: " + id + ")");
                return;
            }
        }
        System.out.println("Expense not found (ID: " + id + ")");
    }

    public void summary(int month){
        loadExpensesFromCsv(); // Load expenses from CSV before summarizing
        double total = 0;
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        if (month < 0 || month > 12) {
            System.out.println("Invalid month. Please enter a value between 1 and 12.");
            return;
        }

        if (month!=0) {
            System.out.println("Summary for month: " + month);
            for (Expense expense : expenses) {
                if (expense.getDate().getMonthValue() == month) {
                total += expense.getAmount();
                }
            }
        } else {
            System.out.println("Summary for all months");
            for (Expense expense : expenses) {
                total += expense.getAmount();
            }
        }

        System.out.println("Total expenses: $"+ total);
    }

    public void saveExpensesToCsv(){
        try(PrintWriter writer = new PrintWriter(new FileWriter(expensesCsvFile))) {
            for (Expense expense : expenses) {
                writer.printf("%d,%s,%.2f,%s,%s%n",
                        expense.getId(),
                        expense.getDescription(),
                        expense.getAmount(),
                        expense.getDate(),
                        expense.getCategory());
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses to CSV: " + e.getMessage());
        }
    }

    public void loadExpensesFromCsv() {
        expenses.clear();
        File file = new File(expensesCsvFile);
        if (!file.exists()) return;
        int maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(expensesCsvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) { // Ensure there are 5 parts: ID, Description, Amount, Date, Category
                    // Parse the parts
                    int id;
                    double amount;
                    LocalDate date;
                    try {
                        id = Integer.parseInt(parts[0]);
                        amount = Double.parseDouble(parts[2]);
                        date = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    } catch (Exception e) {
                        System.out.println("Invalid data in CSV: " + e.getMessage());
                        continue;
                    }
                    if (amount <= 0) {
                        System.out.println("Invalid amount in CSV for ID: " + parts[0]);
                        continue;
                    }
                    String description = parts[1];
                    String category = parts[4];
                    Expense expense = new Expense(description, amount, date, category);
                    expense.setId(id); // Set the ID from the CSV
                    expenses.add(expense);
                    if (id > maxId) maxId = id;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading expenses from CSV: " + e.getMessage());
        }
        // Set the static idCounter in Expense to maxId + 1
        models.Expense.setIdCounter(maxId + 1);
    }

    public void exportCsv(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
        fileName = "expenses.csv";
        } else{
            fileName = fileName + ".csv";
        }
        String downloadPath = System.getProperty("user.home") + "\\Downloads\\";
        String destinationPath = downloadPath + fileName;
        try (PrintWriter writer = new PrintWriter(new FileWriter(destinationPath))) {
            for (Expense expense : expenses) {
                writer.printf("%d,%s,%.2f,%s,%s%n",
                        expense.getId(),
                        expense.getDescription(),
                        expense.getAmount(),
                        expense.getDate(),
                        expense.getCategory());
            }
            System.out.println("Expenses exported to " + destinationPath);
        } catch (IOException e) {
            System.out.println("Error exporting expenses to CSV: " + e.getMessage());
        }
    }

    // check if month expenses exceed budget
    public boolean isMonthExpensesExceedingBudget(int month) {
        loadExpensesFromCsv(); // Load expenses from CSV before checking
        BudgetService.loadBudgetsFromCsv(); // Load budgets from CSV before checking
        double total = 0;
        double budgetAmount = 0;
        for(Budget budget : BudgetService.getBudgets()) {
            if (budget.getMonth() == month) {
                budgetAmount = budget.getAmount();
                for (Expense expense : expenses) {
                    if (expense.getDate().getMonthValue() == month) {
                        total += expense.getAmount();
                    }
                }
            }
        }
        return total >= budgetAmount;
    }
}
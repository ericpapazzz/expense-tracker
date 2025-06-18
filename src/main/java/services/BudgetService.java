package services;

import models.Budget;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class BudgetService {
    
    public BudgetService() {
       loadBudgetsFromCsv(); // Load budgets from CSV on initialization
    }

    private static final String budgetsCsvFile = "budgets.csv";

    // list to store budgets
    private static List<Budget> budgets = new java.util.ArrayList<>();

    public static List<Budget> getBudgets() {
        return budgets;
    }

    // method to add a budget
    public void add(Budget budget) {
        budget.setAmount(budget.getAmount());
        budget.setMonth(budget.getMonth());
        budgets.add(budget);
        // Save to CSV after adding
        saveBudgetToCsv();
        System.out.println("Budget added successfully (ID: " + budget.getId() + ")");
    }

    // method to update a budget
    public void update(int id, Budget updatedBudget) {
        loadBudgetsFromCsv(); // Load budgets from CSV before updating
        for (Budget existingBudget : budgets) {
            if (existingBudget.getId() == id) {
                existingBudget.setAmount(updatedBudget.getAmount() > 0 ? updatedBudget.getAmount() : existingBudget.getAmount());
                existingBudget.setMonth(updatedBudget.getMonth() > 0 ? updatedBudget.getMonth() : existingBudget.getMonth());
                // Save to CSV after updating
                saveBudgetToCsv();
                System.out.println("Budget updated successfully (ID: " + id + ")");
            }
        }
        System.out.println("No budget found with ID: " + id);
    }

    // retrieve all budgets
    public List<Budget> list() {
        loadBudgetsFromCsv(); // Ensure latest data
        System.out.printf("%-4s %-8s %-10s%n", "ID", "Month", "Amount");
        for (Budget budget : budgets) {
            System.out.printf(
                "%-4d %-8d $%-9.2f%n",
                budget.getId(),
                budget.getMonth(),
                budget.getAmount()
            );
        }
        return new ArrayList<>(budgets); // Return a copy of the list
    }

    // method to delete a budget
    public void delete(int id) {
        loadBudgetsFromCsv(); // Load budgets from CSV before deleting
        for (Budget budget : budgets) {
            if (budget.getId() == id) {
                budgets.remove(budget);
                // Save to CSV after deleting
                saveBudgetToCsv();
                System.out.println("Budget deleted successfully (ID: " + id + ")");
            }
        }
        System.out.println("No budget found with ID: " + id);
    }

    public void saveBudgetToCsv() {
        try(PrintWriter writer = new PrintWriter(new FileWriter(budgetsCsvFile))) {
            for(Budget budget : budgets){
                writer.printf("%d,%.2f,%s%n",
                        budget.getId(),
                        budget.getAmount(),
                        budget.getMonth());
            }
        } catch (IOException e) {
            System.out.println("Error saving budget to CSV: " + e.getMessage());
        }
}

    public static void loadBudgetsFromCsv() {
        budgets.clear();
        File file = new File(budgetsCsvFile);
        if (!file.exists()) return;
        int maxId = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(budgetsCsvFile))){
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    double amount = Double.parseDouble(parts[1]);
                    int month = Integer.parseInt(parts[2]);
                    Budget budget = new Budget(id, amount, month);
                    budget.setId(id);
                    budgets.add(budget);
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }
        } catch(IOException e) {
            System.out.println("Error loading budgets from CSV: " + e.getMessage());
        }
        // Update the static idCounter in Budget class
        if (maxId > 0) {
            Budget.setIdCounterB(maxId + 1);
        }
    }

}
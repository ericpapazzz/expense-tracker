package com.example;
import services.ExpenseService;
import models.Expense;
import services.BudgetService;
import models.Budget;

public class Main {
    public static void main(String[] args) {
        ExpenseService expenseService = new ExpenseService();
        BudgetService budgetService = new BudgetService();

        if (args.length == 0) {
            System.out.println("No command provided.");
            return;
        }

        String command = args[0];

        switch (command) {
            case "add":
                String description = "no description provided"; // Default description
                double amount = 0;
                String category = "Uncategorized"; // Default category
                for (int i = 1; i < args.length; i++) {
                    if (args[i].equals("--description")) {
                        description = args[++i];
                    } else if (args[i].equals("--amount")) {
                        amount = Double.parseDouble(args[++i]);
                    } else if (args[i].equals("--category")) {
                        category = args[++i];
                    }
                }
                expenseService.add(new Expense(description, amount, java.time.LocalDate.now(), category));
                break;

            case "update":
                int updateId = -1;
                String updateDescription = null;
                double updateAmount = 0;
                String updateCategory = null;
                java.time.LocalDate updateDate = null;
                for (int i = 1; i < args.length; i++) {
                    if (args[i].equals("--id")) {
                        updateId = Integer.parseInt(args[++i]);
                    } else if (args[i].equals("--description")) {
                        updateDescription = args[++i];
                    } else if (args[i].equals("--amount")) {
                        updateAmount = Double.parseDouble(args[++i]);
                    } else if (args[i].equals("--category")) {
                        updateCategory = args[++i];
                    }
                }
                expenseService.update(updateId, new Expense(updateDescription, updateAmount, updateDate, updateCategory));
                break;

            case "list":
                String categoryFilter = null;
                for (int i = 1; i < args.length; i++) {
                    if (args[i].equals("--category")) {
                        categoryFilter = args[++i];
                    }
                }
                expenseService.list(categoryFilter);
                break;

            case "summary":
                int month = 0; // Default to 0 for all months
                for (int i = 1; i < args.length; i++) {
                    if (args[i].equals("--month")) {
                        month = Integer.parseInt(args[++i]);
                    }
                }
                expenseService.summary(month);
                break;

            case "delete":
                int id = -1;
                for (int i = 1; i < args.length; i++) {
                    if (args[i].equals("--id")) {
                        id = Integer.parseInt(args[++i]);
                    }
                }
                expenseService.delete(id);
                break;

            case "export":
                String fileName = null;
                for (int i = 1; i < args.length; i++) {
                    if (args[i].equals("--name")) {
                        fileName = args[++i];
                    }
                }
                expenseService.exportCsv(fileName);
                break;

            case "budget":
                if (args.length < 2) {
                    System.out.println("No budget subcommand provided.");
                    return;
                }
                String budgetCommand = args[1];
                switch (budgetCommand) {
                    case "add":
                        int budgetMonth = 0;
                        double budgetAmount = 0;
                        for (int i = 2; i < args.length; i++) {
                            if (args[i].equals("--month")) {
                                budgetMonth = Integer.parseInt(args[++i]);
                            } else if (args[i].equals("--amount")) {
                                budgetAmount = Double.parseDouble(args[++i]);
                            }
                        }
                        budgetService.add(new Budget(budgetAmount, budgetMonth));
                        break;
                    case "update":
                        int updateBudgetId = -1;
                        int updateMonth = 0;
                        double updateBudgetAmount = 0;
                        for (int i = 2; i < args.length; i++) {
                            if (args[i].equals("--id")) {
                                updateBudgetId = Integer.parseInt(args[++i]);
                            } else if (args[i].equals("--month")) {
                                updateMonth = Integer.parseInt(args[++i]);
                            } else if (args[i].equals("--amount")) {
                                updateBudgetAmount = Double.parseDouble(args[++i]);
                            }
                        }
                        budgetService.update(updateBudgetId, new Budget(updateBudgetAmount, updateMonth));
                        break;
                    case "list":
                        budgetService.list();
                        break;
                    default:
                        System.out.println("Unknown budget subcommand.");
                }
                break;

            default:
                System.out.println("Unknown command.");
        }
    }
}
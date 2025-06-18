package models;

import java.time.LocalDate;

public class Expense  {

    // counter to auto-generate ids
    private static int idCounter = 1;

    private int id;
    private String description;
    private double amount;
    private LocalDate date;
    private String category;

    // constructor
    public Expense(String description, double amount, LocalDate date, String category) {
        this.id = idCounter++;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    // Constructor for loading from file (manual id)
    public Expense(int id, String description, double amount, LocalDate date, String category) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
        if (id >= idCounter) {
            idCounter = id + 1;
        }
    }

    // getters and setters

    public static int getIdCounter() {
        return idCounter;
    }
    public static void setIdCounter(int idCounter) {
        Expense.idCounter = idCounter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getCategory() {
        return category;
    }
}
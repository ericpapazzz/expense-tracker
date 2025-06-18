package models;

public class Budget {

    // counter to auto-generate ids
    private static int idCounterB = 1;

    private int id;
    private double amount;
    private int month;

    public Budget(double amount, int month) {
        this.id = idCounterB++;
        this.amount = amount;
        this.month = month;
    }

    // Constructor for loading from file (manual id)
    public Budget(int id, double amount, int month) {
        this.id = id;
        this.amount = amount;
        this.month = month;
        if (id >= idCounterB) {
            idCounterB = id + 1;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getMonth() {
        return month;
    }   

    public void setMonth(int month) {
        this.month = month;
    }

    public static int getIdCounterB() {
        return idCounterB;
    }
    
    public static void setIdCounterB(int idCounterB) {
        Budget.idCounterB = idCounterB;
    }
}
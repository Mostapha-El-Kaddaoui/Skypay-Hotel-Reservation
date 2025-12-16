package org.example;

public class Users {
    private final int id;
    private int balance;

    public Users(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void debit(int amount) {
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}

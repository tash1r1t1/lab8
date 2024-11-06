package org.example;
public class AccountType {
    private boolean premium;

    public AccountType(boolean premium) {
        this.premium = premium;
    }

    public boolean isPremium() {
        return premium;
    }

    @Override
    public String toString() {
        return premium ? "premium" : "normal";
    }
}
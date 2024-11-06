package org.example;

public class Customer {

    private String name;
    private String surname;
    private String email;
    private CustomerType customerType;
    private Account account;
    private AccountService accountService;

    public Customer(String name, String surname, String email, CustomerType customerType, Account account) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.customerType = customerType;
        this.account = account;
        this.accountService = new AccountService(account, 1); // Default discount is 1
    }

    // use only to create companies
    public Customer(String name, String email, Account account, double companyOverdraftDiscount) {
        this.name = name;
        this.email = email;
        this.customerType = CustomerType.COMPANY;
        this.account = account;
        this.accountService = new AccountService(account, companyOverdraftDiscount);
    }

    public void withdraw(double sum, String currency) {
        if (!account.getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }

        accountService.withdraw(sum, customerType);
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String printCustomerDaysOverdrawn() {
        String fullName = name + " " + surname + " ";
        String accountDescription = "Account: IBAN: " + account.getIban() + ", Days Overdrawn: " + account.getDaysOverdrawn();
        return fullName + accountDescription;
    }

    public String printCustomerMoney() {
        String fullName = name + " " + surname + " ";
        String accountDescription = "";
        accountDescription += "Account: IBAN: " + account.getIban() + ", Money: " + account.getMoney();
        return fullName + accountDescription;
    }

    public String printCustomerAccount() {
        return "Account: IBAN: " + account.getIban() + ", Money: "
                + account.getMoney() + ", Account type: " + account.getType();
    }
}
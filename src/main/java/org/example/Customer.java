package org.example;
public class Customer {

    private String name;
    private String surname;
    private String email;
    private CustomerType customerType;
    private Account account;
    private double companyOverdraftDiscount = 1;


    public Customer(String name, String surname, String email, CustomerType customerType, Account account) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.customerType = customerType;
        this.account = account;
    }

    // use only to create companies
    public Customer(String name, String email, Account account, double companyOverdraftDiscount) {
        this.name = name;
        this.email = email;
        this.customerType = CustomerType.COMPANY;
        this.account = account;
        this.companyOverdraftDiscount = companyOverdraftDiscount;
    }

    public void withdraw(double sum, String currency) {
        if (!account.getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }

        double overdraftFeeMultiplier = getOverdraftFeeMultiplier();
        processWithdrawal(sum, overdraftFeeMultiplier);
    }

    private double getOverdraftFeeMultiplier() {
        if (account.getType().isPremium()) {
            if (customerType == CustomerType.COMPANY) {
                return companyOverdraftDiscount / 2; // Для компанії на преміум акаунті - знижка 50%
            }
            return 1.0; // Для особи на преміум акаунті множник залишається 1.0
        } else {
            if (customerType == CustomerType.COMPANY) {
                return companyOverdraftDiscount; // Для компанії без преміум акаунта знижка без змін
            }
            return 1.0; // Для особи на звичайному акаунті множник теж 1.0
        }
    }

    private void processWithdrawal(double sum, double overdraftFeeMultiplier) {
        if (account.getMoney() < 0) {
            // Якщо акаунт у овердрафті, застосовуємо відповідний множник
            account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee() * overdraftFeeMultiplier);
        } else {
            // Якщо баланс не від'ємний
            account.setMoney(account.getMoney() - sum);
        }
    }


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
package org.example;

public class AccountService {

    private final Account account;
    private final double companyOverdraftDiscount;

    public AccountService(Account account, double companyOverdraftDiscount) {
        this.account = account;
        this.companyOverdraftDiscount = companyOverdraftDiscount;
    }

    public void withdraw(double sum, CustomerType customerType) {
        double overdraftFeeMultiplier = getOverdraftFeeMultiplier(customerType);

        if (account.getMoney() < 0) {
            // Якщо акаунт у овердрафті, застосовуємо відповідний множник
            account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee() * overdraftFeeMultiplier);
        } else {
            // Якщо баланс не від'ємний
            account.setMoney(account.getMoney() - sum);
        }
    }

    private double getOverdraftFeeMultiplier(CustomerType customerType) {
        if (account.getType().isPremium()) {
            if (customerType == CustomerType.COMPANY) {
                return companyOverdraftDiscount / 2; // Для компанії на преміум аккаунт - знижка 50%
            }
            return 1.0; // Для особи на преміум аккаунт множник залишається 1.0
        } else {
            if (customerType == CustomerType.COMPANY) {
                return companyOverdraftDiscount; // Для компанії без премум аккаунта знижка без змін
            }
            return 1.0; // Для особи на звичайному аккаунт множник теж 1.0
        }
    }
}
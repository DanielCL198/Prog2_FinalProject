public class ChequeingAccount extends Account{

    public ChequeingAccount(String accountNumber, double balance, Client owner) {
        super(accountNumber, balance, owner);
    }

    public void addTransaction(Account target, double amount){
        target.deposit(amount);
        this.balance -= amount;
    }
}

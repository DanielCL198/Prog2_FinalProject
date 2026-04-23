package Accounts;

public abstract class Account {
    public String accountNumber;
    public double balance;
    public Client owner;

    public Account(String accountNumber, double balance, Client owner) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
    }

    public void deposit(double amount){
        this.balance += amount;
    }

    public void deposit(double amount, String description){
        this.balance += amount;
        System.out.println(description);
    }
    public void withdraw(double amount){
        try {
            if(amount > this.balance){
                throw new InsufficientFundsException("Insufficient funds");
            }
            this.balance -= amount;
        }catch(InsufficientFundsException e){
            System.out.println("Exception handled, withdrawal failed");
        }
    }
    public void transfer(Account target, double amount){
        try {
            if (amount > this.balance) {
                throw new InsufficientFundsException("Insufficient funds exception");
            }
            this.balance -= amount;
            target.balance += amount;
        }catch(InsufficientFundsException e){
            System.out.println(e);
            System.out.println("Exception handled, transfer failed");
        }
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

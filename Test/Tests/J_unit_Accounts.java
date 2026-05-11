package Tests;
import Accounts.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class J_unit_Accounts {
    private ChequeingAccount source;
    private ChequeingAccount target;
    private TestClient client;

    // client class for testing
    static class TestClient extends Client {
        public TestClient(String clientID, String name, ArrayList<Account> accounts, String password) {
            super(clientID, name, accounts, password);
        }
    }
    @BeforeEach
    void setUp() {
        client = new TestClient("1", "Daniel", new ArrayList<>(), "1234");
        source = new ChequeingAccount("ACC1", 1000, client);
        target = new ChequeingAccount("ACC2", 500, client);
    }

    // Deposit

    @Test
    void testDeposit() {
        source.deposit(200);
        assertEquals(1200, source.balance);
    }

    @Test
    void testDepositWithDescription() {
        source.deposit(300, "Paycheck");
        assertEquals(1300, source.balance);
    }


    // Withdraw

    @Test
    void testWithdrawSuccess() {
        source.withdraw(400);
        assertEquals(600, source.balance);
    }

    @Test
    void testWithdrawInsufficientFunds() {
        source.withdraw(2000);
        assertEquals(1000, source.balance);
    }


    // Transfers

    @Test
    void testTransferSuccess() {
        source.transfer(target, 300);
        assertEquals(700, source.balance);
        assertEquals(800, target.balance);
    }

    @Test
    void testTransferInsufficientFunds() {
        source.transfer(target, 5000);
        assertEquals(1000, source.balance);
        assertEquals(500, target.balance);
    }


    // Transaction

    @Test
    void testAddTransaction() {
        Transaction t = new Transaction();
        t.amount = 150;
        source.addTransaction(t);
        assertEquals(850, source.balance);
    }


    // Client

    @Test
    void testAddChequeingAccount() {
        ChequeingAccount acc = new ChequeingAccount("C1", 100, client);
        client.addAccount(acc);
        assertEquals(1, client.getAccounts().size());
    }

    @Test
    void testHasChequeingAccount() {
        ChequeingAccount acc = new ChequeingAccount("C1", 100, client);
        client.addAccount(acc);
        assertTrue(client.hasChequeingAccount());
    }

    @Test
    void testAddSavingsWithoutChequeing() {
        SavingAccount savings = new SavingAccount("S1", 100, client);
        try {
            client.addAccount(savings);
            fail("Expected MissingChequeingAccountException");
        } catch (MissingChequeingAccountException e) {
            assertEquals("Client must have a chequeing account to open an investment account or savings account", e.getMessage());
        }
    }


    // Savings

    @Test
    void testSavingsApplyInterest() {
        SavingAccount savings = new SavingAccount("S1", 1000, client);
        savings.applyInterest();
        assertEquals(1020, savings.balance);
    }


    // Investment

    @Test
    void testInvestmentApplyInterest() {
        InvestmentAccount investment = new InvestmentAccount("I1", 1000, client);
        investment.applyInterest();
        assertEquals(1050, investment.balance);
    }

    @Test
    void testInvestmentWithdrawLocked() {
        InvestmentAccount investment = new InvestmentAccount("I1", 1000, client);
        try {
            investment.withdraw(100);
            fail("Expected InvestmentLockException");
        } catch (InvestmentLockException e) {
            assertEquals("Investment account is locked for 1 year.", e.getMessage());
        }
    }


    // Transaction

    @Test
    void testTransactionToString() {
        Transaction t = new Transaction();
        t.transactionId = "T1";
        t.amount = 200;
        t.type = "Deposit";
        assertEquals("Accounts.Transaction: T1 200.0 Deposit", t.toString());
    }
}


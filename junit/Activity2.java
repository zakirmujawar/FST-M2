package activities;

import Activities.BankAccount;
import Activities.NotEnoughFundsException;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Activity2 {

    @Test
    public void notEnoughFunds(){
        BankAccount account = new BankAccount(109);
        assertThrows(NotEnoughFundsException.class,() -> account.withdraw(210),
                "Balance must be greater then withdrawal");
    }

    @Test
    public void enoughFunds(){
        BankAccount account = new BankAccount(200);
        assertDoesNotThrow(()-> account.withdraw(100));
    }
}

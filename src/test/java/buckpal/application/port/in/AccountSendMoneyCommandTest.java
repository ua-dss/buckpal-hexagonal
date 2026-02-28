package buckpal.application.port.in;

import buckpal.application.domain.model.Account;
import buckpal.application.domain.model.Money;
import buckpal.application.port.in.ForSendMoneyAccount.SendMoneyCommand;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

class AccountSendMoneyCommandTest {

    @Test
    public void validationOk() {
        new SendMoneyCommand(
                new Account.AccountId(42L),
                new Account.AccountId(43L),
                new Money(new BigInteger("10")));
        // no exception
    }

    @Test
    public void moneyValidationFails() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            new SendMoneyCommand(
                    new Account.AccountId(42L),
                    new Account.AccountId(43L),
                    new Money(new BigInteger("-10")));
        });
    }

    @Test
    public void accountIdValidationFails() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            new SendMoneyCommand(
                    new Account.AccountId(42L),
                    null,
                    new Money(new BigInteger("10")));
        });
    }

}
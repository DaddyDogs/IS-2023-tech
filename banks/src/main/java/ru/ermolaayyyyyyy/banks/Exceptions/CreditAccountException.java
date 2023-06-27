package ru.ermolaayyyyyyy.banks.Exceptions;

import ru.ermolaayyyyyyy.banks.Interfaces.Bank;

import java.math.BigDecimal;

public class CreditAccountException extends BanksException {

    private CreditAccountException(String message) {
        super(message);
    }

    public static CreditAccountException overstepLimitException(BigDecimal amount)
    {
        if (amount.compareTo(BigDecimal.ZERO) < 0)
        {
            return new CreditAccountException("Amount " + amount + " is not allowed because it's below the limit");
        }

        return new CreditAccountException("Amount " + amount + " is not allowed because it exceeds the limit");
    }

    public static CreditAccountException hasNoClockException()
    {
        return new CreditAccountException("Credit account has no interest percent. You don't need clock for it");
    }
}

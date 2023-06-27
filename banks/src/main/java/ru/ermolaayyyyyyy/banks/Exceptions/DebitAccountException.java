package ru.ermolaayyyyyyy.banks.Exceptions;

import javax.swing.plaf.basic.BasicLookAndFeel;
import java.math.BigDecimal;

public class DebitAccountException extends BanksException {

    private DebitAccountException(String message) {
        super(message);
    }

    public static DebitAccountException insufficientFundsException(BigDecimal amount)
    {
        throw new DebitAccountException("There are insufficient funds on your account. Withdraw of " + amount + " rubles is denied.");
    }
}

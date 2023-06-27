package ru.ermolaayyyyyyy.banks.Exceptions;

import java.math.BigDecimal;

public class AmountException extends BanksException{
    private AmountException(String message) {
        super(message);
    }

    public static AmountException invalidAmountException(BigDecimal amount) {
        throw new AmountException("Amount " + amount + " is invalid. It can't be under 0");
    }
}

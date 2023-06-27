package ru.ermolaayyyyyyy.banks.Exceptions;

import java.math.BigDecimal;

public class ClientStateException extends BanksException{
    private ClientStateException(String message) {
        super(message);
    }

    public static ClientStateException restrictedTransactionException(BigDecimal amount)
    {
        throw new ClientStateException("Can't withdraw" + amount + " rubles before filling out your account");
    }
}

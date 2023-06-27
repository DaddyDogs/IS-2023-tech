package ru.ermolaayyyyyyy.banks.Exceptions;

import ru.ermolaayyyyyyy.banks.Interfaces.Bank;

import java.util.UUID;

public class CentralBankException extends BanksException {

    private CentralBankException(String message) {
        super(message);
    }

    public static CentralBankException bankDoesNotExist(UUID id)
    {
        return new CentralBankException("Bank with id " + id + " is not registered");
    }

    public static CentralBankException transactionDoesNotExist(UUID id)
    {
        return new CentralBankException("Transaction with id " + id + " is not registered");
    }

    public static CentralBankException accountIsNotRegistered(UUID accountId)
    {
        return new CentralBankException("Account with id " + accountId + " is not registered in the bank");
    }
}

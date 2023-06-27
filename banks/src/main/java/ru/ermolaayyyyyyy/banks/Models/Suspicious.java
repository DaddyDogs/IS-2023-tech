package ru.ermolaayyyyyyy.banks.Models;

import ru.ermolaayyyyyyy.banks.Entities.AbstractTransaction;
import ru.ermolaayyyyyyy.banks.Exceptions.ClientStateException;
import ru.ermolaayyyyyyy.banks.Interfaces.ClientState;

import java.math.BigDecimal;

public class Suspicious implements ClientState {

    @Override
    public void check(AbstractTransaction abstractTransaction, BigDecimal newAmount) {
        if (abstractTransaction.getAccount().getAmount().subtract(abstractTransaction.getAccount().getRestriction()).compareTo(newAmount) > 0)
        {
            throw ClientStateException.restrictedTransactionException(abstractTransaction.getAccount().getAmount().subtract(newAmount));
        }
    }
}

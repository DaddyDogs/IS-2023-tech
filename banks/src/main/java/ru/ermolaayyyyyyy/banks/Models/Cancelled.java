package ru.ermolaayyyyyyy.banks.Models;

import ru.ermolaayyyyyyy.banks.Exceptions.TransactionException;
import ru.ermolaayyyyyyy.banks.Interfaces.TransactionState;

public class Cancelled implements TransactionState {
    @Override
    public void tryUndo() {
        throw TransactionException.impossibleCancellation();
    }
}

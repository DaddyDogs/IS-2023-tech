package ru.ermolaayyyyyyy.banks.Interfaces;

public interface TransactionState {
    /**
     * Method that checks if transaction was accomplished and can be cancelled
     */
    void tryUndo();
}

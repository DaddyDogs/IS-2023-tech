package ru.ermolaayyyyyyy.banks.Interfaces;

import ru.ermolaayyyyyyy.banks.Entities.AbstractTransaction;

import java.math.BigDecimal;

public interface ClientState {
    /**
     * Methods that checks if client can accomplish transaction
     *
     * @param abstractTransaction transaction that needs to be checkd
     * @param newAmount sum of money of the transaction
     */
    void check(AbstractTransaction abstractTransaction, BigDecimal newAmount);
}

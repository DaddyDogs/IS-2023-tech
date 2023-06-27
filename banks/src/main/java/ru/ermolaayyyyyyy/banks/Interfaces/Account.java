package ru.ermolaayyyyyyy.banks.Interfaces;

import ru.ermolaayyyyyyy.banks.Models.Amount;

import java.math.BigDecimal;
import java.util.UUID;

public interface Account {
    /**
     * Method that replenishes money on account
     *
     * @param amount for deposit
     */
    void depositMoney(Amount amount);

    /**
     * Method that withdraws money from account
     *
     * @param amount of money to withdraw from account
     */
    void withDrawMoney(Amount amount);

    /**
     * Method that subtracts commission from account
     */
    void subtractCommission();

    /**
     * Method that calculates fee that should be withdrawn after transaction from credit account
     *
     * @param subtrahend amount of money of transaction that client wants to accomplish
     * @return amount of money that should be withdrawn
     */
    BigDecimal calculateFee(BigDecimal subtrahend);
    Clock getClock();
    UUID getId();
    BigDecimal getAmount();
    BigDecimal getRestriction();
}

package ru.ermolaayyyyyyy.banks.Interfaces;

import ru.ermolaayyyyyyy.banks.Entities.Client.Client;
import ru.ermolaayyyyyyy.banks.Models.CreditConditions;
import ru.ermolaayyyyyyy.banks.Models.DebitConditions;
import ru.ermolaayyyyyyy.banks.Models.DepositConditions;
import ru.ermolaayyyyyyy.banks.Models.Amount;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public interface CentralBank {
    /**
     * Method that creates bank
     * @param name
     * @param depositConditions conditions for deposit account
     * @param creditConditions conditions for credit account
     * @param debitConditions conditions for debit account
     * @return created Bank
     */
    Bank createBank(String name, DepositConditions depositConditions, CreditConditions creditConditions, DebitConditions debitConditions);

    /**
     * Method that replenishes account
     *
     * @param amount sum of money to put on account
     * @param accountId id of account that should be replenished
     * @return id of transaction
     */
    UUID replenishAccount(Amount amount, UUID accountId);

    /**
     * Method that transfers money from one account to another
     *
     * @param amount sum of money to transfer
     * @param accountId id of account that sends money
     * @param recipientId id of account that gets money
     * @return id of transaction
     */
    UUID transferMoney(Amount amount, UUID accountId, UUID recipientId);

    /**
     * Method that withdraws money from account
     *
     * @param amount sum of money to withdraw
     * @param accountId id of account from which withdraw money
     * @return id of transaction
     */
    UUID withDrawMoney(Amount amount, UUID accountId);

    /**
     * Method that cancel transaction by its id
     *
     * @param transactionId if of transaction that should be cancelled
     */
    void cancelTransaction(UUID transactionId);
    Bank getBank(UUID id);
    Client getClient(UUID accountId);

    /**
     * Method that accelerate time to check if interest payment works correctly
     *
     * @param daysNumber count of passed days
     * @param accountId id of account for which we should accelerate time and pay the interest
     */
    void accelerate(int daysNumber, UUID accountId);
    List<Bank> getBanksList();
}

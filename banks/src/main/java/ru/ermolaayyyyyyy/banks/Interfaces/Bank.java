package ru.ermolaayyyyyyy.banks.Interfaces;

import ru.ermolaayyyyyyy.banks.Entities.Client.Client;
import ru.ermolaayyyyyyy.banks.Models.Address.Address;
import ru.ermolaayyyyyyy.banks.Models.Amount;
import ru.ermolaayyyyyyy.banks.Models.CreditConditions;
import ru.ermolaayyyyyyy.banks.Models.DebitConditions;
import ru.ermolaayyyyyyy.banks.Models.Passport;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public interface Bank {
    /**
     *     Method for registering credit account of a client
     *
     * @param client which credit account has to be created
     * @return id of created account
     */
    UUID registerCreditAccount(Client client);

    /**
     *     Method for registering debit account of a client
     *
     * @param client which debit account has to be created
     * @return id of created account
     */
    UUID registerDebitAccount(Client client);

    /**
     *     Method for registering deposit account of a client
     *
     * @param client which credit account has to be created
     * @return id of created account
     */
    UUID registerDepositAccount(Client client, Amount amount);

    /**
     * Method that register client and add it to the common system of Central Bank
     *
     * @param firstName of client
     * @param lastName of client
     * @param address where client lives
     * @param passport data of client
     * @return created client
     */
    Client registerClient(String firstName, String lastName, Address address, Passport passport);

    /**
     * Method that changes interest percent for a new one
     *
     * @param newPercent for changing an old percent
     */
    void changeInterestPercent(Amount newPercent);

    /**
     * Method that changes credit limit
     *
     * @param newLimit for changing credit limit
     */
    void changeCreditLimit(Amount newLimit);
    UUID getId();
    CreditConditions getCreditConditions();
    DebitConditions getDebitConditions();
    String getName();
    List<Client> getClientList();

    /**
     * Method that finds a client by his account id or return null if such account is not registered
     *
     * @param accountId id of account which owner should be found
     * @return id of found owner of account
     */
    public Client findClientByAccountId(UUID accountId);

    /**
     * Method that returns amount of money on the account by account ID
     *
     * @param accountId account which amount is needed
     * @return sum of money on the account
     */
    public BigDecimal getMoneyAmount(UUID accountId);
}

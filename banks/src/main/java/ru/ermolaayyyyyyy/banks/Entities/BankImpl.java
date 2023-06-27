package ru.ermolaayyyyyyy.banks.Entities;

import lombok.Getter;
import ru.ermolaayyyyyyy.banks.Entities.Client.Client;
import ru.ermolaayyyyyyy.banks.Exceptions.ClientException;
import ru.ermolaayyyyyyy.banks.Interfaces.Account;
import ru.ermolaayyyyyyy.banks.Interfaces.Bank;
import ru.ermolaayyyyyyy.banks.Models.Address.Address;
import ru.ermolaayyyyyyy.banks.Models.Amount;
import ru.ermolaayyyyyyy.banks.Models.CreditConditions;
import ru.ermolaayyyyyyy.banks.Models.DebitConditions;
import ru.ermolaayyyyyyy.banks.Models.DepositConditions;
import ru.ermolaayyyyyyy.banks.Models.Passport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BankImpl implements Bank {
    private final ArrayList<Client> clients = new ArrayList<>();
    @Getter
    public CreditConditions creditConditions;
    @Getter
    public DebitConditions debitConditions;
    @Getter
    public DepositConditions depositConditions;
    @Getter

    public List<Client> clientList = Collections.unmodifiableList(clients);
    @Getter
    public UUID id;
    @Getter
    public String name;

    public BankImpl(String name, DepositConditions depositConditions, CreditConditions creditConditions, DebitConditions debitConditions) {
        id = UUID.randomUUID();
        if (name == null || name.isEmpty())
        {
            throw new IllegalArgumentException(name);
        }

        this.name = name;
        this.creditConditions = creditConditions;
        this.debitConditions = debitConditions;
        this.depositConditions = depositConditions;
    }

    @Override
    public UUID registerCreditAccount(Client client) {
        getClient(client.id);

        var newAccount = new CreditAccount(creditConditions, id);
        client.addAccount(newAccount);
        return newAccount.id;
    }


    /**
     *     Method for registering debit account of a client
     *
     * @param client which debit account has to be created
     * @return id of created account
     */

    @Override
    public UUID registerDebitAccount(Client client) {
        getClient(client.id);

        var newAccount = new DebitAccount(debitConditions, id);
        client.addAccount(newAccount);
        return newAccount.id;
    }
    @Override
    public UUID registerDepositAccount(Client client, Amount amount) {
        getClient(client.id);

        var newAccount = new DepositAccount(depositConditions, id, amount);
        client.addAccount(newAccount);
        return newAccount.id;
    }
    @Override
    public Client registerClient(String firstName, String lastName, Address address, Passport passport) {
        Client client = Client.getBuilder().withName(firstName, lastName).withAddress(address).withPassport(passport).build();
        clients.add(client);
        return client;
    }

    @Override
    public void changeInterestPercent(Amount newPercent) {
        debitConditions.changePercent(newPercent);
    }

    @Override
    public void changeCreditLimit(Amount newLimit) {
        creditConditions.changeLimit(newLimit);
    }

    private Account getAccountById(UUID accountId) {
        for (Client client : clients){
            for (Account account : client.accountList){
                if (account.getId() == accountId){
                    return account;
                }
            }
        }
        throw ClientException.accountIsNotRegistered(accountId);
    }
    @Override
    public Client findClientByAccountId(UUID accountId) {
        for (Client client : clients){
            for (Account account : client.accountList){
                if (account.getId() == accountId){
                    return client;
                }
            }
        }
        return null;
    }
    @Override
    public BigDecimal getMoneyAmount(UUID accountId) {
        Account account = getAccountById(accountId);
        return getAccountById(accountId).getAmount();
    }

    private Client getClient(UUID clientId)
    {
        Client client = clients.stream().filter(c -> c.id == clientId).findAny().orElse(null);
        if (client == null)
        {
            throw ClientException.clientIsNotRegistered(clientId);
        }

        return client;
    }
}

package ru.ermolaayyyyyyy.banks.Services;

import lombok.Getter;
import ru.ermolaayyyyyyy.banks.Entities.*;
import ru.ermolaayyyyyyy.banks.Entities.Client.Client;
import ru.ermolaayyyyyyy.banks.Exceptions.CentralBankException;
import ru.ermolaayyyyyyy.banks.Exceptions.ClientException;
import ru.ermolaayyyyyyy.banks.Interfaces.Account;
import ru.ermolaayyyyyyy.banks.Interfaces.Bank;
import ru.ermolaayyyyyyy.banks.Interfaces.CentralBank;
import ru.ermolaayyyyyyy.banks.Models.Amount;
import ru.ermolaayyyyyyy.banks.Models.CreditConditions;
import ru.ermolaayyyyyyy.banks.Models.DebitConditions;
import ru.ermolaayyyyyyy.banks.Models.DepositConditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CentralBankImpl implements CentralBank {
    private final ArrayList<Bank> banks = new ArrayList<>();
    private final ArrayList<AbstractTransaction> transactions = new ArrayList<>();
    @Getter
    public List<Bank> banksList = Collections.unmodifiableList(banks);
    @Override
    public Bank createBank(String name, DepositConditions depositConditions, CreditConditions creditConditions, DebitConditions debitConditions) {
        var bank = new BankImpl(name, depositConditions, creditConditions, debitConditions);
        banks.add(bank);
        return bank;
    }

    @Override
    public UUID replenishAccount(Amount amount, UUID accountId) {
        Client client = getClient(accountId);

        Account account = client.accountList.stream().filter(acc -> acc.getId() == accountId).findAny().orElse(null);
        AbstractTransaction replenishment = new Replenishment(amount.sum, account);

        replenishment.Execute(client.state);
        transactions.add(replenishment);
        return replenishment.getId();
    }

    @Override
    public UUID transferMoney(Amount amount, UUID accountId, UUID recipientId) {
        Client client = getClient(accountId);
        Client recipient = getClient(recipientId);

        Account account = client.accountList.stream().filter(acc -> acc.getId() == accountId).findAny().orElse(null);
        Account recipientAccount = recipient.accountList.stream().filter(acc -> acc.getId() == recipientId).findAny().orElse(null);

        AbstractTransaction transfer = new Transfer(amount.sum, account, recipientAccount);

        transfer.Execute(client.state);
        transactions.add(transfer);
        return transfer.getId();
    }

    @Override
    public UUID withDrawMoney(Amount amount, UUID accountId) {
        Client client = getClient(accountId);

        Account account = client.accountList.stream().filter(acc -> acc.getId() == accountId).findAny().orElse(null);

        AbstractTransaction withdrawal = new Withdrawal(amount.sum, account);

        withdrawal.Execute(client.state);
        transactions.add(withdrawal);
        return withdrawal.getId();
    }

    @Override
    public void cancelTransaction(UUID transactionId) {
        AbstractTransaction transaction = transactions.stream().filter(t -> t.getId() == transactionId).findAny().orElse(null);
        if (transaction == null) {
            throw CentralBankException.transactionDoesNotExist(transactionId);
        }

        transaction.undo();
    }

    @Override
    public Bank getBank(UUID id) {
        Bank bank = banks.stream().filter(b -> b.getId() == id).findAny().orElse(null);
        if (bank == null) {
            throw CentralBankException.bankDoesNotExist(id);
        }

        return bank;
    }

    @Override
    public Client getClient(UUID accountId) {
        Client client = null;
        for (Bank b : banks) {
            client = b.findClientByAccountId(accountId);
        }
        if (client == null) {
            throw CentralBankException.accountIsNotRegistered(accountId);
        }

        return client;
    }

    @Override
    public void accelerate(int daysNumber, UUID accountId) {
        Account account = getClient(accountId).accountList.stream().filter(acc -> acc.getId() == accountId).findAny().orElse(null);
        if (account == null) {
            throw ClientException.accountIsNotRegistered(accountId);
        }
        account.getClock().accelerate(daysNumber);
    }
}

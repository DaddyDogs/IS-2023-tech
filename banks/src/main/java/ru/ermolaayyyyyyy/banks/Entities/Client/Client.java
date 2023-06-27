package ru.ermolaayyyyyyy.banks.Entities.Client;

import lombok.Getter;
import ru.ermolaayyyyyyy.banks.Exceptions.ClientException;
import ru.ermolaayyyyyyy.banks.Interfaces.*;
import ru.ermolaayyyyyyy.banks.Models.Address.Address;
import ru.ermolaayyyyyyy.banks.Models.Passport;
import ru.ermolaayyyyyyy.banks.Models.Reliable;
import ru.ermolaayyyyyyy.banks.Models.Suspicious;
import ru.ermolaayyyyyyy.banks.Services.ConsoleNotifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Client implements Observer {
    private final ArrayList<Account> accounts = new ArrayList<>();
    private final Suspicious suspicious = new Suspicious();
    private final Reliable reliable = new Reliable();
    private Notifier notifier = new ConsoleNotifier();
    private Client(String firstName, String lastName, Address address, Passport passport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.passport = passport;
        if (this.address == null || this.passport == null)
        {
            state = suspicious;
        }
        else
        {
            state = reliable;
        }

        id = UUID.randomUUID();
        accountList = Collections.unmodifiableList(accounts);
    }

    public static NameBuilder getBuilder() {
        return new ClientBuilderImpl();
    };

    public List<Account> accountList;
    @Getter
    public String firstName;
    @Getter
    public String lastName;
    @Getter
    public Address address;
    @Getter
    public Passport passport;
    @Getter
    public ClientState state;
    @Getter
    public UUID id;

    public void setAddress(Address address)
    {
        this.address = address;
        if (passport != null)
        {
            state = reliable;
        }
    }

    public void setPassport(int series, int number)
    {
        passport = new Passport(series, number);
        if (address != null)
        {
            state = reliable;
        }
    }

    public void addAccount(Account account) {
        if (accounts.contains(account)) {
            throw ClientException.accountIsAlreadyRegistered(account.getId());
        }

        accounts.add(account);
    }

    public void subscribeToLimitUpdate(Bank bank)
    {
        bank.getCreditConditions().addObserver(this);
    }

    public void unsubscribeToLimitUpdate(Bank bank)
    {
        bank.getCreditConditions().removeObserver(this);
    }

    public void subscribeToPercentUpdate(Bank bank)
    {
        bank.getDebitConditions().addObserver(this);
    }

    public void unsubscribeToPercentUpdate(Bank bank)
    {
        bank.getDebitConditions().removeObserver(this);
    }

    public void update(String subject, BigDecimal newValue)
    {
        notifier.notify(subject, newValue);
    }

    public void setNotifier(Notifier notifier)
    {
        this.notifier = notifier;
    }

    private static class ClientBuilderImpl implements NameBuilder, ClientBuilder {
        private String firstName;
        private String lastName;
        private Address address;
        private Passport passport;

        public ClientBuilder withName(String firstName, String lastName) {
            if (firstName == null || firstName.isEmpty())
            {
                throw new IllegalArgumentException(firstName);
            }
            if (lastName == null || lastName.isEmpty())
            {
                throw new IllegalArgumentException(lastName);
            }

            this.firstName = firstName;
            this.lastName = lastName;
            return this;
        }

        public ClientBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public ClientBuilder withPassport(Passport passport) {
            this.passport = passport;
            return this;
        }

        public Client build() {
            if (firstName == null || lastName == null)
            {
                throw new IllegalArgumentException();
            }

            return new Client(firstName, lastName, address, passport);
        }
    }
}

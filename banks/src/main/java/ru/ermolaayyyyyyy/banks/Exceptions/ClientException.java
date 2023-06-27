package ru.ermolaayyyyyyy.banks.Exceptions;

import java.util.UUID;

public class ClientException extends BanksException{

    private ClientException(String message) {
        super(message);
    }

    public static ClientException accountIsAlreadyRegistered(UUID id)
    {
        return new ClientException("Account with id " + id + " is already registered for this client");
    }
    public static ClientException clientIsNotRegistered(UUID clientId)
    {
        return new ClientException("Client with id " + clientId + " is not registered in the bank");
    }


    public static ClientException clientIsAlreadySubscribed()
    {
        throw new ClientException("Can't subscribe client because he is already subscribed");
    }

    public static ClientException clientIsNotSubscribed()
    {
        throw new ClientException("Can't unsubscribe client because he is not subscribed");
    }

    public static ClientException accountIsNotRegistered(UUID accountId)
    {
        return new ClientException("Account with id" + accountId + " is not registered in the bank");
    }
}

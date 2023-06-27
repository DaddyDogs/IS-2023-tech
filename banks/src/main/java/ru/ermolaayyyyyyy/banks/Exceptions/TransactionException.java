package ru.ermolaayyyyyyy.banks.Exceptions;

public class TransactionException extends BanksException{

    protected TransactionException(String message) {
        super(message);
    }

    public static TransactionException impossibleCancellation()
    {
        return new TransactionException("Can't cancel transaction. It has not accomplished yet");
    }
}

package ru.ermolaayyyyyyy.banks.Exceptions;

public class AddressException extends BanksException{
    private AddressException(String message) {
        super(message);
    }

    public static AddressException invalidNumberException(String subject, int number) {
        throw new AddressException(subject + " number " + number + " is invalid. It can't be under 1");
    }
}

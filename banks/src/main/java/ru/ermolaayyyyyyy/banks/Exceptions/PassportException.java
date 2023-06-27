package ru.ermolaayyyyyyy.banks.Exceptions;

public class PassportException extends BanksException{
    protected PassportException(String message) {
        super(message);
    }

    public static PassportException invalidPassportSeriesException(int series)
    {
        throw new PassportException("Series " + series + " is invalid. It should contains 4 numbers");
    }

    public static PassportException invalidPassportNumberException(int number)
    {
        throw new PassportException("Number " + number + " is invalid. It should contains 6 numbers");
    }
}

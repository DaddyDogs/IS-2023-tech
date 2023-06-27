package ru.ermolaayyyyyyy.banks.Models;

import lombok.Getter;
import ru.ermolaayyyyyyy.banks.Exceptions.PassportException;

@Getter
public class Passport {
    public Passport(int series, int number) {
        if (Integer.toString(series).length() != 4) {
            throw PassportException.invalidPassportSeriesException(series);
        }

        if (Integer.toString(number).length() != 6) {
            throw PassportException.invalidPassportNumberException(number);
        }

        this.series = series;
        this.number = number;
    }

    public int series;
    public int number;
}

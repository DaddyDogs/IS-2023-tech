package ru.ermolaayyyyyyy.banks.Models;

import java.math.BigDecimal;
import lombok.Getter;
import ru.ermolaayyyyyyy.banks.Exceptions.AmountException;

/**
 * Class for easier work with money that cannot be under 0
 */
@Getter
public class Amount {
    public Amount(int amount) {
        BigDecimal newAmount = BigDecimal.valueOf(amount);
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw AmountException.invalidAmountException(newAmount);
        }

        sum = newAmount;
    }

    public Amount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw AmountException.invalidAmountException(amount);
        }

        sum = amount;
    }

    public Amount(String amount) {
        BigDecimal newAmount = new BigDecimal(amount);
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw AmountException.invalidAmountException(newAmount);
        }

        sum = newAmount;
    }

    public BigDecimal sum;
}

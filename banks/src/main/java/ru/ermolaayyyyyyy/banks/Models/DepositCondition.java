package ru.ermolaayyyyyyy.banks.Models;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Class for easier work with deposit conditions
 */
@Getter
public class DepositCondition {
    public DepositCondition(Amount maximalAmount, Amount percent) {
        this.maximalAmount = maximalAmount.sum;
        this.percent = percent.sum;
    }

    public BigDecimal maximalAmount;
    public BigDecimal percent;
}

package ru.ermolaayyyyyyy.banks.Entities;

import lombok.Getter;
import ru.ermolaayyyyyyy.banks.Exceptions.DebitAccountException;
import ru.ermolaayyyyyyy.banks.Interfaces.Account;
import ru.ermolaayyyyyyy.banks.Interfaces.Clock;
import ru.ermolaayyyyyyy.banks.Models.Amount;
import ru.ermolaayyyyyyy.banks.Models.DebitConditions;
import ru.ermolaayyyyyyy.banks.Services.ClockImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.*;

public class DebitAccount implements Account {

    private final Clock clock = new ClockImpl(new addInterest(), new payInterest());
    private BigDecimal saved;
    public DebitAccount(DebitConditions conditions, UUID bankId)
    {
        amount = BigDecimal.ZERO;
        this.conditions = conditions;
        this.bankId = bankId;
        id = UUID.randomUUID();
        saved = BigDecimal.ZERO;
    }

    @Getter
    public DebitConditions conditions;
    @Getter
    public BigDecimal amount;
    @Getter
    public UUID id;
    public UUID bankId;

    private class payInterest extends TimerTask{

        @Override
        public void run() {
            amount = amount.add(saved);
            saved = BigDecimal.ZERO;
        }
    }

    private class addInterest extends TimerTask{
        @Override
        public void run() {
        boolean leap = Year.now().isLeap();
        saved = saved.add(amount.multiply(conditions.percent.divide(leap ? BigDecimal.valueOf(366) : BigDecimal.valueOf(365), RoundingMode.HALF_DOWN)));
        }
    }

    @Override
    public void depositMoney(Amount amount) {

        this.amount = this.amount.add(amount.sum);
    }

    @Override
    public void withDrawMoney(Amount amount) {
        if (this.amount.subtract(amount.sum).compareTo(BigDecimal.ZERO) < 0)
        {
            throw DebitAccountException.insufficientFundsException(amount.sum);
        }
        this.amount = this.amount.subtract(amount.sum);
    }

    @Override
    public void subtractCommission() {

    }

    @Override
    public BigDecimal calculateFee(BigDecimal subtrahend) {
        return null;
    }

    @Override
    public Clock getClock() {
        return null;
    }

    @Override
    public BigDecimal getRestriction() {
        return getConditions().restriction;
    }
}

package ru.ermolaayyyyyyy.banks.Entities;

import lombok.Getter;
import ru.ermolaayyyyyyy.banks.Exceptions.DepositAccountException;
import ru.ermolaayyyyyyy.banks.Interfaces.Account;
import ru.ermolaayyyyyyy.banks.Interfaces.Clock;
import ru.ermolaayyyyyyy.banks.Models.Amount;
import ru.ermolaayyyyyyy.banks.Models.DepositCondition;
import ru.ermolaayyyyyyy.banks.Models.DepositConditions;
import ru.ermolaayyyyyyy.banks.Services.ClockImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.Date;
import java.util.Objects;
import java.util.TimerTask;
import java.util.UUID;

public class DepositAccount implements Account {
    private final long DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24;
    private final Clock clock = new ClockImpl(new addInterest(), new payInterest());
    private BigDecimal saved;
    public DepositAccount(DepositConditions conditions, UUID bankId, Amount amount)
    {
        for (DepositCondition condition : conditions.depositConditionList) {
            if (amount.sum.compareTo(condition.maximalAmount) >= 0) continue;
            percent = condition.percent;
            break;
        }

        if (Objects.equals(percent, BigDecimal.ZERO)) {
            throw DepositAccountException.invalidDepositAmount(amount.sum);
        }

        saved = BigDecimal.ZERO;
        this.amount = amount.sum;
        restriction = conditions.restriction;
        this.bankId = bankId;
        expiryDate = new Date(new Date().getTime() + conditions.term * 24 * DAY_IN_MILLISECONDS);
        id = UUID.randomUUID();
    }

    @Getter
    public BigDecimal restriction;
    @Getter
    public BigDecimal percent = BigDecimal.ZERO;
    @Getter
    public BigDecimal amount;
    @Getter
    public Date expiryDate;
    @Getter
    public UUID id;
    @Getter
    public UUID bankId;

    private class payInterest extends TimerTask {

        @Override
        public void run() {
            amount = amount.add(saved);
            saved = BigDecimal.ZERO;
        }
    }

    private class addInterest extends TimerTask {
        @Override
        public void run() {
            boolean leap = Year.now().isLeap();
            saved = saved.add(amount.multiply(percent.divide(leap ? BigDecimal.valueOf(366) : BigDecimal.valueOf(365), RoundingMode.HALF_EVEN)));
        }
    }
    @Override
    public void depositMoney(Amount amount) {

        this.amount = this.amount.add(amount.sum);
    }

    @Override
    public void withDrawMoney(Amount amount) {
        if (new Date().before(expiryDate)) {
            throw DepositAccountException.deniedWithdrawingException(expiryDate);
        }

        this.amount = this.amount.subtract(amount.sum);
    }

    @Override
    public void subtractCommission() {

    }

    @Override
    public BigDecimal calculateFee(BigDecimal subtrahend) {

        return BigDecimal.ZERO;
    }

    @Override
    public Clock getClock() {

        return clock;
    }
}

package ru.ermolaayyyyyyy.banks.Entities;

import lombok.Getter;
import ru.ermolaayyyyyyy.banks.Exceptions.CreditAccountException;
import ru.ermolaayyyyyyy.banks.Interfaces.Account;
import ru.ermolaayyyyyyy.banks.Interfaces.Clock;
import ru.ermolaayyyyyyy.banks.Models.Amount;
import ru.ermolaayyyyyyy.banks.Models.CreditConditions;

import java.math.BigDecimal;
import java.util.UUID;

public class CreditAccount implements Account {
    public CreditAccount(CreditConditions conditions, UUID bankId)
    {
        amount = BigDecimal.ZERO;
        this.conditions = conditions;
        id = UUID.randomUUID();
        this.bankId = bankId;
    }
    @Getter
    public CreditConditions conditions;

    @Getter
    public BigDecimal amount;

    @Getter
    public UUID id;

    @Getter
    public UUID bankId;


    @Override
    public void depositMoney(Amount amount) {
        if (this.amount.add(amount.sum).compareTo(conditions.limit) > 0)
        {
            throw CreditAccountException.overstepLimitException(this.amount.add(amount.sum));
        }

        this.amount = this.amount.add(amount.sum);
    }

    @Override
    public void withDrawMoney(Amount amount) {
        if (this.amount.subtract(amount.sum).abs().compareTo(conditions.limit) > 0)
        {
            throw CreditAccountException.overstepLimitException(this.amount.subtract(amount.sum));
        }

        this.amount = this.amount.subtract(amount.sum);
        if (this.amount.compareTo(BigDecimal.ZERO) < 0)
        {
            this.amount = this.amount.subtract(conditions.fee);
        }
    }
    @Override
    public void subtractCommission() {
        if (amount.compareTo(BigDecimal.ZERO) < 0)
        {
            amount = amount.subtract(conditions.fee);
        }
    }
    @Override
    public BigDecimal calculateFee(BigDecimal subtrahend) {
        return amount.subtract(subtrahend).compareTo(BigDecimal.ZERO) < 0 ? conditions.fee : BigDecimal.ZERO;
    }

    @Override
    public Clock getClock() {
        throw CreditAccountException.hasNoClockException();
    }

    @Override
    public BigDecimal getRestriction() {
        return getConditions().restriction;
    }
}

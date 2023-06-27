package ru.ermolaayyyyyyy.banks.Entities;

import lombok.Getter;
import ru.ermolaayyyyyyy.banks.Interfaces.Account;
import ru.ermolaayyyyyyy.banks.Interfaces.TransactionState;
import ru.ermolaayyyyyyy.banks.Models.Accomplished;
import ru.ermolaayyyyyyy.banks.Models.Amount;
import ru.ermolaayyyyyyy.banks.Models.Cancelled;
import ru.ermolaayyyyyyy.banks.Models.Executing;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Withdrawal extends AbstractTransaction{
    private final Amount amount;
    private final UUID id;
    private final Account account;
    private BigDecimal fee = BigDecimal.ZERO;
    private TransactionState state = new Executing();

    public Withdrawal(BigDecimal amount, Account account) {
        this.amount = new Amount(amount);
        this.account = account;
        id = UUID.randomUUID();
    }

    @Override
    public void undo() {
        state.tryUndo();
        account.depositMoney(new Amount(amount.sum.add(fee)));
        state = new Cancelled();
    }

    @Override
    protected BigDecimal evaluate() {
        return account.getAmount().subtract(amount.sum);
    }

    @Override
    protected void accomplish() {
        fee = account.calculateFee(amount.sum);
        account.withDrawMoney(amount);
        state = new Accomplished();
    }
}

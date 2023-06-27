package ru.ermolaayyyyyyy.banks.Entities;

import lombok.Getter;
import ru.ermolaayyyyyyy.banks.Interfaces.Account;
import ru.ermolaayyyyyyy.banks.Interfaces.TransactionState;
import ru.ermolaayyyyyyy.banks.Models.Amount;
import ru.ermolaayyyyyyy.banks.Models.Cancelled;
import ru.ermolaayyyyyyy.banks.Models.Executing;
import ru.ermolaayyyyyyy.banks.Models.Accomplished;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
public class Replenishment extends AbstractTransaction{
    private final Amount amount;
    private final UUID id;
    private final Account account;
    private TransactionState state = new Executing();

    public Replenishment(BigDecimal amount, Account account) {
        this.amount = new Amount(amount);
        this.account = account;
        id = UUID.randomUUID();
    }

    @Override
    public void undo() {
        state.tryUndo();
        account.withDrawMoney(amount);
        state = new Cancelled();
    }

    @Override
    protected BigDecimal evaluate() {
        return amount.sum.add(account.getAmount());
    }

    @Override
    protected void accomplish() {
        account.depositMoney(amount);
        state = new Accomplished();
    }
}

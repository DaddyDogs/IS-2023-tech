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
public class Transfer extends AbstractTransaction{
    private final Amount amount;
    private final UUID id;
    private final Account account;
    private final Account recipient;
    private BigDecimal fee = BigDecimal.ZERO;
    private TransactionState state = new Executing();

    public Transfer(BigDecimal amount, Account account, Account recipient) {
        this.amount = new Amount(amount);
        this.id = UUID.randomUUID();
        this.account = account;
        this.recipient = recipient;
    }

    @Override
    public void undo() {
        state.tryUndo();
        recipient.withDrawMoney(amount);
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
        recipient.depositMoney(amount);
        state = new Accomplished();
    }
}

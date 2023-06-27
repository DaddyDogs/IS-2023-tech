package ru.ermolaayyyyyyy.banks.Entities;

import ru.ermolaayyyyyyy.banks.Interfaces.Account;
import ru.ermolaayyyyyyy.banks.Interfaces.ClientState;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class AbstractTransaction {

    /**
     *     Method that checks a state of client and accomplish transaction
     *
     * @param clientState the state of client (suspicious or no)
     */
    public void Execute(ClientState clientState) {
        BigDecimal newAmount = evaluate();
        clientState.check(this, newAmount);
        accomplish();
    }

    /**
     * Method that cancels transaction
     */
    public abstract void undo();

    /**
     * Method that evaluates money that will be on account after transaction
     *
     * @return amount of money on account after that transaction
     */
    protected abstract BigDecimal evaluate();

    /**
     * Method that accomplishes transaction
     */
    protected abstract void accomplish();
    public abstract Account getAccount();
    public abstract UUID getId();
}

package ru.ermolaayyyyyyy.banks.Models;

import ru.ermolaayyyyyyy.banks.Entities.AbstractTransaction;
import ru.ermolaayyyyyyy.banks.Interfaces.ClientState;

import java.math.BigDecimal;

public class Reliable implements ClientState {
    @Override
    public void check(AbstractTransaction abstractTransaction, BigDecimal newAmount) {

    }
}

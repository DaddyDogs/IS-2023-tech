package ru.ermolaayyyyyyy.banks.Models;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class DepositConditions {
    public static final int FEE = 0;
    private ArrayList<DepositCondition> depositConditions = new ArrayList<>();

    public DepositConditions(ArrayList<DepositCondition> depositConditions, int term, Amount restriction) {
        this.depositConditions = depositConditions;
        this.term = term;
        this.restriction = restriction.sum;
        this.depositConditionList = Collections.unmodifiableList(depositConditions);

    }

    public int term;
    public BigDecimal restriction;

    public List<DepositCondition> depositConditionList;
}

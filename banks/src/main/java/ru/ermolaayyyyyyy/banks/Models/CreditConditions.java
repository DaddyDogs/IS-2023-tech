package ru.ermolaayyyyyyy.banks.Models;

import lombok.Getter;
import ru.ermolaayyyyyyy.banks.Exceptions.ClientException;
import ru.ermolaayyyyyyy.banks.Interfaces.Observer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CreditConditions {
    public static final BigDecimal Percent = BigDecimal.ZERO;
    private final List<Observer> observers = new ArrayList<Observer>();
    public CreditConditions(Amount limit, Amount fee, Amount restriction) {
        this.limit = limit.sum;
        this.fee = fee.sum;
        this.restriction = restriction.sum;
    }
    public BigDecimal limit;
    public BigDecimal fee;
    public BigDecimal restriction;

    public void notifyObservers(BigDecimal newValue) {
        for (Observer observer : observers)
        {
            observer.update("Credit limit", newValue);
        }
    }

    public void addObserver(Observer observer) {
        if (observers.contains(observer))
        {
            throw ClientException.clientIsAlreadySubscribed();
        }

        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        if (!observers.contains(observer))
        {
            throw ClientException.clientIsNotSubscribed();
        }

        observers.remove(observer);
    }

    public void changeLimit(Amount newLimit) {
        limit = newLimit.sum;
        notifyObservers(newLimit.sum);
    }
}

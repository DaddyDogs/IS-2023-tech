package ru.ermolaayyyyyyy.banks.Models;

import ru.ermolaayyyyyyy.banks.Exceptions.ClientException;
import ru.ermolaayyyyyyy.banks.Interfaces.Observable;
import ru.ermolaayyyyyyy.banks.Interfaces.Observer;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DebitConditions implements Observable{

    public static final int FEE = 0;
    private final ArrayList<Observer> observers = new ArrayList<Observer>(0);
    public DebitConditions(Amount percent, Amount restriction) {
        this.percent = percent.sum;
        this.restriction = restriction.sum;
    }
    public BigDecimal percent;
    public BigDecimal restriction;
    @Override
    public void addObserver(Observer observer) {
        if (observers.contains(observer)) {
            throw ClientException.clientIsAlreadySubscribed();
        }

        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (!observers.contains(observer)) {
            throw ClientException.clientIsNotSubscribed();
        }

        observers.remove(observer);
    }

    @Override
    public void notifyObservers(BigDecimal newValue) {
        for (Observer observer : observers) {
            observer.update("Interest percent", newValue);
        }
    }

    public void changePercent(Amount newPercent) {
        percent = newPercent.sum;
        notifyObservers(newPercent.sum);
    }
}

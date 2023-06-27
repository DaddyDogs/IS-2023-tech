package ru.ermolaayyyyyyy.banks.Interfaces;

import java.math.BigDecimal;

public interface Observable {
    /**
     * Method that subscribe client for updates
     *
     * @param observer subscriber
     */
    void addObserver(Observer observer);

    /**
     * Method that removed client from subscribers
     * @param observer client that wants to unsubscribe
     */
    void removeObserver(Observer observer);

    /**
     *
     * @param newValue value that was changed
     */
    void notifyObservers(BigDecimal newValue);
}

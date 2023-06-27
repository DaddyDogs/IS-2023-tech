package ru.ermolaayyyyyyy.banks.Interfaces;

import java.math.BigDecimal;

public interface Observer {

    /**
     * Method to subscribe to limit updates
     * @param bank observable object
     */
    void subscribeToLimitUpdate(Bank bank);

    /**
     * Method to unsubscribe to limit updates
     * @param bank observable object
     */
    void unsubscribeToLimitUpdate(Bank bank);

    /**
     * Method to subscribe to percent updates
     * @param bank observable object
     */
    void subscribeToPercentUpdate(Bank bank);

    /**
     * Method to unsubscribe to percent updates
     * @param bank observable object
     */
    void unsubscribeToPercentUpdate(Bank bank);

    /**
     * Method to get updates by subscribers
     * @param subject what was changed
     * @param newValue new value of subject
     */
    void update(String subject, BigDecimal newValue);
    void setNotifier(Notifier notifier);
}

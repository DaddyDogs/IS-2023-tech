package ru.ermolaayyyyyyy.banks.Interfaces;

import java.math.BigDecimal;

public interface Notifier {
    /**
     * Method that notify subscribers about new conditions in the bank
     * @param subject which conditions were changed
     * @param newValue of subject
     */
    void notify(String subject, BigDecimal newValue);
}

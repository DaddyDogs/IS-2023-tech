package ru.ermolaayyyyyyy.banks.Services;

import ru.ermolaayyyyyyy.banks.Interfaces.Notifier;

import java.math.BigDecimal;

public class ConsoleNotifier implements Notifier {
    @Override
    public void notify(String subject, BigDecimal newValue) {
        System.out.println(subject + "was changed and now amounts " + newValue);
    }
}

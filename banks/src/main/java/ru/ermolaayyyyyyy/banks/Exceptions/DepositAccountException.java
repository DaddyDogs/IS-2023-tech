package ru.ermolaayyyyyyy.banks.Exceptions;

import java.math.BigDecimal;
import java.util.Date;

public class DepositAccountException extends BanksException{
    private DepositAccountException(String message) {
        super(message);
    }

    public static DepositAccountException deniedWithdrawingException(Date expiryDate)
    {
        throw new DepositAccountException("Can't withdraw money before account's expiry date " + expiryDate.toString());
    }

    public static DepositAccountException invalidDepositAmount(BigDecimal amount)
    {
        throw new DepositAccountException("Can't create account with deposit " + amount + " because it's over the limit");
    }
}

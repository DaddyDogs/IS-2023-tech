import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ermolaayyyyyyy.banks.Entities.Client.Client;
import ru.ermolaayyyyyyy.banks.Exceptions.ClientStateException;
import ru.ermolaayyyyyyy.banks.Exceptions.CreditAccountException;
import ru.ermolaayyyyyyy.banks.Exceptions.DebitAccountException;
import ru.ermolaayyyyyyy.banks.Exceptions.DepositAccountException;
import ru.ermolaayyyyyyy.banks.Interfaces.Bank;
import ru.ermolaayyyyyyy.banks.Interfaces.CentralBank;
import ru.ermolaayyyyyyy.banks.Models.*;
import ru.ermolaayyyyyyy.banks.Models.Address.Address;
import ru.ermolaayyyyyyy.banks.Services.CentralBankImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class BanksTest {
    private CentralBank centralBank;
    private Bank bank;
    private DepositConditions depositConditions;
    private CreditConditions creditConditions;
    private DebitConditions debitConditions;
    private Client client;
    @BeforeEach
    public void setup() {
        centralBank = new CentralBankImpl();
        var depositCondition = new ArrayList<DepositCondition>() {{
            add(new DepositCondition(new Amount(1000), new Amount(5)));
            add(new DepositCondition(new Amount(2000), new Amount(13)));
            add(new DepositCondition(new Amount(3000), new Amount(17)));
        }};
        depositConditions = new DepositConditions(depositCondition, 365, new Amount(1000));
        creditConditions = new CreditConditions(new Amount(1500), new Amount(10), new Amount(1500));
        debitConditions = new DebitConditions(new Amount(6), new Amount(900));

        bank = centralBank.createBank("Tinkoff", depositConditions, creditConditions, debitConditions);
        client = bank.registerClient("Suren", "Nerus", null, null);
    }

    @Test
    public void debitAccountTryGoIntoMinusException() {
        UUID newAccountId = bank.registerDebitAccount(client);

        Assertions.assertThrows(DebitAccountException.class, () -> centralBank.withDrawMoney(new Amount(100), newAccountId));
    }

    @Test
    public void DepositAccount_TryWithdrawMoneyBeforeExpiryDate_Exception() {
        UUID newAccountId = bank.registerDepositAccount(client, new Amount(1500));

        Assertions.assertThrows(DepositAccountException.class, () -> centralBank.withDrawMoney(new Amount(100), newAccountId));
    }

    @Test
    public void CreditAccount_OverstepLimit_Exception_CheckFee() {
        UUID newAccountId = bank.registerCreditAccount(client);

        centralBank.withDrawMoney(new Amount(100), newAccountId);

        Assertions.assertThrows(CreditAccountException.class, () -> centralBank.withDrawMoney(new Amount(1420), newAccountId));

        Assertions.assertEquals(BigDecimal.valueOf(-110), bank.getMoneyAmount(newAccountId));
    }

    @Test
    public void changeConditionsConditionsHaveChanged() {
        bank.changeInterestPercent(new Amount(15));
        Assertions.assertEquals(BigDecimal.valueOf(15), bank.getDebitConditions().percent);

        bank.changeCreditLimit(new Amount(6000));
        Assertions.assertEquals(BigDecimal.valueOf(6000), bank.getCreditConditions().limit);
    }

    @Test
    public void suspiciousClientTryWithdrawMoneyExceptionFillOutAccountAccomplishTransaction()
    {
        UUID accountId = bank.registerDebitAccount(client);

        centralBank.replenishAccount(new Amount(5000), accountId);
        Assertions.assertThrows(ClientStateException.class, () -> centralBank.withDrawMoney(new Amount(901), accountId));

        client.setAddress(Address.getBuilder().withStreet("Surenova").withBuilding("115A").withFlat(15).build());
        client.setPassport(1234, 228669);

        centralBank.withDrawMoney(new Amount(905), accountId);

        Assertions.assertEquals(BigDecimal.valueOf(5000 - 905), bank.getMoneyAmount(accountId));
    }
}

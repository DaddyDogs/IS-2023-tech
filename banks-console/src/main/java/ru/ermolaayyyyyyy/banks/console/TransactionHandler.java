package ru.ermolaayyyyyyy.banks.console;

import ru.ermolaayyyyyyy.banks.Entities.Client.Client;
import ru.ermolaayyyyyyy.banks.Interfaces.Account;
import ru.ermolaayyyyyyy.banks.Interfaces.Bank;
import ru.ermolaayyyyyyy.banks.Interfaces.CentralBank;
import ru.ermolaayyyyyyy.banks.Models.Amount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class TransactionHandler implements ConsoleHandler {
    private ConsoleHandler successor;
    private CentralBank centralBank;
    private Scanner scanner;

    public TransactionHandler(ConsoleHandler successor, CentralBank centralBank)
    {
        this.successor = successor;
        this.centralBank = centralBank;
        scanner = new Scanner(System.in);
    }

    public void handleRequest(String command, int state)
    {
        if (Objects.equals(command, "Make transaction"))
        {
            if (state < 4)
            {
                System.out.println("You have to create an account at first");
                successor.handleRequest(command, state);
            }
            else
            {
                Client client = chooseClient();

                UUID accountId = chooseAccountId(client);
                System.out.println("Input amount");
                String answer = scanner.nextLine();
                checkForNull(answer);
                BigDecimal answerDecimal = BigDecimal.valueOf(tryParseToInt(answer));
                while (answerDecimal.compareTo(BigDecimal.ZERO) <= 0)
                {
                    System.out.println("Invalid input");
                    answer = scanner.nextLine();
                    answerDecimal = BigDecimal.valueOf(tryParseToInt(answer));
                }

                BigDecimal amount = answerDecimal;
                System.out.println("Choose operation: \n replenish \n withdraw \n transfer");
                answer = scanner.nextLine();
                while (!Objects.equals(answer, "replenish") && !Objects.equals(answer, "withdraw") && !Objects.equals(answer, "transfer"))
                {
                    System.out.println("Invalid input");
                    answer = scanner.nextLine();
                }

                switch (answer)
                {
                    case "replenish":
                    {
                        centralBank.replenishAccount(new Amount(amount), accountId);
                        break;
                    }

                    case "withdraw":
                    {
                        centralBank.withDrawMoney(new Amount(amount), accountId);
                        break;
                    }

                    case "transfer":
                    {
                        System.out.println("Choose the recipient:");
                        var recipient = chooseClient();
                        centralBank.transferMoney(new Amount(amount), accountId, chooseAccountId(recipient));
                        break;
                    }
                }
            }
        }
        else
        {
            successor.handleRequest(command, state);
        }
    }

    public Client chooseClient()
    {
        System.out.println("Choose the client (print number):");

        var clients = new ArrayList<Client>();

        for (Bank b : centralBank.getBanksList())
        {
            for (var c : b.getClientList())
            {
                System.out.println(c.getLastName() + " " + c.getFirstName());
                clients.add(c);
            }
        }

        String answer = scanner.nextLine();
        checkForNull(answer);
        int ans = tryParseToInt(answer);
        while (ans <= 0 || ans > clients.size())
        {
            System.out.println("Invalid input");
            answer = scanner.nextLine();
            ans = tryParseToInt(answer);
        }

        return clients.get(ans - 1);
    }

    public UUID chooseAccountId(Client client)
    {
        System.out.println("Choose the account (print number):");
        ArrayList<UUID> accountsId = new ArrayList<>();

        for (Account account : client.accountList)
        {
            System.out.println(account.getId());
            accountsId.add(account.getId());
        }

        String answer = scanner.nextLine();
        checkForNull(answer);
        int ans = tryParseToInt(answer);
        while (ans <= 0 || ans > client.accountList.size())
        {
            System.out.println("Invalid input");
            answer = scanner.nextLine();
            ans = tryParseToInt(answer);
        }

        return accountsId.get(ans - 1);
    }
    public void checkForNull(String answer)
    {
        while (answer == null || answer.isEmpty()) {
            System.out.println("Invalid input");
            answer = scanner.nextLine();
        }
    }

    public int tryParseToInt(String line){
        int answer = 0;
        boolean parsed = false;
        while (!parsed) {
            try {
                answer = Integer.parseInt(line);
                parsed = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid output " + e.getMessage());
                line = scanner.nextLine();
            }
        }
        return answer;
    }
}

package ru.ermolaayyyyyyy.banks.console;

import ru.ermolaayyyyyyy.banks.Entities.Client.Client;
import ru.ermolaayyyyyyy.banks.Interfaces.Bank;
import ru.ermolaayyyyyyy.banks.Models.*;
import ru.ermolaayyyyyyy.banks.Models.Address.Address;
import ru.ermolaayyyyyyy.banks.Services.CentralBankImpl;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CreationHandler  implements ConsoleHandler{

    private ConsoleHandler _successor;
    private ConsoleHandler _successor2;
    private final Scanner scanner = new Scanner(System.in);
    public CentralBankImpl CentralBank = new CentralBankImpl();

    public void SetSuccessor1(ConsoleHandler successor)
    {
        _successor = successor;
    }

    public void SetSuccessor2(ConsoleHandler successor2)
    {
        _successor2 = successor2;
    }
    @Override
    public void handleRequest(String command, int state) {
        String[] words = command.split(" ");
        if (!Objects.equals(words[0], "Create"))
        {
            _successor.handleRequest(command, state);
        }
        else
        {
            String item = words[1];
            if (Objects.equals(item, "bank"))
            {
                System.out.println("Input the name");
                String name = scanner.nextLine();
                checkForNull(name);

                System.out.println("Input the maximal amount and interest percent for deposit account");

                var depositConditions = new ArrayList<DepositCondition>();

                String answer = scanner.nextLine();
                checkForNull(answer);
                String[] params = answer.split(" ");
                while (answer != null && !answer.isEmpty())
                {
                    depositConditions.add(new DepositCondition(new Amount(params[0]), new Amount(params[1])));
                    answer = scanner.nextLine();
                    params = answer.split(" ");
                }

                System.out.println("Input the term of the deposit in days");
                answer = scanner.nextLine();
                checkForNull(answer);
                var term = tryParseToInt(answer);

                System.out.println("Input the amount of restriction for suspicious clients for deposit");
                answer = scanner.nextLine();
                checkForNull(answer);

                var amount = new Amount(tryParseToInt(answer));

                var depositCondition = new DepositConditions(depositConditions, term, amount);

                System.out.println("Input limit, fee and restriction for suspicious clients for credit");
                answer = scanner.nextLine();
                checkForNull(answer);
                params = answer.split(" ");
                var creditCondition = new CreditConditions(new Amount(params[0]), new Amount(params[1]), new Amount(params[2]));

                System.out.println("Input percent and restriction for suspicious clients for debit");
                answer = scanner.nextLine();
                checkForNull(answer);

                params = answer.split(" ");
                var debitCondition = new DebitConditions(new Amount(params[0]), new Amount(params[1]));
                CentralBank.createBank(name, depositCondition, creditCondition, debitCondition);
                System.out.println("Bank was successfully created!");
            }
            else if (Objects.equals(item, "client"))
            {
                if (state < 2)
                {
                    System.out.println("You have to create a bank at first");
                    _successor2.handleRequest(command, state);
                }

                System.out.println("Choose the bank:");
                for (Bank b : CentralBank.getBanksList())
                {
                    System.out.println(b.getName());
                }

                String answer = scanner.nextLine();
                String finalAnswer = answer;
                while (answer == null || (CentralBank.getBanksList().stream().filter(b -> Objects.equals(b.getName(), finalAnswer)).findAny().orElse(null) == null))
                {
                    System.out.println("Invalid input");
                    answer = scanner.nextLine();
                }

                String finalAnswer1 = answer;
                var bank = CentralBank.getBanksList().stream().filter(b -> Objects.equals(b.getName(), finalAnswer1)).findAny().orElse(null);
                assert bank != null;

                System.out.println("Input your last name");
                String name = scanner.nextLine();
                checkForNull(name);

                var lastname = name;

                System.out.println("Input your first name");
                name = scanner.nextLine();
                checkForNull(name);

                var firstname = name;

                System.out.println("Want to fill out account to have all access?");
                answer = scanner.nextLine();
                checkForNull(answer);
                while (!Objects.equals(answer, "Yes") && !Objects.equals(answer, "No"))
                {
                    System.out.println("Invalid input");
                    answer = scanner.nextLine();
                }

                if (answer.equals("No"))
                {
                    bank.registerClient(firstname, lastname, null, null);
                }
                else
                {
                    System.out.println("Input passport series");
                    answer = scanner.nextLine();
                    checkForNull(answer);
                    int ans = tryParseToInt(answer);
                    while (answer.length() != 4)
                    {
                        System.out.println("Invalid input");
                        answer = scanner.nextLine();
                        ans = tryParseToInt(answer);
                    }

                    int series = ans;

                    System.out.println("Input passport number");
                    answer = scanner.nextLine();
                    checkForNull(answer);
                    ans = tryParseToInt(answer);
                    while (answer.length() != 6)
                    {
                        System.out.println("Invalid input");
                        answer = scanner.nextLine();
                        ans = tryParseToInt(answer);
                    }

                    int numberPassport = ans;

                    System.out.println("Input your address \n street: ");
                    answer = scanner.nextLine();
                    checkForNull(answer);
                    String street = answer;

                    System.out.println("\n building number: ");
                    answer = scanner.nextLine();
                    checkForNull(answer);
                    String building = answer;

                    System.out.println("\n flat number: ");
                    answer = scanner.nextLine();
                    checkForNull(answer);
                    int flat = tryParseToInt(answer);

                    bank.registerClient(firstname, lastname, Address.getBuilder().withStreet(street).withBuilding(building).withFlat(flat).build(), new Passport(series, numberPassport));
                }

                System.out.println("Client was registered successfully");
            }
            else if (Objects.equals(item, "account"))
            {
                if (state < 3)
                {
                    System.out.println("You have to create a client at first");
                    _successor2.handleRequest(command, state);
                }

                System.out.println("Choose the client (print number):");

                var clients = new ArrayList<Client>();

                for (Bank b : CentralBank.getBanksList())
                {
                    for (Client c : b.getClientList())
                    {
                        System.out.println(c.getLastName() + " " + c.getFirstName());
                        clients.add(c);
                    }
                }

                String answer = scanner.nextLine();
                checkForNull(answer);
                int ans = tryParseToInt(answer);
                while (ans <= 0 || ans > (long) clients.size())
                {
                    System.out.println("Invalid input");
                    answer = scanner.nextLine();
                }

                var client = clients.get(ans - 1);

                System.out.println("Choose the bank:");
                var banks = new ArrayList<Bank>();
                for (Bank b : CentralBank.getBanksList().stream().filter(b -> b.getClientList().contains(client)).toList())
                {
                    System.out.println(b.getName());
                    banks.add(b);
                }

                answer = scanner.nextLine();
                checkForNull(answer);
                String finalAnswer = answer;
                Bank bank = banks.stream().filter(b -> Objects.equals(b.getName(), finalAnswer)).findAny().orElse(null);
                while (bank == null)
                {
                    System.out.println("Invalid input");
                    answer = scanner.nextLine();
                    String finalAnswer1 = answer;
                    bank = banks.stream().filter(b -> Objects.equals(b.getName(), finalAnswer1)).findAny().orElse(null);
                }

                System.out.println("Choose account's type: \n Credit \n Debit \n Deposit");
                answer = scanner.nextLine();
                checkForNull(answer);
                while (!Objects.equals(answer, "Credit") && !Objects.equals(answer, "Debit") && !Objects.equals(answer, "Deposit"))
                {
                    System.out.println("Invalid input");
                    answer = scanner.nextLine();
                }

                switch (answer) {
                    case "Credit" -> {
                        bank.registerCreditAccount(client);
                    }
                    case "Debit" -> {
                        bank.registerDebitAccount(client);
                    }
                    case "Deposit" -> {
                        System.out.println("Input the amount you want to put on the account");
                        checkForNull(answer);
                        ans = tryParseToInt(answer);
                        while (ans < 0) {
                            System.out.println("Invalid input");
                            answer = scanner.nextLine();
                            ans = tryParseToInt(answer);
                        }

                        bank.registerDepositAccount(client, new Amount(ans));
                    }
                }

                System.out.println("Account was created successfully!");
            }
            else
            {
                _successor2.handleRequest(command, state);
            }
        }
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

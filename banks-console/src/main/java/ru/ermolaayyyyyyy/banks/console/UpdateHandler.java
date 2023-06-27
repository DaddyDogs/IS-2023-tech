package ru.ermolaayyyyyyy.banks.console;

import ru.ermolaayyyyyyy.banks.Entities.Client.Client;
import ru.ermolaayyyyyyy.banks.Interfaces.Bank;
import ru.ermolaayyyyyyy.banks.Interfaces.CentralBank;
import ru.ermolaayyyyyyy.banks.Models.Address.Address;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class UpdateHandler implements ConsoleHandler {
    private ConsoleHandler successor;
    private CentralBank centralBank;
    private Scanner scanner;

    public UpdateHandler(ConsoleHandler successor, CentralBank centralBank)
    {
        this.successor = successor;
        this.centralBank = centralBank;
        scanner = new Scanner(System.in);
    }

    public void handleRequest(String command, int state)
    {
        if (Objects.equals(command, "Update client"))
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

            var client = clients.get(ans - 1);
            System.out.println("What do you want to update: passport or address?");
            answer = scanner.nextLine();
            while (!Objects.equals(answer, "passport") && !Objects.equals(answer, "address"))
            {
                System.out.println("Invalid input");
                answer = scanner.nextLine();
            }

            switch (answer)
            {
                case "passport":
                {
                    System.out.println("Input passport series");
                    answer = scanner.nextLine();
                    ans = tryParseToInt(answer);
                    while (answer.length() != 4)
                    {
                        System.out.println("Invalid input");
                        answer = scanner.nextLine();
                        ans = tryParseToInt(answer);
                    }

                    int series = ans;

                    System.out.println("Input passport number");
                    answer = scanner.nextLine();
                    ans = tryParseToInt(answer);
                    while (answer.length() != 6)
                    {
                        System.out.println("Invalid input");
                        answer = scanner.nextLine();
                        ans = tryParseToInt(answer);
                    }

                    int numberPassport = ans;
                    client.setPassport(series, numberPassport);
                    break;
                }

                case "address":
                {
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
                    client.setAddress(Address.getBuilder().withStreet(street).withBuilding(building).withFlat(flat).build());
                    break;
                }
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

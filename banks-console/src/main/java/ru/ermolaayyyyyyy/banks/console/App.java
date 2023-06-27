package ru.ermolaayyyyyyy.banks.console;

import java.util.Objects;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        System.out.println("Hello! Choose the command: \n Create bank \n Exit");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        while (answer == null)
        {
            System.out.println("Invalid input");
            answer = scanner.nextLine();
        }

        var handler = new HandlersController();
        handler.handleRequest(answer, 1);

        System.out.println("Choose the command: \n Create client \n Create bank \n Exit");
        answer = scanner.nextLine();
        while (answer == null)
        {
            System.out.println("Invalid input");
            answer = scanner.nextLine();
        }

        handler.handleRequest(answer, 2);

        System.out.println("Choose the command: \n Create client \n Create bank \n Create account \n Update client \n Exit");
        answer = scanner.nextLine();
        while (answer == null)
        {
            System.out.println("Invalid input");
            answer = scanner.nextLine();
        }

        handler.handleRequest(answer, 3);

        System.out.println("Choose the command: \n Create client \n Create bank \n Create account \n Make transaction \n Update client \n Exit");
        answer = scanner.nextLine();
        while (!Objects.equals(answer, "Exit"))
        {
            while (answer == null)
            {
                System.out.println("Invalid input");
                answer = scanner.nextLine();
            }

            handler.handleRequest(answer, 4);

            System.out.println("Choose the command: \n Create client \n Create bank \n Create account \n Make transaction \n Update client \n Exit");
            answer = scanner.nextLine();
        }
    }
}

package ru.ermolaayyyyyyy.banks.console;

import java.util.Scanner;

public class InvalidOperationHandler implements ConsoleHandler{
    private ConsoleHandler successor;
    private Scanner scanner;

    public InvalidOperationHandler(ConsoleHandler successor)
    {
        this.successor = successor;
        scanner = new Scanner(System.in);
    }

    public void handleRequest(String command, int state)
    {
        System.out.println("Invalid request. You can choose operation from suggested");
        String answer = scanner.nextLine();
        while (answer == null || answer.isEmpty())
        {
            System.out.println("Wait for the command");
            answer = scanner.nextLine();
        }

        successor.handleRequest(answer, state);
    }
}

package ru.ermolaayyyyyyy.banks.console;

import java.util.Objects;

public class ExitHandler implements ConsoleHandler{
    private ConsoleHandler successor;
    public ExitHandler(ConsoleHandler successor){
        this.successor = successor;
    }

    @Override
    public void handleRequest(String command, int state) {
        if (Objects.equals(command, "Exit"))
        {
            System.out.println("Goodbye");
            System.exit(0);
        }
        else
        {
            successor.handleRequest(command, state);
        }
    }
}

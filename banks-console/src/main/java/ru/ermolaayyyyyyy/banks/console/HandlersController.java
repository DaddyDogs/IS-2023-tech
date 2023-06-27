package ru.ermolaayyyyyyy.banks.console;

import lombok.Getter;

public class HandlersController implements ConsoleHandler{
    @Getter
    public ExitHandler ExitHandler;
    @Getter
    public InvalidOperationHandler InvalidOperationHandler;
    public HandlersController()
    {
        CreationHandler creationHandler = new CreationHandler();
        ExitHandler = new ExitHandler(creationHandler);
        InvalidOperationHandler = new InvalidOperationHandler(ExitHandler);
        UpdateHandler updateHandler = new UpdateHandler(InvalidOperationHandler, creationHandler.CentralBank);
        var transactionHandler = new TransactionHandler(updateHandler, creationHandler.CentralBank);
        creationHandler.SetSuccessor1(transactionHandler);
        creationHandler.SetSuccessor2(InvalidOperationHandler);
    }

    @Override
    public void handleRequest(String command, int state) {
        getExitHandler().handleRequest(command, state);
    }
}

package ru.ermolaayyyyyyy.leschats.exceptions;

public class RabbitException extends ChatsException{
    protected RabbitException(String message) {
        super(message);
    }

    public static RabbitException GatewayTimeoutException(String queueName){
        return new RabbitException("Queue " + queueName + " does not respond");
    }
}

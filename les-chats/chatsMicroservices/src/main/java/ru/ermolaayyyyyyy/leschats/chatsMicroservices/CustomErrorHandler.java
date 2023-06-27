package ru.ermolaayyyyyyy.leschats.chatsMicroservices;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ermolaayyyyyyy.leschats.dto.ExceptionMessage;

@Configuration
public class CustomErrorHandler implements RabbitListenerErrorHandler {
    Logger logger = LogManager.getLogger(CatServiceImpl.class);
    @SneakyThrows
    @Override
    public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) throws Exception {
        logger.info(exception.getCause().getMessage() + exception.getCause().getClass().getTypeName());
        return exception.getCause().getMessage();
    }
}

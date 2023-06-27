package ru.ermolaayyyyyyy.leschats.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    Logger logger = LogManager.getLogger(RabbitConfiguration.class);

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }
    @Bean
    Queue messagesQueue() {
        return QueueBuilder.durable("QUEUE_MESSAGES")
                .withArgument("x-dead-letter-exchange", "deadExchange")
                .build();
    }
    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange("deadExchange");
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable("deadLetterQueue").build();
    }

    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        //rabbitTemplate.setReceiveTimeout(20000);
        //rabbitTemplate.setReplyTimeout(20000);
        return rabbitTemplate;
    }

    @Bean
    public Queue myQueue1() {
        return new Queue("findCat");
    }

    @Bean
    public Queue myQueue2() {
        return new Queue("deleteCat");
    }
    @Bean
    public Queue myQueue3() {
        return new Queue("saveCat");
    }

    @Bean
    public Queue myQueue4() {
        return new Queue("updateCat");
    }

    @Bean
    public Queue myQueue5() {
        return new Queue("addFriend");
    }

    @Bean
    public Queue myQueue6() {
        return new Queue("findFilteredCats");
    }

    @Bean
    public Queue myQueue7() {
        return new Queue("getOwner");
    }
    @Bean
    public Queue myQueue8() {
        return new Queue("findAllCats");
    }
    @Bean
    public Queue myQueue9() {
        return new Queue("authenticate");
    }

//    @Bean
//    public ErrorHandler errorHandler() {
//        return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
//    }
//
//    public static class MyFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
//
//        @Override
//        public boolean isFatal(Throwable t) {
//            if (t instanceof ListenerExecutionFailedException) {
//                ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
//                logger.error("Failed to process inbound message from queue "
//                        + lefe.getFailedMessage().getMessageProperties().getConsumerQueue()
//                        + "; failed message: " + lefe.getFailedMessage(), t);
//            }
//            return super.isFatal(t);
//        }
//
//    }

//    @Bean
//    public ObjectMapper objectMapper(){
//        return new ObjectMapper().findAndRegisterModules();
////        return JsonMapper.builder().configure().build();
//    }

//    @Bean
//    public Queue myQueue2() {
//        return new Queue("mQueue1");
//    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
//    @Bean
//    public RemoteInvocationAwareMessageConverterAdapter remoteInvocationAwareMessageConverterAdapter() {
//        return new RemoteInvocationAwareMessageConverterAdapter(new Jackson2JsonMessageConverter());
//    }

//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer1() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory());
//        container.setQueueNames("mQueue");
////        container.setQueueNames("mQueue1");
//        container.setMessageListener(message -> {
//                logger.info("received from mQueue : " + new String(message.getBody()));
//            });
//        return container;
//    }
}

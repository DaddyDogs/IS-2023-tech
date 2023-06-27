package ru.ermolaayyyyyyy.leschats.chatsMicroservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

@Configuration
public class RabbitConfiguration {
    Logger logger = LogManager.getLogger(RabbitConfiguration.class);
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

//    @Bean
//    public AmqpAdmin amqpAdmin() {
//        return new RabbitAdmin(connectionFactory());
//    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    @Bean
    public RabbitListenerErrorHandler customRabbitListenerErrorHandler(){
        return new CustomErrorHandler();
    }
//    @Bean ErrorHandler errorHandler(){
//        return
//    }

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
    public ObjectMapper objectMapper(){
//        return new ObjectMapper().findAndRegisterModules();
        return JsonMapper.builder().addModule(new JavaTimeModule()).build();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jsonMessageConverter());
        factory.setDefaultRequeueRejected(false);
        return factory;
    }
}
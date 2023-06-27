package ru.ermolaayyyyyyy.leschats.ownersMicroservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    @Bean
    public Queue myQueue1() {
        return new Queue("findOwner");
    }
    @Bean
    public Queue myQueue2() {
        return new Queue("saveOwner");
    }
    @Bean
    public Queue myQueue3() {
        return new Queue("deleteOwner");
    }

    @Bean
    public Queue myQueue4() {
        return new Queue("updateOwner");
    }
    @Bean
    public Queue myQueue5() {
        return new Queue("getOwner");
    }

    @Bean
    public Queue myQueue6() {
        return new Queue("findAllOwners");
    }

    @Bean
    public ObjectMapper objectMapper(){
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

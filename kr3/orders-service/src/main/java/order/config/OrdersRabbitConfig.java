package order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrdersRabbitConfig {
    public static final String EXCHANGE = "order-exchange";
    public static final String ROUTING_KEY = "order.created";
    public static final String QUEUE = "orders.queue";

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue ordersQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding ordersBinding(Queue ordersQueue, TopicExchange orderExchange) {
        return BindingBuilder
                .bind(ordersQueue)
                .to(orderExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter converter
    ) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}

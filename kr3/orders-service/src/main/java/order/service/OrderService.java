package order.service;

import com.events.OrderCreatedEvent;
import order.config.OrdersRabbitConfig;
import order.entity.Order;
import order.entity.OutboxEvent;
import order.repository.OutboxRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import order.repository.OrderRepository;

import java.math.BigDecimal;


@Service
public class OrderService {
    private final RabbitTemplate rabbitTemplate;
    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;

    public OrderService(RabbitTemplate rabbitTemplate,
                        OrderRepository orderRepository, OutboxRepository outboxRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    public Order createOrder(String userId, BigDecimal amount) {
        Order order = new Order(null, userId, amount, "CREATED");
        orderRepository.save(order);

        OutboxEvent event = new OutboxEvent();
        event.setAggregateType("Order");
        event.setEventType("OrderCreated");
        String payload = String.format(
                "{\"orderId\":%d,\"userId\":\"%s\",\"amount\":%s}",
                order.getId(), userId, amount.toString()
        );
        event.setPayload(payload);
        event.setSent(false);
        outboxRepository.save(event);

        rabbitTemplate.convertAndSend(
                OrdersRabbitConfig.EXCHANGE,
                OrdersRabbitConfig.ROUTING_KEY,
                new OrderCreatedEvent(order.getId(), order.getUserId(), order.getAmount())
        );
        return order;
    }

}

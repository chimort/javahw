package payments.service;

import payments.config.RabbitConfig;
import payments.entity.OutboxEvent_;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import payments.repository.OutboxRepository;

import java.util.List;


@Component
public class OutboxPublisher {

    private final OutboxRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;

    public OutboxPublisher(OutboxRepository outboxRepository,
                           RabbitTemplate rabbitTemplate) {
        this.outboxRepository = outboxRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    public void publishPendingEvents() {
        List<OutboxEvent_> pending = outboxRepository.findBySentFalse();
        for (OutboxEvent_ event : pending) {
            try {
                rabbitTemplate.convertAndSend(RabbitConfig.PAYMENTS_QUEUE, event.getPayload());
                event.setSent(true);
                outboxRepository.save(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

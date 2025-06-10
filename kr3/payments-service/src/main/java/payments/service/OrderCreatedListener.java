package payments.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import payments.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import payments.requests.PaymentMessage;


@Service
public class OrderCreatedListener {

    private final AccountService accountService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderCreatedListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @RabbitListener(queues = RabbitConfig.ORDERS_QUEUE)
    public void onMessage(String message) {
        try {
            PaymentMessage msg = objectMapper.readValue(message, PaymentMessage.class);
            accountService.processPayment(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

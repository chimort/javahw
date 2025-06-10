package payments.listener;

import com.events.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import payments.config.RabbitConfig;
import payments.service.AccountService;

@Component
public class PaymentListener {

    private final AccountService accountService;

    public PaymentListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @RabbitListener(
            queues = RabbitConfig.ORDERS_QUEUE,
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void onOrderCreated(OrderCreatedEvent event) {
        accountService.topUp(event.getUserId(), event.getAmount());
    }
}
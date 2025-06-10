package payments.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payments.entity.Account;
import payments.entity.InboxEvent;
import payments.entity.OutboxEvent_;
import payments.entity.TransactionRecord;
import payments.repository.AccountRepository;
import payments.repository.InboxRepository;
import payments.repository.OutboxRepository;
import payments.repository.TransactionRepository;
import payments.requests.PaymentMessage;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final InboxRepository inboxRepository;
    private final OutboxRepository outboxRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository,
                          InboxRepository inboxRepository,
                          OutboxRepository outboxRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.inboxRepository = inboxRepository;
        this.outboxRepository = outboxRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public BigDecimal topUp(String userId, BigDecimal amount) {
        Account account = accountRepository.findById(userId)
                .orElseGet(() -> new Account(userId, BigDecimal.ZERO));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        return account.getBalance();
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalance(String userId) {
        return accountRepository.findById(userId)
                .map(Account::getBalance)
                .orElse(BigDecimal.ZERO);
    }


    @Transactional
    public void processPayment(PaymentMessage msg) {
        Long orderId = msg.getOrderId();
        String userId = msg.getUserId();
        BigDecimal amount = msg.getAmount();

        if (inboxRepository.existsById(orderId)) {
            return;
        }

        if (transactionRepository.existsById(orderId)) {
            return;
        }

        Account account = accountRepository.findById(userId)
                .orElseGet(() -> new Account(userId, BigDecimal.ZERO));

        boolean success;
        String status;
        if (account.getBalance().compareTo(amount) >= 0) {
            account.setBalance(account.getBalance().subtract(amount));
            accountRepository.save(account);
            success = true;
            status = "SUCCESS";
        } else {
            success = false;
            status = "FAILURE";
        }

        TransactionRecord tr = new TransactionRecord(orderId, status);
        transactionRepository.save(tr);

        inboxRepository.save(new InboxEvent(orderId));

        String payload = String.format(
                "{\"orderId\":%d,\"status\":\"%s\",\"userId\":\"%s\",\"amount\":%s}",
                orderId, status, userId, amount.toPlainString()
        );

        String eventType = success ? "PaymentSucceeded" : "PaymentFailed";
        OutboxEvent_ outboxEvent = new OutboxEvent_(eventType, payload);
        outboxRepository.save(outboxEvent);
    }
}

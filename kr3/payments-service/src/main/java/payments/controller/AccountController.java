package payments.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payments.requests.TopUpRequest;
import payments.service.AccountService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{userId}/top-up")
    public ResponseEntity<BigDecimal> topUp(@PathVariable String userId, @RequestBody TopUpRequest req) {
        BigDecimal newBalance = accountService.topUp(userId, req.getAmount());
        return ResponseEntity.ok(newBalance);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String userId) {
        BigDecimal balance = accountService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }
}

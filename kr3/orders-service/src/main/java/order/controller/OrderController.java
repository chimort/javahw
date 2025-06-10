package order.controller;

import order.entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import order.requests.OrderRequest;
import order.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderRequest req) {
        Order created = orderService.createOrder(req.getUserId(), req.getAmount());
        return ResponseEntity.ok(created);
    }

}
